package com.example.ainaming.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 注册请求 DTO
 * 对应前端注册页提交的 { "userId": "...", "password": "..." }
 */
public class RegisterRequest {

    @NotBlank(message = "账号不能为空")
    @Size(min = 3, max = 20, message = "账号长度需在 3-20 个字符之间")
    private String userId;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度需在 6-20 个字符之间")
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
