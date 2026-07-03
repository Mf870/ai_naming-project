package com.example.ainaming.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * 系统用户实体
 * 映射数据库表 sys_user，用于登录校验
 */
@Entity
@Table(name = "sys_user", uniqueConstraints = @UniqueConstraint(columnNames = "user_id"))
public class SysUser {

    /** 主键 ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 登录账号 */
    @Column(name = "user_id", length = 50, nullable = false)
    private String userId;

    /** 登录密码（当前为明文存储，仅用于教学/演示，生产环境务必改为加密存储，例如 BCrypt） */
    @Column(length = 100, nullable = false)
    private String password;

    public SysUser() {
    }

    public SysUser(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
