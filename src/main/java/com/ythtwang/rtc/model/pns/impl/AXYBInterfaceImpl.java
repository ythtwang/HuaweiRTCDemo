package com.ythtwang.rtc.model.pns.impl;

import com.alibaba.fastjson.JSONObject;
import com.ythtwang.rtc.model.pns.IAXYBInterface;
import com.ythtwang.rtc.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * AXYB模式接口测试
 */
public class AXYBInterfaceImpl implements IAXYBInterface {
    private static Logger logger = LogManager.getLogger(AXYBInterfaceImpl.class);

    private String appKey; // APP_Key
    private String appSecret; // APP_Secret
    private String ompDomainName; // APP接入地址

    public AXYBInterfaceImpl(String appKey, String appSecret, String ompDomainName) {
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
    public void xyBindNumber(String origNum, String areaCode, String subscriptionId) {
        JSONObject json = null;
        if (StringUtils.isBlank(subscriptionId)) {
            //AXYB for AX
            if (StringUtils.isBlank(origNum) || StringUtils.isBlank(areaCode)) {
                logger.info("AXYB-AX bind: origNum or areaCode is null.");
                return;
            }
            json = new JSONObject();
            json.put("origNum", origNum); // 用户号码
            json.put("areaCode", areaCode); // 区号,标示小号归属的区域

            /**
             * 选填,各参数要求请参考"AXYB模式绑定接口"
             */
//            json.put("areaMatchMode", "0"); // 号码筛选方式
//            json.put("callDirection", 0); // 允许的呼叫方向
//            json.put("duration", 7200); // 绑定关系保持时间(单位为秒)
//            json.put("recordFlag", true); // 录音标识
//            json.put("recordHintTone", "recordHintTone.wav"); // 录音提示音
//            json.put("preVoiceX", "x_hint_tone1.wav"); // 设置对X号码播放的个性化通话前等待音
        } else {
            //AXYB for YB
            if (StringUtils.isBlank(origNum)) {
                logger.info("AXYB-YB bind: origNum is null.");
                return;
            }
            json = new JSONObject();
            json.put("origNum", origNum); // 用户号码
            json.put("subscriptionId", subscriptionId); // 绑定关系ID

            /**
             * 选填,各参数要求请参考"AXYB模式绑定接口"
             */
            if (StringUtils.isNotBlank(areaCode)) {
                json.put("areaCode", areaCode); // 区号,标示小号归属的区域
            }
//            json.put("areaMatchMode", "0"); // 号码筛选方式
//            json.put("preVoiceY", "y_hint_tone1.wav"); // 设置对Y号码播放的个性化通话前等待音
        }
        // 必填,AXYB模式绑定接口访问URI
        String url = "/rest/omp/xyrelationnumber/v1.0";
        // 申明对象
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("app_key", appKey); // appKey
        String realUrl = buildOmpUrl(url) + "?" + HttpUtil.map2UrlEncodeString(map);

        String result = HttpUtil.sendPost(appKey, appSecret, realUrl, json.toString());
        logger.info("Response is :" + result);
    }

    @Override
    public void xyUnbindNumber(String subscriptionId) {
        if (StringUtils.isBlank(subscriptionId)) {
            logger.info("xyUnbindNumber set params error");
            return;
        }

        // 必填,AXYB模式解绑接口访问URI
        String url = "/rest/omp/xyrelationnumber/v1.0";
        String realUrl = buildOmpUrl(url);

        // 申明对象
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("subscriptionId", subscriptionId); // 绑定关系ID
        map.put("app_key", appKey); // appKey

        String result = HttpUtil.sendDelete(appKey, appSecret, realUrl, HttpUtil.map2UrlEncodeString(map));
        logger.info("Response is :" + result);
    }

    @Override
    public void xyQueryBindRelation(String subscriptionId, String relationNum) {
        if (StringUtils.isBlank(subscriptionId) && StringUtils.isBlank(relationNum)) {
            logger.error("xyQueryBindRelation set parsms error");
            return;
        }

        // 必填,AXYB模式绑定信息查询接口访问URI
        String url = "/rest/omp/xyrelationnumber/v1.0";
        String realUrl = buildOmpUrl(url);

        // 申明对象
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(subscriptionId)) {
            map.put("subscriptionId", subscriptionId); // 指定绑定关系ID进行查询
        }
        if (StringUtils.isNotBlank(relationNum)) {
            map.put("relationNum", relationNum); // 指定X号码进行查询
        }
        map.put("app_key", appKey); // appKey

        String result = HttpUtil.sendGet(appKey, appSecret, realUrl, HttpUtil.map2UrlEncodeString(map));
        logger.info("Response is :" + result);
    }

    @Override
    public void xyGetRecordDownloadLink(String recordDomain, String fileName) {
        if (StringUtils.isBlank(recordDomain) || StringUtils.isBlank(fileName)) {
            logger.info("xyGetRecordDownloadLink set params error");
            return;
        }

        // 必填,AXYB模式获取录音文件下载地址接口访问URI
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
    public void xyStopCall(String sessionid) {
        if (StringUtils.isBlank(sessionid)) {
            logger.info("xyStopCall set params error");
            return;
        }

        // 必填,AXYB模式终止呼叫接口访问URI
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