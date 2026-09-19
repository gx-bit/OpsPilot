from .models import Category, Priority

CATEGORY_RULES: dict[Category, tuple[str, ...]] = {
    "NETWORK": ("网络", "联网", "打不开", "dns", "路由", "教务系统", "wifi"),
    "ACCOUNT": ("账号", "登录", "密码", "权限", "认证"),
    "SOFTWARE": ("软件", "安装", "崩溃", "闪退", "许可证"),
    "HARDWARE": ("电脑", "显示器", "键盘", "硬盘", "开不了机"),
    "SERVER": ("服务器", "磁盘", "cpu", "内存", "服务", "告警"),
    "OTHER": (),
}


def classify(text: str) -> Category:
    lowered = text.lower()
    scores = {category: sum(word in lowered for word in words)
              for category, words in CATEGORY_RULES.items()}
    best = max(scores, key=scores.get)
    return best if scores[best] else "OTHER"


def priority(text: str) -> Priority:
    lowered = text.lower()
    if any(word in lowered for word in ("全部", "全校", "生产", "紧急", "立即")):
        return "URGENT"
    if any(word in lowered for word in ("多人", "十几台", "上课前", "无法工作", "告警")):
        return "HIGH"
    if any(word in lowered for word in ("偶尔", "不影响", "咨询")):
        return "LOW"
    return "MEDIUM"


def group(category: Category) -> str:
    return {
        "NETWORK": "网络运维组", "ACCOUNT": "账号权限组", "SOFTWARE": "桌面支持组",
        "HARDWARE": "设备维修组", "SERVER": "服务器运维组", "OTHER": "综合服务台",
    }[category]

