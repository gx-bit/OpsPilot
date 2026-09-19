CREATE TABLE ticket (
  id BIGSERIAL PRIMARY KEY,
  title VARCHAR(160) NOT NULL,
  description TEXT NOT NULL,
  status VARCHAR(32) NOT NULL,
  category VARCHAR(32) NOT NULL,
  priority VARCHAR(32) NOT NULL,
  assigned_group VARCHAR(80),
  ai_summary TEXT,
  ai_suggestion TEXT,
  created_at TIMESTAMPTZ NOT NULL,
  updated_at TIMESTAMPTZ NOT NULL,
  version BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE knowledge_article (
  id BIGSERIAL PRIMARY KEY,
  title VARCHAR(200) NOT NULL,
  content TEXT NOT NULL,
  category VARCHAR(32) NOT NULL,
  visibility VARCHAR(32) NOT NULL DEFAULT 'PUBLIC'
);

CREATE TABLE agent_run (
  id UUID PRIMARY KEY,
  ticket_id BIGINT NOT NULL REFERENCES ticket(id),
  mode VARCHAR(32) NOT NULL,
  status VARCHAR(32) NOT NULL,
  input_json TEXT NOT NULL,
  output_json TEXT,
  error_message TEXT,
  duration_ms BIGINT,
  created_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_ticket_status_created ON ticket(status, created_at DESC);
CREATE INDEX idx_ticket_category ON ticket(category);

INSERT INTO knowledge_article(title, content, category) VALUES
('仅教务系统无法访问的排查步骤', '先确认其他网站是否正常，再检查 DNS 解析和到教务系统的路由；收集受影响实验室、设备数量与开始时间。', 'NETWORK'),
('账号无法登录的处理规范', '确认账号状态、密码错误次数与权限有效期。重置密码前必须验证用户身份。', 'ACCOUNT'),
('磁盘空间告警处理', '先查询磁盘使用率与大文件，不得直接删除未知文件。涉及生产服务器的清理操作必须审批。', 'SERVER');

