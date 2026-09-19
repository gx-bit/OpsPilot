# OpsPilot

OpsPilot 是一个面向校园实验室和中小团队的智能 IT 服务台。Java 服务负责工单、状态流转、资产和审计等确定性业务；Python 服务负责分诊、RAG 和 Agent 工具编排；Vue 负责用户界面。

## 一分钟启动

前置条件：Docker Desktop 已启动。

```powershell
Copy-Item .env.example .env
docker compose up --build
```

打开：

- Web: http://localhost:5173
- Java API/Swagger: http://localhost:8080/swagger-ui.html
- Python Agent API: http://localhost:8000/docs
- RabbitMQ: http://localhost:15672 (`guest` / `guest`)
- MinIO: http://localhost:9001 (`minioadmin` / `minioadmin`)

没有配置 `LLM_API_KEY` 时，Agent 使用可复现的规则降级模式，整条业务链仍可演示。配置兼容 OpenAI API 的模型后，Agent 会使用 LangGraph 工作流和模型生成诊断建议。

## 核心演示

1. 在 Web 提交自然语言工单。
2. Java 保存工单并调用 Python Agent。
3. Agent 调用 Java 内部工具查询知识库和相似工单。
4. Agent 返回分类、优先级、处理组、摘要和建议。
5. Java 更新工单并保存 Agent 审计记录。

## 项目结构

```text
business-service/   Spring Boot 业务服务
agent-service/      FastAPI + LangGraph Agent
web/                Vue 3 前端
docs/               架构、学习路线和接口说明
infra/              数据库初始化与基础设施配置
```

## 常用命令

```powershell
# 基础设施
docker compose up -d postgres redis rabbitmq minio

# Java 测试
Set-Location business-service
mvn test

# Python 测试（推荐 Python 3.12）
Set-Location agent-service
python -m venv .venv
.venv\Scripts\pip install -e ".[dev]"
.venv\Scripts\pytest

# 前端
Set-Location web
npm install
npm run dev
```

详细学习计划见 [docs/LEARNING_ROADMAP.md](docs/LEARNING_ROADMAP.md)。

