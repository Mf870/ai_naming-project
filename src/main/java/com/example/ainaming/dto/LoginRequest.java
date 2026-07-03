package com.example.ainaming.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 登录请求 DTO
 * 对应前端登录页提交的 { "userId": "...", "password": "..." }
 */
public class LoginRequest {

    @NotBlank(message = "账号不能为空")
    private String userId;

    @NotBlank(message = "密码不能为空")
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
