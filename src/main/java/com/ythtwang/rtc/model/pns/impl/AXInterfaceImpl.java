package com.ythtwang.rtc.model.pns.impl;

import com.alibaba.fastjson.JSONObject;
import com.ythtwang.rtc.model.pns.IAXInterface;
import com.ythtwang.rtc.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * AX模式接口测试
 */
public class AXInterfaceImpl implements IAXInterface {
    private static Logger logger = LogManager.getLogger(AXInterfaceImpl.class);

    private String appKey; // APP_Key
    private String appSecret; // APP_Secret
    private String ompDomainName; // APP接入地址

    public AXInterfaceImpl(String appKey, String appSecret, String ompDomainName) {
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.ompDomainName = ompDomainName;
    }

    /**
     * Build the real url of https request | 构建隐私保护通话平台请求路径
     *
     * @param path 接口访问URI
     * @return
     */
    private String buildOmpUrl(String path) {
        return ompDomainName + path;
    }

    @Override
    public void axBindNumber(String origNum, String privateNum, String calleeNumDisplay) {
        if (StringUtils.isBlank(origNum) || StringUtils.isBlank(privateNum) || StringUtils.isBlank(calleeNumDisplay)) {
            logger.info("axBindNumber set params error");
            return;
        }
        // 必填,AX模式绑定接口访问URI
        String url = "/rest/provision/caas/privatenumber/v1.0";
        String realUrl = buildOmpUrl(url);

        // 封装JOSN请求
        JSONObject json = new JSONObject();
        json.put("origNum", origNum); // A方真实号码(手机或固话)
        json.put("privateNum", privateNum); // 已订购的隐私号码(X号码)
        json.put("calleeNumDisplay", calleeNumDisplay); // 设置非A用户呼叫X时,A接到呼叫时的主显号码

        /**
         * 选填,各参数要求请参考"AX模式绑定接口"
         */
//        json.put("privateNumType", "mobile-virtual"); //固定为mobile-virtual
//        json.put("areaCode", "0755"); //城市码
//        json.put("recordFlag", "true"); ////是否通话录音
//        json.put("recordHintTone", "recordHintTone.wav"); //录音提示音
//        json.put("privateSms", "true"); //是否支持短信功能
//        JSONObject preVoice = new JSONObject();
//        preVoice.put("callerHintTone", "callerHintTone.wav"); //设置A拨打X号码时的通话前等待音
//        preVoice.put("calleeHintTone", "calleeHintTone.wav"); //设置非A用户拨打X号码时的通话前等待音
//        json.put("preVoice", preVoice); //个性化通话前等待音

        String result = HttpUtil.sendPost(appKey, appSecret, realUrl, json.toString());
        logger.info("Response is :" + result);
    }

    @Override
    public void axModifyNumber(String subscriptionId, String origNum, String privateNum, boolean privateSms) {
        if (StringUtils.isBlank(subscriptionId) && (StringUtils.isBlank(origNum) || StringUtils.isBlank(privateNum))) {
            logger.info("axModifyNumber set params error");
            return;
        }

        // 必填,AX模式绑定信息修改接口访问URI
        String url = "/rest/provision/caas/privatenumber/v1.0";
        String realUrl = buildOmpUrl(url);

        // 封装JOSN请求
        JSONObject json = new JSONObject();
        if (StringUtils.isNotBlank(subscriptionId)) {
            json.put("subscriptionId", subscriptionId); // 绑定关系ID
        }
        if (StringUtils.isNotBlank(origNum)) {
            json.put("origNum", origNum); // AX中的A号码
        }
        if (StringUtils.isNotBlank(privateNum)) {
            json.put("privateNum", privateNum); // AX中的X号码
        }

        json.put("privateSms", privateSms); // 是否支持短信功能

        String result = HttpUtil.sendPut(appKey, appSecret, realUrl, json.toString());
        logger.info("Response is :" + result);
    }

