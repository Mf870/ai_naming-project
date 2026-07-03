package com.example.ainaming.repository;

import com.example.ainaming.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 用户数据访问接口
 */
public interface SysUserRepository extends JpaRepository<SysUser, Long> {

    /**
     * 根据登录账号查询用户
     * Spring Data JPA 会根据方法名自动生成对应的 SQL，无需手写
     */
    Optional<SysUser> findByUserId(String userId);
}
