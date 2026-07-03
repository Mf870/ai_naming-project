package com.example.ainaming.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 * 项目已将前端页面放入 src/main/resources/static 由后端同源托管，
 * 通常不会遇到跨域问题；但为了兼容前端单独部署（例如用 VSCode Live Server、
 * 其他端口或域名访问）的场景，这里统一开放 /api/** 路径的跨域访问。
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                // 允许所有来源访问（生产环境建议改为白名单，例如具体的前端域名）
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
    }
}
