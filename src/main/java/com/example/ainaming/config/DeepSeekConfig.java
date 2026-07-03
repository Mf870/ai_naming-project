package com.example.ainaming.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * DeepSeek 相关配置
 * 与 application.yml 中 deepseek 前缀的配置项绑定，
 * 确保 API Key 等敏感信息不写死在代码中，而是从配置文件 / 环境变量中读取
 */
@Component
@ConfigurationProperties(prefix = "deepseek")
public class DeepSeekConfig {

    /** DeepSeek 接口基础地址，例如 https://api.deepseek.com */
    private String baseUrl;

    /** DeepSeek API Key */
    private String apiKey;

    /** 使用的模型名称，例如 deepseek-chat */
    private String model;

    /** 请求超时时间（毫秒） */
    private long timeoutMillis = 60000L;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }

    public void setTimeoutMillis(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }
}
