package com.example.ainaming.dto;

/**
 * DeepSeek Chat Completion 消息体
 * 对应官方接口中 messages 数组的元素结构：{ "role": "...", "content": "..." }
 */
public class DeepSeekMessage {

    /** 角色：system / user / assistant */
    private String role;

    /** 消息内容 */
    private String content;

    public DeepSeekMessage() {
    }

    public DeepSeekMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
