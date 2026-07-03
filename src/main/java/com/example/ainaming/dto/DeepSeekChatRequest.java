package com.example.ainaming.dto;

import java.util.List;

/**
 * DeepSeek Chat Completion 请求体
 * 严格对应官方 REST API 请求格式：
 * {
 *   "model": "deepseek-chat",
 *   "messages": [ { "role": "...", "content": "..." } ]
 * }
 */
public class DeepSeekChatRequest {

    /** 模型名称，例如 deepseek-chat */
    private String model;

    /** 对话消息列表 */
    private List<DeepSeekMessage> messages;

    /** 采样温度，控制输出的随机性，取名场景使用适中的值 */
    private Double temperature;

    /** 是否使用流式返回，本项目不需要流式，固定为 false */
    private Boolean stream;

    public DeepSeekChatRequest() {
    }

    public DeepSeekChatRequest(String model, List<DeepSeekMessage> messages, Double temperature, Boolean stream) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
        this.stream = stream;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<DeepSeekMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<DeepSeekMessage> messages) {
        this.messages = messages;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }
}
