package com.example.ainaming.util;

/**
 * JSON 内容提取工具类
 * 大模型有时会在返回的 JSON 前后附带 ```json ... ``` 这样的 Markdown 代码块标记，
 * 或者附带多余的说明文字。该工具类用于从原始文本中提取出纯净的 JSON 字符串，
 * 提高后续 Jackson 解析的成功率。
 */
public class JsonContentExtractor {

    private JsonContentExtractor() {
        // 工具类禁止实例化
    }

    /**
     * 从模型返回的原始文本中提取出 JSON 字符串
     *
     * @param rawContent 模型返回的原始内容（choices[0].message.content）
     * @return 清理后的 JSON 字符串
     */
    public static String extractJson(String rawContent) {
        if (rawContent == null) {
            return null;
        }

        String content = rawContent.trim();

        // 去除 ```json 或 ``` 包裹的代码块标记
        if (content.startsWith("```")) {
            // 去掉开头的 ```json 或 ```
            int firstNewLine = content.indexOf('\n');
            if (firstNewLine != -1) {
                content = content.substring(firstNewLine + 1);
            }
            // 去掉结尾的 ```
            int lastFence = content.lastIndexOf("```");
            if (lastFence != -1) {
                content = content.substring(0, lastFence);
            }
        }

        content = content.trim();

        // 兜底：截取第一个 { 到最后一个 } 之间的内容，避免模型输出多余说明文字
        int start = content.indexOf('{');
        int end = content.lastIndexOf('}');
        if (start != -1 && end != -1 && end > start) {
            content = content.substring(start, end + 1);
        }

        return content;
    }
}
