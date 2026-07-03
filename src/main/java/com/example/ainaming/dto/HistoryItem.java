package com.example.ainaming.dto;

/**
 * 历史记录条目 DTO
 * 用于 /api/history 接口返回给前端展示，避免直接暴露数据库实体
 */
public class HistoryItem {

    private Long id;
    private String surname;
    private String gender;
    /** 生成的名字，多个名字用顿号拼接，例如：张云泽、张思远 */
    private String generatedNames;
    /** 创建时间，格式化为字符串，例如：2026-07-03 15:30:00 */
    private String createTime;

    public HistoryItem() {
    }

    public HistoryItem(Long id, String surname, String gender, String generatedNames, String createTime) {
        this.id = id;
        this.surname = surname;
        this.gender = gender;
        this.generatedNames = generatedNames;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGeneratedNames() {
        return generatedNames;
    }

    public void setGeneratedNames(String generatedNames) {
        this.generatedNames = generatedNames;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
