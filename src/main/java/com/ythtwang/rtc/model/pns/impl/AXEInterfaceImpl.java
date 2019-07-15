package com.ythtwang.rtc.model.pns.impl;

import com.alibaba.fastjson.JSONObject;
import com.ythtwang.rtc.model.pns.IAXEInterface;
import com.ythtwang.rtc.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * AXE模式接口测试
 */
public class AXEInterfaceImpl implements IAXEInterface {
    private static Logger logger = LogManager.getLogger(AXEInterfaceImpl.class);

    private String appKey; // APP_Key
    private String appSecret; // APP_Secret
    private String ompDomainName; // APP接入地址

    public AXEInterfaceImpl(String appKey, String appSecret, String ompDomainName) {
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
    public void axeBindNumber(String virtualNum, String bindNum) {
        if (StringUtils.isBlank(virtualNum) || StringUtils.isBlank(bindNum)) {
            logger.info("axeBindNumber set params error");
            return;
        }

        // 必填,AXE模式绑定接口访问URI
        String url = "/rest/caas/extendnumber/v1.0";
        String realUrl = buildOmpUrl(url);

        // 封装JOSN请求
        JSONObject json = new JSONObject();
        json.put("virtualNum", virtualNum); // X号码
        json.put("bindNum", bindNum); // A方真实号码(手机或固话)

        /**
         * 选填,各参数要求请参考"AXE模式绑定接口"
         */
        // json.put("areaCode", "0755"); // 城市码
        // json.put("displayNumMode", "0"); // 非A用户呼叫X号码时，A看到的主显号码
        // json.put("recordFlag", "false"); // 是否通话录音
        // json.put("recordHintTone", "recordHintTone.wav"); // 录音提示音
        // json.put("callbackTone", "callbackTone.wav"); // A呼叫X不存在回呼记录的提示音
        // json.put("callbackNum", "+8617000000021"); // A呼叫X不存在回呼记录的转接号码
        // json.put("bindExpiredTime", 24); // 绑定关系的有效时间
        // json.put("callbackExpiredTime", 24); // 回呼记录有效时间
        // json.put("userData", "abcdefg"); // 用户自定义数据

        String result = HttpUtil.sendPost(appKey, appSecret, realUrl, json.toString());
        logger.info("Response is :" + result);
    }

    @Override
    public void axeUnbindNumber(String subscriptionId, String virtualNum, String extendNum) {
        if (StringUtils.isBlank(subscriptionId)
                && (StringUtils.isBlank(virtualNum) || StringUtils.isBlank(extendNum))) {
            logger.info("axeUnbindNumber set params error");
            return;
        }

        // 必填,AXE模式解绑接口访问URI
        String url = "/rest/caas/extendnumber/v1.0";
        String realUrl = buildOmpUrl(url);

        // 申明对象
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(subscriptionId)) {
            map.put("subscriptionId", subscriptionId); // 绑定关系ID
        }
        if (StringUtils.isNotBlank(virtualNum)) {
            map.put("virtualNum", virtualNum); // AXE中的X号码
        }
        if (StringUtils.isNotBlank(extendNum)) {
            map.put("extendNum", extendNum); // AXE中的E号码
        }

        String result = HttpUtil.sendDelete(appKey, appSecret, realUrl, HttpUtil.map2UrlEncodeString(map));
        logger.info("Response is :" + result);
    }

    @Override
    public void axeQueryBindRelation(String subscriptionId, String virtualNum, String extendNum) {
        if (StringUtils.isBlank(subscriptionId)
                && (StringUtils.isBlank(virtualNum) || StringUtils.isBlank(extendNum))) {
            logger.info("axeQueryBindRelation set params error");
            return;
        }

        // 必填,AXE模式绑定信息查询接口访问URI
        String url = "/rest/caas/extendnumber/v1.0";
        String realUrl = buildOmpUrl(url);

        // 申明对象
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(subscriptionId)) {
            map.put("subscriptionId", subscriptionId); // 绑定关系ID
        }
        if (StringUtils.isNotBlank(virtualNum)) {
            map.put("virtualNum", virtualNum); // AXE中的X号码
        }
        if (StringUtils.isNotBlank(extendNum)) {
            map.put("extendNum", extendNum); // AXE中的E号码
        }

        String result = HttpUtil.sendGet(appKey, appSecret, realUrl, HttpUtil.map2UrlEncodeString(map));
        logger.info("Response is :" + result);
    }

    @Override
    public void axeGetRecordDownloadLink(String recordDomain, String fileName) {
        if (StringUtils.isBlank(recordDomain) || StringUtils.isBlank(fileName)) {
            logger.info("axeGetRecordDownloadLink set params error");
            return;
        }
        // 必填,AXE模式获取录音文件下载地址接口访问URI
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
    public void axeStopCall(String sessionid) {
        if (StringUtils.isBlank(sessionid)) {
            logger.info("axeStopCall set params error");
            return;
        }

        // 必填,AXE模式终止呼叫接口访问URI
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