package com.example.ainaming.config;

import com.example.ainaming.entity.SysUser;
import com.example.ainaming.repository.SysUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 数据初始化器
 * 项目启动时检查 sys_user 表，如果表里一个用户都没有，
 * 就自动插入一个默认账号，方便你第一次直接登录测试，不用手动写 SQL 插数据。
 *
 * 默认账号：admin
 * 默认密码：admin123
 *
 * 生产环境建议删除本类，改为通过正式的注册流程创建账号。
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final SysUserRepository sysUserRepository;

    public DataInitializer(SysUserRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }

    @Override
    public void run(String... args) {
        if (sysUserRepository.count() == 0) {
            sysUserRepository.save(new SysUser("admin", "admin123"));
            log.info("检测到用户表为空，已自动创建默认账号 -> 账号: admin  密码: admin123");
        }
    }
}
