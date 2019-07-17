# 说明
这是华为RTC Demo

# 样例

## 给指定号码发送短信验证码
curl http://localhost:8080/sms?receiver=%2B86133XXXXXXXX

## 隐私保护通话AX模式
curl http://localhost:8080/ax?A=%2B86133XXXXXXXX\&X=%2B86133XXXXXXXX

## 隐私保护通话AXB模式
curl http://localhost:8080/axb?A=%2B86133XXXXXXXX\&X=%2B86133XXXXXXXX\&B=%2B86133XXXXXXXX