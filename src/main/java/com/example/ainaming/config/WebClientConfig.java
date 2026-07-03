package com.example.ainaming.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

/**
 * WebClient 配置类
 * Spring Boot 3 推荐使用 WebClient（响应式）替代已过时的 RestTemplate。
 * 这里创建一个专门用于访问 DeepSeek 官方 API 的 WebClient Bean，
 * 统一配置了 Base URL、鉴权 Header 与超时时间。
 */
@Configuration
public class WebClientConfig {

    /**
     * 创建专门用于调用 DeepSeek Chat Completion API 的 WebClient
     *
     * @param deepSeekConfig DeepSeek 配置（baseUrl、apiKey、timeout）
     * @return 配置好的 WebClient 实例
     */
    @Bean
    public WebClient deepSeekWebClient(DeepSeekConfig deepSeekConfig) {
        // 配置连接超时与读取超时时间，避免请求长时间挂起
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) deepSeekConfig.getTimeoutMillis())
                .doOnConnected(conn -> conn.addHandlerLast(
                        new ReadTimeoutHandler(deepSeekConfig.getTimeoutMillis(), TimeUnit.MILLISECONDS)));

        return WebClient.builder()
                // DeepSeek 官方接口基础地址
                .baseUrl(deepSeekConfig.getBaseUrl())
                // 统一携带 Authorization: Bearer YOUR_API_KEY
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + deepSeekConfig.getApiKey())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new org.springframework.http.client.reactive.ReactorClientHttpConnector(httpClient))
                .build();
    }
}
