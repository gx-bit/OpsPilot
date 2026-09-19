import pytest
from app.rules import classify, priority


@pytest.mark.parametrize(("text", "expected"), [
    ("实验室无法联网，但其他设备正常", "NETWORK"),
    ("学生账号登录失败", "ACCOUNT"),
    ("软件安装后一直闪退", "SOFTWARE"),
    ("显示器无法点亮", "HARDWARE"),
    ("生产服务器磁盘告警", "SERVER"),
    ("需要一般咨询", "OTHER"),
])
def test_classify(text, expected):
    assert classify(text) == expected


def test_wide_impact_is_urgent():
    assert priority("生产服务器全部不可用") == "URGENT"


def test_multiple_devices_is_high():
    assert priority("十几台电脑无法访问") == "HIGH"

