# AI 智能取名项目（Java 17 + Spring Boot 3）

对接 DeepSeek 官方 Chat Completion API（`https://api.deepseek.com/chat/completions`），
根据用户提供的姓氏、性别、生辰等信息，AI 生成 5 个推荐中文名字。

## 目录结构

```
com.example.ainaming
├── controller     # NameController：接收前端请求
├── service        # NameService 接口 + impl 实现（Prompt 构造、调用 DeepSeek、解析结果）
├── dto            # 前端交互 DTO + DeepSeek 请求/响应 DTO
├── config         # DeepSeekConfig（读取配置）、WebClientConfig（构造 WebClient）
├── entity         # NameRecord：预留持久化扩展
├── exception       # BusinessException + 全局异常处理器
└── util           # PromptBuilder（拼接提示词）、JsonContentExtractor（清理模型返回内容）
```

## 运行前准备

1. 安装 JDK 17、Maven 3.8+，本地装好 MySQL 并创建数据库 `ainaming`
2. 配置密钥（配置文件中不再包含明文密钥，需要通过环境变量注入）：
   - `DEEPSEEK_API_KEY`：你的 DeepSeek API Key
   - `DB_PASSWORD`：你的 MySQL root 密码

   **命令行运行**，可以在启动前设置环境变量：
   ```bash
   set DEEPSEEK_API_KEY=你的key
   set DB_PASSWORD=你的数据库密码
   mvn spring-boot:run
   ```

   **在 IDEA 里运行**：
   - 点顶部运行配置下拉框 → `Edit Configurations...`
   - 找到 `Environment variables` 一栏，点右侧的图标展开编辑
   - 添加两行：`DEEPSEEK_API_KEY=你的key` 和 `DB_PASSWORD=你的数据库密码`
   - 保存后再点运行

   ⚠️ 注意：`.gitignore` 已排除 `target/`、`.idea/` 等文件，但**环境变量本身不会被提交到 Git**，每台电脑/每个人首次运行前都需要自己设置一次。

## 启动项目

```bash
mvn clean spring-boot:run
```

默认监听 `http://localhost:8080`

## 调用示例

```bash
curl -X POST http://localhost:8080/api/name \
  -H "Content-Type: application/json" \
  -d '{
    "surname": "张",
    "gender": "男",
    "birthday": "2024-06-18",
    "birthTime": "辰时",
    "meaning": "健康、阳光、积极向上",
    "other": "不要生僻字，希望名字朗朗上口"
  }'
```

## 返回示例

```json
{
  "code": 200,
  "msg": "success",
  "data": {
    "names": [
      {
        "name": "张云泽",
        "meaning": "寓意胸怀宽广，福泽深厚。",
        "reason": "名字读音流畅，寓意积极。",
        "score": 96
      }
    ]
  }
}
```

## 说明

- 使用 Spring Boot 3 的 `WebClient`（基于 WebFlux）直接调用 DeepSeek 官方 REST 接口，未使用任何第三方 SDK。
- API Key 通过 `application.yml` 的 `deepseek.api-key` 配置项读取（支持环境变量覆盖），不写死在代码中。
- `JsonContentExtractor` 用于清理模型可能附带的 ```json 代码块标记，提高 JSON 解析成功率。
- 全局异常统一由 `GlobalExceptionHandler` 捕获，返回 `{ code, msg, data }` 格式，避免前端拿到堆栈信息。
