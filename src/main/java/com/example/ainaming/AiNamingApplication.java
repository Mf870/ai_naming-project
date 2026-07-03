package com.example.ainaming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * AI 智能取名项目启动类
 *
 * 项目功能：接收用户输入的姓氏、性别、生辰等信息，
 * 调用 DeepSeek Chat Completion API 生成推荐名字，并以统一格式返回给前端。
 */
@SpringBootApplication
// 开启 @ConfigurationProperties 扫描，用于加载 DeepSeekConfig 等配置类
@ConfigurationPropertiesScan
public class AiNamingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiNamingApplication.class, args);
    }

}
