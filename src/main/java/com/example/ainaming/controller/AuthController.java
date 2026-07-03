package com.example.ainaming.controller;

import com.example.ainaming.dto.ApiResult;
import com.example.ainaming.dto.LoginRequest;
import com.example.ainaming.dto.RegisterRequest;
import com.example.ainaming.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录 / 注册 Controller
 * 提供 POST /api/login 和 POST /api/register 接口
 */
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ApiResult<Void> login(@Valid @RequestBody LoginRequest request) {
        // 登录成功不会抛异常；失败会被 GlobalExceptionHandler 捕获并转换为标准错误返回
        authService.login(request);
        return ApiResult.success(null);
    }

    @PostMapping("/register")
    public ApiResult<Void> register(@Valid @RequestBody RegisterRequest request) {
        // 内部会先查询账号是否已存在，存在则抛出异常并被全局异常处理器捕获
        authService.register(request);
        return ApiResult.success(null);
    }
}
