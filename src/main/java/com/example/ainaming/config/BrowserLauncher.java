package com.example.ainaming.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.awt.Desktop;
import java.net.URI;

/**
 * 启动完成后自动打开浏览器
 * 监听 Spring Boot 的 ApplicationReadyEvent（应用完全就绪事件），
 * 就绪后自动用系统默认浏览器打开首页，省去手动输入地址的步骤。
 *
 * 注意：
 * 1. 仅在图形界面环境下生效（例如本地开发用的 Windows/Mac 电脑），
 *    如果部署在没有图形界面的服务器上，会自动跳过，不会报错。
 * 2. 如果不想要这个功能，直接删除本文件即可，不影响其他功能。
 */
@Component
public class BrowserLauncher implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(BrowserLauncher.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // 从配置中读取实际使用的端口（避免端口写死，端口被占用自动换端口时也能正确打开）
        Environment env = event.getApplicationContext().getEnvironment();
        String port = env.getProperty("local.server.port", env.getProperty("server.port", "8080"));
        String url = "http://localhost:" + port;

        // 图形界面环境检测：不支持则跳过（例如在 Linux 服务器上部署时）
        if (!Desktop.isDesktopSupported() || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            log.info("当前环境不支持自动打开浏览器，请手动访问：{}", url);
            return;
        }

        try {
            Desktop.getDesktop().browse(new URI(url));
            log.info("已自动打开浏览器：{}", url);
        } catch (Exception e) {
            // 自动打开失败不应影响服务正常运行，仅记录日志提示手动访问
            log.warn("自动打开浏览器失败，请手动访问：{}，原因：{}", url, e.getMessage());
        }
    }
}
