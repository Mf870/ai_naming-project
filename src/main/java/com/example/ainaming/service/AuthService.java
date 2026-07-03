package com.example.ainaming.service;

import com.example.ainaming.dto.LoginRequest;
import com.example.ainaming.dto.RegisterRequest;

/**
 * 登录 / 注册 Service 接口
 */
public interface AuthService {

    /**
     * 校验账号密码
     *
     * @param request 登录请求参数
     * @throws com.example.ainaming.exception.BusinessException 账号不存在或密码错误时抛出
     */
    void login(LoginRequest request);

    /**
     * 注册新账号
     * 会先查询数据库中是否已存在相同账号，存在则抛出异常拒绝注册
     *
     * @param request 注册请求参数
     * @throws com.example.ainaming.exception.BusinessException 账号已存在时抛出
     */
    void register(RegisterRequest request);
}
