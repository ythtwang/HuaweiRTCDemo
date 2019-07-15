package com.ythtwang.rtc.model.pns;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 呼叫事件通知/话单通知/短信通知
 * 客户平台收到隐私保护通话平台的呼叫事件通知/话单通知/短信通知,可按如下样例解析处理
 */
public class HostingVoiceEventImpl {
    private static Logger logger = LogManager.getLogger(HostingVoiceEventImpl.class);

    /**
     * 呼叫事件 for AXB/AX/X/AXE/AXYB
     *
     * @param jsonBody
     * @breif 详细内容以接口文档为准
     */
    public void onCallEvent(String jsonBody) {
        // 封装JOSN请求
        JSONObject json = JSON.parseObject(jsonBody);
        String eventType = json.getString("eventType"); // 通知事件类型

        if ("fee".equalsIgnoreCase(eventType)) {
            logger.info("EventType error: " + eventType);
            return;
        }

        JSONObject statusInfo = json.getJSONObject("statusInfo"); // 呼叫状态事件信息

        logger.info("eventType: " + eventType); // 打印通知事件类型
        //callin：呼入事件
        if ("callin".equalsIgnoreCase(eventType)) {
            /**
             * Example: 此处以解析notifyMode为例,请按需解析所需参数并自行实现相关处理
             *
             * 'timestamp': 呼叫事件发生时隐私保护通话平台的UNIX时间戳
             * 'sessionId': 通话链路的标识ID
             * 'caller': 主叫号码
             * 'called': 被叫号码
             * 'subscriptionId': 绑定关系ID,AXE/X模式不携带该参数
             * 'notifyMode': 通知模式,仅X模式场景携带
             */
            String callNotifyMode = statusInfo.getString("notifyMode"); // 通知模式,仅X模式场景携带

            if ("Block".equalsIgnoreCase(callNotifyMode)) {
                JSONObject resp = new JSONObject();

                /*
                 * section1: 接续被叫通话
                 */
                resp.put("operation", "connect"); // 用于指示平台的呼叫操作
                JSONArray connectInfoArr = new JSONArray();
                JSONObject connectInfo = new JSONObject();
                connectInfo.put("displayCalleeNum", "+8613400000001"); // 被叫端的来显号码
                connectInfo.put("calleeNum", "+8613400000001"); // 真实被叫号码
                connectInfo.put("maxDuration", 60); // 单次通话最长时间
                connectInfo.put("waitVoice", "waitVoice001.wav"); // 个性化通话前等待音
                connectInfo.put("recordFlag", "true"); // 是否通话录音
                connectInfo.put("recordHintTone", "recordHintTone001.wav"); // 录音提示音
                connectInfo.put("lastMinVoice", "lastMinVoice001.wav"); // 通话最后一分钟提示音
                connectInfo.put("userData", "userflag001"); // 用户自定义数据
                connectInfoArr.add(connectInfo);
                resp.put("connectInfo", connectInfoArr); // 指示平台接续被叫通话的参数列表

                /*
                 * section2: 结束会话
                 */
//                resp.put("operation", "close");
//                JSONArray closeInfoArr = new JSONArray();
//                JSONObject closeInfo = new JSONObject();
//                closeInfo.put("closeHintTone", "closeHintTone001.wav"); // 挂机提示音
//                closeInfo.put("userData", "userflag001"); // 用户自定义数据
//                closeInfoArr.add(closeInfo);
//                resp.put("closeInfo", closeInfoArr); // 指示平台结束会话的参数列表

                logger.info("resp: " + resp);
            }
            return;
        }
        //collectInfo：放音收号结果事件,仅AXE模式下的A被叫场景携带
        if ("collectInfo".equalsIgnoreCase(eventType)) {
            /**
             * Example: 此处以解析digitInfo为例,请按需解析所需参数并自行实现相关处理
             *
             * 'timestamp': 呼叫事件发生时隐私保护通话平台的UNIX时间戳
             * 'sessionId': 通话链路的标识ID
             * 'digitInfo': AXE场景中携带收号结果(即用户输入的数字)
             */
            String digitInfo = statusInfo.getString("digitInfo");
            logger.info("digitInfo: " + digitInfo);
            return;
        }
        //callout：呼出事件
        if ("callout".equalsIgnoreCase(eventType)) {
            /**
             * Example: 此处以解析sessionId为例,请按需解析所需参数并自行实现相关处理
             *
             * 'timestamp': 呼叫事件发生时隐私保护通话平台的UNIX时间戳
             * 'userData': 用户附属信息,仅X模式场景携带
             * 'sessionId': 通话链路的标识ID
             * 'caller': 主叫号码
             * 'called': 被叫号码
             * 'subscriptionId': 绑定关系ID
             */
            String sessionId = statusInfo.getString("sessionId");
            logger.info("sessionId: " + sessionId);
            return;
        }
        //alerting：振铃事件
        if ("alerting".equalsIgnoreCase(eventType)) {
            /**
             * Example: 此处以解析sessionId为例,请按需解析所需参数并自行实现相关处理
             *
             * 'timestamp': 呼叫事件发生时隐私保护通话平台的UNIX时间戳
             * 'userData': 用户附属信息,仅X模式场景携带
             * 'sessionId': 通话链路的标识ID
             * 'caller': 主叫号码
             * 'called': 被叫号码
             * 'subscriptionId': 绑定关系ID
             */
            String sessionId = statusInfo.getString("sessionId");
            logger.info("sessionId: " + sessionId);
            return;
        }
        //answer：应答事件
        if ("answer".equalsIgnoreCase(eventType)) {
            /**
             * Example: 此处以解析sessionId为例,请按需解析所需参数并自行实现相关处理
             *
             * 'timestamp': 呼叫事件发生时隐私保护通话平台的UNIX时间戳
             * 'userData': 用户附属信息,仅X模式场景携带
             * 'sessionId': 通话链路的标识ID
             * 'caller': 主叫号码
             * 'called': 被叫号码
             * 'subscriptionId': 绑定关系ID
             */
            String sessionId = statusInfo.getString("sessionId");
            logger.info("sessionId: " + sessionId);
            return;
        }
        //disconnect：挂机事件
        if ("disconnect".equalsIgnoreCase(eventType)) {
            /**
             * Example: 此处以解析sessionId为例,请按需解析所需参数并自行实现相关处理
             *
             * 'timestamp': 呼叫事件发生时隐私保护通话平台的UNIX时间戳
             * 'userData': 用户附属信息,仅X模式场景携带
             * 'sessionId': 通话链路的标识ID
             * 'caller': 主叫号码
             * 'called': 被叫号码
             * 'stateCode': 通话挂机的原因值
             * 'stateDesc': 通话挂机的原因值的描述
             * 'subscriptionId': 绑定关系ID
             */
            String sessionId = statusInfo.getString("sessionId");
            logger.info("sessionId: " + sessionId);
            return;
        }
    }

