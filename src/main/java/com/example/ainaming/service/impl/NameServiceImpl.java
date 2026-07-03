package com.example.ainaming.service.impl;

import com.example.ainaming.config.DeepSeekConfig;
import com.example.ainaming.dto.DeepSeekChatRequest;
import com.example.ainaming.dto.DeepSeekChatResponse;
import com.example.ainaming.dto.DeepSeekMessage;
import com.example.ainaming.dto.NameRequest;
import com.example.ainaming.dto.NameResponse;
import com.example.ainaming.entity.NameRecord;
import com.example.ainaming.exception.BusinessException;
import com.example.ainaming.repository.NameRecordRepository;
import com.example.ainaming.service.NameService;
import com.example.ainaming.util.JsonContentExtractor;
import com.example.ainaming.util.PromptBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 取名 Service 实现类
 * 负责：
 * 1. 构造 Prompt
 * 2. 调用 DeepSeek Chat Completion API
 * 3. 获取返回结果
 * 4. 提取 choices[0].message.content
 * 5. 将 JSON 转换为 Java 对象
 * 6. 返回给 Controller
 */
@Service
public class NameServiceImpl implements NameService {

    private static final Logger log = LoggerFactory.getLogger(NameServiceImpl.class);

    /** DeepSeek 官方 Chat Completion 接口路径（相对于 baseUrl） */
    private static final String CHAT_COMPLETIONS_PATH = "/chat/completions";

    private final WebClient deepSeekWebClient;
    private final DeepSeekConfig deepSeekConfig;
    private final ObjectMapper objectMapper;
    private final NameRecordRepository nameRecordRepository;

    public NameServiceImpl(@Qualifier("deepSeekWebClient") WebClient deepSeekWebClient,
                            DeepSeekConfig deepSeekConfig,
                            ObjectMapper objectMapper,
                            NameRecordRepository nameRecordRepository) {
        this.deepSeekWebClient = deepSeekWebClient;
        this.deepSeekConfig = deepSeekConfig;
        this.objectMapper = objectMapper;
        this.nameRecordRepository = nameRecordRepository;
    }

    @Override
    public NameResponse generateNames(NameRequest request) {
        log.info("收到取名请求：{}", request);

        // 1. 构造 System Prompt 与 User Prompt
        String systemPrompt = PromptBuilder.buildSystemPrompt();
        String userPrompt = PromptBuilder.buildUserPrompt(request);

        // 2. 构造 DeepSeek 官方格式请求体
        DeepSeekChatRequest chatRequest = new DeepSeekChatRequest(
                deepSeekConfig.getModel(),
                List.of(
                        new DeepSeekMessage("system", systemPrompt),
                        new DeepSeekMessage("user", userPrompt)
                ),
                1.3, // 温度：取名场景需要一定创造性，采用官方推荐的“创意写作”温度值
                false // 不使用流式返回
        );

        // 3. 调用 DeepSeek Chat Completion API
        DeepSeekChatResponse chatResponse = callDeepSeek(chatRequest);

        // 4. 提取 choices[0].message.content
        String content = extractContent(chatResponse);
        log.debug("DeepSeek 原始返回内容：{}", content);

        // 5. 清理并解析 JSON 为 Java 对象
        NameResponse nameResponse = parseNameResponse(content);

        // 6. 保存本次取名历史到数据库，方便后续在“历史记录”中查看
        saveHistory(request, nameResponse);

        return nameResponse;
    }

    /**
     * 保存一条取名历史记录
     * 保存失败不影响本次接口的正常返回，只记录日志，避免因为历史记录功能拖垮核心的取名功能
     */
    private void saveHistory(NameRequest request, NameResponse nameResponse) {
        try {
            NameRecord record = new NameRecord();
            record.setSurname(request.getSurname());
            record.setGender(request.getGender());
            String namesJoined = nameResponse.getNames() == null
                    ? ""
                    : nameResponse.getNames().stream()
                            .map(item -> item.getName() == null ? "" : item.getName())
                            .collect(Collectors.joining("、"));
            record.setGeneratedNames(namesJoined);
            record.setCreateTime(LocalDateTime.now());
            nameRecordRepository.save(record);
        } catch (Exception e) {
            log.warn("保存取名历史记录失败，不影响本次取名结果返回", e);
        }
    }

    /**
     * 调用 DeepSeek 官方 REST API
     */
    private DeepSeekChatResponse callDeepSeek(DeepSeekChatRequest chatRequest) {
        try {
            return deepSeekWebClient.post()
                    .uri(CHAT_COMPLETIONS_PATH)
                    .bodyValue(chatRequest)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            clientResponse -> clientResponse.bodyToMono(String.class)
                                    .flatMap(body -> {
                                        log.error("DeepSeek 接口返回错误，状态码：{}，响应体：{}",
                                                clientResponse.statusCode(), body);
                                        return reactor.core.publisher.Mono.error(
                                                new BusinessException("调用 DeepSeek 接口失败：" + body));
                                    }))
                    .bodyToMono(DeepSeekChatResponse.class)
                    .timeout(Duration.ofMillis(deepSeekConfig.getTimeoutMillis()))
                    .block();
        } catch (WebClientResponseException e) {
            log.error("调用 DeepSeek 接口异常，状态码：{}，响应体：{}", e.getStatusCode(), e.getResponseBodyAsString(), e);
            throw new BusinessException("调用 DeepSeek 接口异常：" + e.getMessage());
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("调用 DeepSeek 接口发生未知异常", e);
            throw new BusinessException("调用 DeepSeek 接口发生未知异常：" + e.getMessage());
        }
    }

    /**
     * 从 DeepSeek 响应中提取 choices[0].message.content
     */
    private String extractContent(DeepSeekChatResponse response) {
        if (response == null
                || response.getChoices() == null
                || response.getChoices().isEmpty()
                || response.getChoices().get(0).getMessage() == null) {
            throw new BusinessException("DeepSeek 未返回有效内容，请稍后重试");
        }
        String content = response.getChoices().get(0).getMessage().getContent();
        if (content == null || content.isBlank()) {
            throw new BusinessException("DeepSeek 返回内容为空，请稍后重试");
        }
        return content;
    }

    /**
     * 将 DeepSeek 返回的文本内容解析为 NameResponse 对象
     */
    private NameResponse parseNameResponse(String content) {
        String jsonContent = JsonContentExtractor.extractJson(content);
        try {
            return objectMapper.readValue(jsonContent, NameResponse.class);
        } catch (Exception e) {
            log.error("解析 DeepSeek 返回的 JSON 失败，原始内容：{}", content, e);
            throw new BusinessException("解析取名结果失败，请稍后重试");
        }
    }
}
