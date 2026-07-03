package com.example.ainaming.util;

import com.example.ainaming.dto.NameRequest;

/**
 * Prompt 构造工具类
 * 根据用户输入的取名信息，拼接发送给 DeepSeek 的 System Prompt 与 User Prompt
 */
public class PromptBuilder {

    private PromptBuilder() {
        // 工具类禁止实例化
    }

    /**
     * 构造 System Prompt，用于设定 AI 的角色定位
     */
    public static String buildSystemPrompt() {
        return "你是一位专业的中文取名大师，精通中国传统文化、姓名学与现代审美，" +
                "擅长根据姓氏、性别、生辰八字与父母期望为孩子取一个寓意美好、朗朗上口的中文名字。";
    }

    /**
     * 根据用户请求参数构造 User Prompt
     *
     * @param request 用户提交的取名请求
     * @return 完整的用户提示词
     */
    public static String buildUserPrompt(NameRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("请作为专业中文取名大师。\n");
        sb.append("根据以下信息：\n");
        sb.append("姓氏：").append(nullToEmpty(request.getSurname())).append("\n");
        sb.append("性别：").append(nullToEmpty(request.getGender())).append("\n");
        sb.append("生日：").append(nullToEmpty(request.getBirthday())).append("\n");
        sb.append("出生时辰：").append(nullToEmpty(request.getBirthTime())).append("\n");
        sb.append("期望寓意：").append(nullToEmpty(request.getMeaning())).append("\n");
        sb.append("其他要求：").append(nullToEmpty(request.getOther())).append("\n");
        sb.append("\n");
        sb.append("请推荐 5 个中文名字。\n");
        sb.append("要求：\n");
        sb.append("1. 每个名字都要符合中文命名习惯。\n");
        sb.append("2. 不使用生僻字。\n");
        sb.append("3. 每个名字都要给出寓意。\n");
        sb.append("4. 给出推荐理由。\n");
        sb.append("5. 返回严格 JSON，不要包含任何多余文字、注释或 Markdown 代码块标记。\n");
        sb.append("\n");
        sb.append("返回格式如下（严格按此 JSON 结构输出）：\n");
        sb.append("{\n");
        sb.append("  \"names\": [\n");
        sb.append("    {\n");
        sb.append("      \"name\": \"张云泽\",\n");
        sb.append("      \"meaning\": \"寓意胸怀宽广，福泽深厚。\",\n");
        sb.append("      \"reason\": \"名字读音流畅，寓意积极。\",\n");
        sb.append("      \"score\": 96\n");
        sb.append("    }\n");
        sb.append("  ]\n");
        sb.append("}\n");
        return sb.toString();
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }
}
