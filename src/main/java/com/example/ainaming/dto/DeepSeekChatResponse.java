package com.example.ainaming.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * DeepSeek Chat Completion 响应体
 * 只映射本项目关心的字段，其余未知字段通过 @JsonIgnoreProperties 忽略，
 * 避免 DeepSeek 接口新增字段导致反序列化失败
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeepSeekChatResponse {

    /** 响应 ID */
    private String id;

    /** 使用的模型 */
    private String model;

    /** 候选回复列表，通常取第一个即 choices[0] */
    private List<Choice> choices;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    /**
     * 候选回复条目
     * 对应 choices[i]，其中 message.content 即为模型生成的正文内容
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Choice {

        /** 序号 */
        private Integer index;

        /** 消息内容，包含 role 与 content */
        private DeepSeekMessage message;

        /** 结束原因，DeepSeek 返回字段名为 finish_reason（下划线命名），需显式映射 */
        @JsonProperty("finish_reason")
        private String finishReason;

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public DeepSeekMessage getMessage() {
            return message;
        }

        public void setMessage(DeepSeekMessage message) {
            this.message = message;
        }

        public String getFinishReason() {
            return finishReason;
        }

        public void setFinishReason(String finishReason) {
            this.finishReason = finishReason;
        }
    }
}