    /**
     * 话单通知 for AXB/AX/X/AXE/AXYB
     *
     * @param jsonBody
     * @breif 详细内容以接口文档为准
     */
    public void onFeeEvent(String jsonBody) {
        // 封装JSON请求
        JSONObject json = JSON.parseObject(jsonBody);
        String eventType = json.getString("eventType"); // 通知事件类型

        if (!("fee".equalsIgnoreCase(eventType))) {
            logger.info("EventType error: " + eventType);
            return;
        }

        JSONArray feeLst = json.getJSONArray("feeLst"); // 呼叫话单事件信息

        /**
         * Example: 此处以解析notifyMode为例,请按需解析所需参数并自行实现相关处理
         *
         * 'direction': 通话的呼叫方向
         * 'spId': 客户的云服务账号
         * 'appKey': 商户应用的AppKey
         * 'icid': 呼叫记录的唯一标识
         * 'bindNum': 隐私保护号码
         * 'sessionId': 通话链路的唯一标识
         * 'callerNum': 主叫号码
         * 'calleeNum': 被叫号码
         * 'fwdDisplayNum': 转接呼叫时的显示号码
         * 'fwdDstNum': 转接呼叫时的转接号码
         * 'callInTime': 呼入的开始时间
         * 'fwdStartTime': 转接呼叫操作的开始时间
         * 'fwdAlertingTime': 转接呼叫操作后的振铃时间
         * 'fwdAnswerTime': 转接呼叫操作后的应答时间
         * 'callEndTime': 呼叫结束时间
         * 'fwdUnaswRsn': 转接呼叫操作失败的Q850原因值
         * 'failTime': 呼入,呼出的失败时间
         * 'ulFailReason': 通话失败的拆线点
         * 'sipStatusCode': 呼入,呼出的失败SIP状态码
         * 'recordFlag': 录音标识
         * 'recordStartTime': 录音开始时间
         * 'recordObjectName': 录音文件名
         * 'recordBucketName': 录音文件所在的目录名
         * 'recordDomain': 存放录音文件的域名
         * 'serviceType': 携带呼叫的业务类型信息
         * 'hostName': 话单生成的服务器设备对应的主机名
         * 'subscriptionId': 绑定关系ID
         *
         * 'userData': 用户附属信息,该参数仅在X模式场景携带
         * 'notifyMode': 该参数仅在X模式场景携带,固定取值为Block,用于标识该话单为X模式话单
         *
         * 'extendNum': 分机号E,该参数仅在AXE模式场景携带
         */
        // 短时间内有多个通话结束时隐私保护通话平台会将话单合并推送,每条消息最多携带50个话单
        if (feeLst.size() > 1) {
            for (Object loop : feeLst) {
                if (((JSONObject) loop).containsKey("sessionId")) {
                    logger.info("sessionId: " + ((JSONObject) loop).getString("sessionId"));
                }
            }
        } else if (feeLst.size() == 1) {
            if (feeLst.getJSONObject(0).containsKey("sessionId")) {
                logger.info("sessionId: " + feeLst.getJSONObject(0).getString("sessionId"));
            }
        } else {
            logger.info("feeLst error: no element.");
        }
    }

