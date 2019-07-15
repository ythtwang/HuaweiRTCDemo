package com.ythtwang.rtc.model.pns.impl;

import com.alibaba.fastjson.JSONObject;
import com.ythtwang.rtc.model.pns.IXInterface;
import com.ythtwang.rtc.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * X模式接口测试
 */
public class XInterfaceImpl implements IXInterface {
    private static Logger logger = LogManager.getLogger(XInterfaceImpl.class);

    private String appKey; // APP_Key
    private String appSecret; // APP_Secret
    private String ompDomainName; // APP接入地址

    public XInterfaceImpl(String appKey, String appSecret, String ompDomainName) {
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
    public void xGetRecordDownloadLink(String recordDomain, String fileName) {
        if (StringUtils.isBlank(recordDomain) || StringUtils.isBlank(fileName)) {
            logger.info("xGetRecordDownloadLink set params error");
            return;
        }

        // 必填,X模式获取录音文件下载地址接口访问URI
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
    public void xStopCall(String sessionid) {
        if (StringUtils.isBlank(sessionid)) {
            logger.info("xStopCall set params error");
            return;
        }

        // 必填,X模式终止呼叫接口访问URI
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