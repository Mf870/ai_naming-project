package com.example.ainaming.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 取名请求 DTO
 * 对应前端 POST /api/name 提交的 JSON 数据
 */
public class NameRequest {

    /** 姓氏，例如：张 */
    @NotBlank(message = "姓氏不能为空")
    private String surname;

    /** 性别，例如：男 / 女 */
    @NotBlank(message = "性别不能为空")
    private String gender;

    /** 出生日期，例如：2024-06-18 */
    private String birthday;

    /** 出生时辰，例如：辰时 */
    private String birthTime;

    /** 期望寓意，例如：健康、阳光、积极向上 */
    private String meaning;

    /** 其他要求，例如：不要生僻字，希望名字朗朗上口 */
    private String other;

    public NameRequest() {
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(String birthTime) {
        this.birthTime = birthTime;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return "NameRequest{" +
                "surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday='" + birthday + '\'' +
                ", birthTime='" + birthTime + '\'' +
                ", meaning='" + meaning + '\'' +
                ", other='" + other + '\'' +
                '}';
    }
}
