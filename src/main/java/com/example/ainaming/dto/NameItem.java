package com.example.ainaming.dto;

/**
 * 单个推荐名字条目
 * 对应 DeepSeek 返回 JSON 中 names 数组的每一个元素
 */
public class NameItem {

    /** 推荐的名字，例如：张云泽 */
    private String name;

    /** 寓意说明 */
    private String meaning;

    /** 推荐理由 */
    private String reason;

    /** 推荐分数 */
    private Integer score;

    public NameItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
