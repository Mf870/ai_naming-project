package com.example.ainaming.service.impl;

import com.example.ainaming.dto.LoginRequest;
import com.example.ainaming.dto.RegisterRequest;
import com.example.ainaming.entity.SysUser;
import com.example.ainaming.exception.BusinessException;
import com.example.ainaming.repository.SysUserRepository;
import com.example.ainaming.service.AuthService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 登录 / 注册 Service 实现类
 * 当前使用明文密码比对与存储，仅适用于教学/演示场景。
 * 生产环境建议改为 BCrypt 等加密算法存储和校验密码。
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final SysUserRepository sysUserRepository;

    public AuthServiceImpl(SysUserRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }

    @Override
    public void login(LoginRequest request) {
        Optional<SysUser> userOptional = sysUserRepository.findByUserId(request.getUserId());

        if (userOptional.isEmpty()) {
            throw new BusinessException(400, "账号不存在");
        }

        SysUser user = userOptional.get();
        if (!user.getPassword().equals(request.getPassword())) {
            throw new BusinessException(400, "密码错误");
        }
        // 校验通过，不抛异常即代表登录成功
    }

    @Override
    public void register(RegisterRequest request) {
        // 先查询数据库中是否已存在相同账号，避免重复注册
        boolean exists = sysUserRepository.findByUserId(request.getUserId()).isPresent();
        if (exists) {
            throw new BusinessException(400, "该账号已被注册，请更换一个账号");
        }

        // 查重通过，写入新账号
        SysUser user = new SysUser(request.getUserId(), request.getPassword());
        try {
            sysUserRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            // 极端情况下（两个请求几乎同时提交，都通过了上面的查重），
            // 依赖数据库的唯一约束（user_id 列的 UniqueConstraint）做最终兜底
            throw new BusinessException(400, "该账号已被注册，请更换一个账号");
        }
    }
}
