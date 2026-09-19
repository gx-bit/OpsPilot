# OpsPilot 项目驱动学习路线

不要先学完全部技术再做项目。采用“学一个最小知识点，立刻在项目里交付一个功能”的方式。建议每周 20～25 小时，共 16 周。

## 第 0 阶段：开发习惯（2 天）

学习：Git 基础、Markdown、命令行、HTTP 基本概念。

交付：能够克隆项目、创建分支、提交代码；能用 Docker Compose 启动项目；用 Swagger 调一次接口。

验收：不看教程完成 `clone → branch → commit → push`；能解释 GET、POST、状态码和 JSON。

## 第 1 阶段：Java 基础（第 1～2 周）

学习顺序：类与对象 → 接口和多态 → 集合与泛型 → 异常 → Stream/Lambda → 注解与反射概念 → Maven → JUnit。

项目任务：

1. 为工单增加 `category` 字段。
2. 增加按状态筛选接口。
3. 为状态流转编写参数化测试。
4. 使用异常处理器统一错误响应。

验收：能解释 `List/Set/Map` 的选择、泛型的作用、受检异常与运行时异常、依赖注入为什么有利于测试。

## 第 2 阶段：Spring Boot 与数据库（第 3～4 周）

学习顺序：分层架构 → REST → IoC/AOP → Bean Validation → JPA → SQL → 事务 → Flyway。

项目任务：

1. 完成用户、部门、角色和 RBAC。
2. 完成工单评论和操作日志。
3. 实现工单状态机与乐观锁。
4. 添加分页、排序和组合查询。

验收：能解释事务边界、N+1 查询、索引、乐观锁、为什么 Controller 不应写业务规则。

## 第 3 阶段：工程中间件（第 5～6 周）

学习顺序：Redis → 缓存一致性 → RabbitMQ → 消息可靠性 → MinIO/S3 → 定时任务 → 日志和监控。

项目任务：

1. Redis 保存登录会话和限流计数。
2. RabbitMQ 异步发送工单通知。
3. MinIO 保存附件。
4. 实现 SLA 超时扫描和告警。
5. 接入 Prometheus 指标。

验收：能解释缓存穿透/击穿、消息重复消费、幂等、死信队列、最终一致性。

## 第 4 阶段：Python 基础（第 7～8 周）

学习顺序：变量和容器 → 函数 → 类 → 类型标注 → 异常 → 模块/包 → 虚拟环境 → pytest → asyncio → HTTP 客户端。

项目任务：

1. 阅读并重写 `rules.py` 的关键词规则。
2. 给规则分诊编写 30 个 pytest 用例。
3. 使用 Pydantic 定义输入输出。
4. 新增一个调用 Java 的只读 Tool。

验收：能解释 Python 的 `list/dict/set/tuple`、生成器、装饰器基本用途、同步与异步、Pydantic 校验。

## 第 5 阶段：FastAPI 与服务通信（第 9 周）

学习顺序：FastAPI 路由 → Pydantic → 依赖注入 → async → httpx → 超时/重试 → 服务鉴权。

项目任务：

1. 增加 Agent 运行查询接口。
2. 为内部 Tool API 增加签名或短期 Token。
3. 实现超时、重试和熔断。
4. 用契约测试保证 Java/Python DTO 一致。

验收：能够解释为什么 Agent 不直接连接业务数据库，以及网络失败后的处理策略。

## 第 6 阶段：LLM 基础（第 10 周）

学习顺序：Token → System/User 消息 → Temperature → Structured Output → Tool Calling → 上下文窗口 → 成本和延迟。

项目任务：

1. 配置一个兼容 OpenAI API 的模型。
2. 将自由文本输出改成 Pydantic 结构化输出。
3. 记录 Token、耗时与估算成本。
4. 增加模型失败时的规则降级。

验收：能说明模型为什么会幻觉、Temperature 不能解决什么、为什么业务层必须再次校验。

## 第 7 阶段：RAG（第 11～12 周）

学习顺序：Embedding → 文档解析 → Chunking → 向量相似度 → pgvector/HNSW → 混合检索 → Rerank → 引用。

项目任务：

1. 上传并解析 Markdown/PDF 运维文档。
2. 保存 chunk 与 embedding。
3. 实现关键词 + 向量混合检索。
4. 添加部门与可见性过滤。
5. 返回引用文档和段落。

验收：构建至少 100 个问题的检索集，计算 Recall@5，并能说明 chunk 大小和召回率之间的关系。

## 第 8 阶段：LangGraph Agent（第 13～14 周）

学习顺序：状态图 → 节点/边 → Tool Calling → Checkpoint → 重试 → Human-in-the-loop → 防 Prompt Injection。

项目任务：

1. 将当前分诊图扩展为 `补充信息→检索→规划→执行→验证`。
2. 工具按读、写、高风险分类。
3. 高风险工具创建审批单并暂停图。
4. 审批后从 checkpoint 恢复。
5. 限制最多工具次数和总预算。

验收：演示恶意知识文档不能诱导 Agent 越权关闭工单。

## 第 9 阶段：前端（第 15 周）

学习顺序：HTML/CSS/JavaScript → TypeScript → Vue 组件 → Router → Pinia → Axios → SSE。

项目任务：

1. 登录和权限菜单。
2. 工单列表与详情。
3. Agent 执行时间线。
4. 审批页面。
5. 指标看板。

验收：能够展示加载、空数据、失败和重试状态，而不仅是成功页面。

## 第 10 阶段：测试、部署与求职包装（第 16 周）

学习：Testcontainers、端到端测试、Docker、多阶段构建、CI、基础 Linux、监控、安全检查。

交付：

- Java/Python 单元测试和集成测试
- GitHub Actions CI
- 100～200 条 Agent 评测集
- 架构图、ER 图、接口文档
- 3 分钟演示视频
- 一份说明设计取舍的技术文章

## 每日学习循环

1. 45 分钟学习一个概念。
2. 90 分钟在项目中实现最小功能。
3. 30 分钟写测试。
4. 15 分钟记录今天遇到的问题与设计选择。
5. 每周末脱离教程重新实现本周核心功能。

衡量是否学会的标准不是“看完课程”，而是能独立实现、测试、解释并排查故障。