    @Override
    public void axUnbindNumber(String subscriptionId, String origNum, String privateNum) {
        if (StringUtils.isBlank(subscriptionId) && (StringUtils.isBlank(origNum) || StringUtils.isBlank(privateNum))) {
            logger.info("axUnbindNumber set params error");
            return;
        }

        // 必填,AX模式解绑接口访问URI
        String url = "/rest/provision/caas/privatenumber/v1.0";
        String realUrl = buildOmpUrl(url);

        // 申明对象
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(subscriptionId)) {
            map.put("subscriptionId", subscriptionId); // 绑定关系ID
        }
        if (StringUtils.isNotBlank(origNum)) {
            map.put("origNum", origNum); // AX中的A号码
        }
        if (StringUtils.isNotBlank(privateNum)) {
            map.put("privateNum", privateNum); // AX中的X号码
        }

        String result = HttpUtil.sendDelete(appKey, appSecret, realUrl, HttpUtil.map2UrlEncodeString(map));
        logger.info("Response is :" + result);
    }

    @Override
    public void axQueryBindRelation(String subscriptionId, String origNum) {
        if (StringUtils.isBlank(subscriptionId) && StringUtils.isBlank(origNum)) {
            logger.error("axQueryBindRelation set parsms error");
            return;
        }

        // 必填,AX模式绑定信息查询接口访问URI
        String url = "/rest/provision/caas/privatenumber/v1.0";
        String realUrl = buildOmpUrl(url);

        // 申明对象
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(origNum)) {
            map.put("origNum", origNum); // 指定A号码进行查询
        }
        if (StringUtils.isNotBlank(subscriptionId)) {
            map.put("subscriptionId", subscriptionId); // 指定绑定关系ID进行查询
        }

        String result = HttpUtil.sendGet(appKey, appSecret, realUrl, HttpUtil.map2UrlEncodeString(map));
        logger.info("Response is :" + result);
    }

    @Override
    public void axSetCalleeNumber(String subscriptionId, String origNum, String privateNum, String calleeNum) {
        if (StringUtils.isBlank(calleeNum) || (StringUtils.isBlank(subscriptionId)
                && (StringUtils.isBlank(origNum) || StringUtils.isBlank(privateNum)))) {
            logger.info("axSetCalleeNumber set params error");
            return;
        }

        // 必填,AX模式设置临时被叫接口
        String url = "/rest/caas/privatenumber/calleenumber/v1.0";
        String realUrl = buildOmpUrl(url);

        // 封装JOSN请求
        JSONObject json = new JSONObject();
        if (StringUtils.isNotBlank(subscriptionId)) {
            json.put("subscriptionId", subscriptionId); // 绑定关系ID
        }
        if (StringUtils.isNotBlank(origNum)) {
            json.put("origNum", origNum); // AX中的A号码
        }
        if (StringUtils.isNotBlank(privateNum)) {
            json.put("privateNum", privateNum); // AX中的X号码
        }

        json.put("calleeNum", calleeNum); // 本次呼叫的真实被叫号码

        String result = HttpUtil.sendPut(appKey, appSecret, realUrl, json.toString());
        logger.info("Response is :" + result);
    }

    @Override
    public void axGetRecordDownloadLink(String recordDomain, String fileName) {
        if (StringUtils.isBlank(recordDomain) || StringUtils.isBlank(fileName)) {
            logger.info("axGetRecordDownloadLink set params error");
            return;
        }

        // 必填,AX模式获取录音文件下载地址接口访问URI
        String url = "/rest/provision/voice/record/v1.0";
        String realUrl = buildOmpUrl(url);

        // 申明对象
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("recordDomain", recordDomain); // 录音文件存储的服务器域名
        map.put("fileName", fileName); // 录音文件名

        String result = HttpUtil.sendGet(appKey, appSecret, realUrl, HttpUtil.map2UrlEncodeString(map));
        logger.info("Response is :" + result);
    }

    @Override
    public void axStopCall(String sessionid) {
        if (StringUtils.isBlank(sessionid)) {
            logger.info("axStopCall set params error");
            return;
        }

        // 必填,AX模式终止呼叫接口访问URI
        String url = "/rest/httpsessions/callStop/v2.0";
        String realUrl = buildOmpUrl(url);

        // 封装JOSN请求
        JSONObject json = new JSONObject();
        json.put("sessionid", sessionid); // 呼叫会话ID
        json.put("signal", "call_stop"); // 取值固定为"call_stop"

        String result = HttpUtil.sendPost(appKey, appSecret, realUrl, json.toString());
        logger.info("Response is :" + result);
    }
}