    /**
     * 短信通知 for AXB/AX/X
     *
     * @param jsonBody
     * @breif 详细内容以接口文档为准
     */
    public void onSmsEvent(String jsonBody) {
        // 封装JOSN请求
        JSONObject json = JSON.parseObject(jsonBody);
        // String appKey = json.getString("appKey"); // 商户应用的AppKey

        JSONObject smsEvent = json.getJSONObject("smsEvent"); // 短信通知信息

        /**
         * Example: 此处以解析notificationMode为例,请按需解析所需参数并自行实现相关处理
         *
         * 'smsIdentifier': 短信唯一标识
         * 'notificationMode': 通知模式
         * 'calling': 真实发送方号码
         * 'called': 真实接收方号码
         * 'virtualNumber': 隐私号码(X号码)
         * 'event': 短信状态事件
         * 'timeStamp': 短信事件发生的系统时间戳,UTC时间
         * 'subscriptionId': 绑定ID
         * 'smsContent': 用户发送的短信内容
         */
        String notificationMode = smsEvent.getString("notificationMode"); // 通知模式

        // 如果是Block模式,则要按接口文档进行回复响应
        if ("Block".equalsIgnoreCase(notificationMode)) {
            JSONObject resp = new JSONObject();
            JSONArray actions = new JSONArray();

            JSONObject action = new JSONObject();
            action.put("operation", "vNumberRoute"); // 操作类型
            actions.add(action);

            resp.put("actions", actions); // Block模式响应消息
            logger.info(resp);
        }
    }
}