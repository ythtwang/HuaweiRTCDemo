package com.ythtwang.rtc.model.pns.impl;

import com.alibaba.fastjson.JSONObject;
import com.ythtwang.rtc.model.pns.IAXBInterface;
import com.ythtwang.rtc.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * AXB模式接口测试
 */
public class AXBInterfaceImpl implements IAXBInterface {
    private static Logger logger = LogManager.getLogger(AXBInterfaceImpl.class);

    private String appKey; // APP_Key
    private String appSecret; // APP_Secret
    private String ompDomainName; // APP接入地址

    public AXBInterfaceImpl(String appKey, String appSecret, String ompDomainName) {
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
    public void axbBindNumber(String relationNum, String callerNum, String calleeNum) {
        if (StringUtils.isBlank(relationNum) || StringUtils.isBlank(callerNum) || StringUtils.isBlank(calleeNum)) {
            logger.info("axbBindNumber set params error");
            return;
        }

        // 必填,AXB模式绑定接口访问URI
        String url = "/rest/caas/relationnumber/partners/v1.0";
        String realUrl = buildOmpUrl(url);

        // 封装JOSN请求
        JSONObject json = new JSONObject();
        json.put("relationNum", relationNum); // X号码(关系号码)
        json.put("callerNum", callerNum); // A方真实号码(手机或固话)
        json.put("calleeNum", calleeNum); // B方真实号码(手机或固话)

        /**
         * 选填,各参数要求请参考"AXB模式绑定接口"
         */
//         json.put("areaCode", "0755"); //城市码
//         json.put("callDirection", 0); //允许呼叫的方向
//         json.put("duration", 86400); //绑定关系保持时间
//         json.put("recordFlag", "false"); //是否通话录音
//         json.put("recordHintTone", "recordHintTone.wav"); //录音提示音
//         json.put("maxDuration", 60); //单次通话最长时间
//         json.put("lastMinVoice", "lastMinVoice.wav"); //通话最后一分钟提示音
//         json.put("privateSms", "true"); //是否支持短信功能
//         JSONObject preVoice = new JSONObject();
//         preVoice.put("callerHintTone", "callerHintTone.wav"); //设置A拨打X号码时的通话前等待音
//         preVoice.put("calleeHintTone", "calleeHintTone.wav"); //设置B拨打X号码时的通话前等待音
//         json.put("preVoice", preVoice); //个性化通话前等待音

        String result = HttpUtil.sendPost(appKey, appSecret, realUrl, json.toString());
        logger.info("Response is :" + result);
    }

    @Override
    public void axbModifyNumber(String subscriptionId, String callerNum, String calleeNum) {
        if (StringUtils.isBlank(subscriptionId)) {
            logger.info("axbModifyNumber set params error");
            return;
        }

        // 必填,AXB模式绑定信息修改接口访问URI
        String url = "/rest/caas/relationnumber/partners/v1.0";
        String realUrl = buildOmpUrl(url);

        // 封装JOSN请求
        JSONObject json = new JSONObject();
        json.put("subscriptionId", subscriptionId); // 绑定关系ID
        if (StringUtils.isNotBlank(callerNum)) {
            json.put("callerNum", callerNum); // 将A方修改为新的号码(手机或固话)
        }
        if (StringUtils.isNotBlank(calleeNum)) {
            json.put("calleeNum", calleeNum); // 将B方修改为新的号码(手机或固话)
        }

        /**
         * 选填,各参数要求请参考"AXB模式绑定信息修改接口"
         */
//         json.put("callDirection", 0); //允许呼叫的方向
//         json.put("duration", 86400); //绑定关系保持时间
//         json.put("maxDuration", 90); //单次通话最长时间
//         json.put("lastMinVoice", "lastMinVoice.wav"); //通话最后一分钟提示音
//         json.put("privateSms", "true"); //是否支持短信功能
//         JSONObject preVoice = new JSONObject();
//         preVoice.put("callerHintTone", "callerHintTone.wav"); //设置A拨打X号码时的通话前等待音
//         preVoice.put("calleeHintTone", "calleeHintTone.wav"); //设置B拨打X号码时的通话前等待音
//         json.put("preVoice", preVoice); //个性化通话前等待音

        String result = HttpUtil.sendPut(appKey, appSecret, realUrl, json.toString());
        logger.info("Response is :" + result);
    }

    @Override
    public void axbUnbindNumber(String subscriptionId, String relationNum) {
        if (StringUtils.isBlank(subscriptionId) && StringUtils.isBlank(relationNum)) {
            logger.info("axbUnbindNumber set params error");
            return;
        }

        // 必填,AXB模式解绑接口访问URI
        String url = "/rest/caas/relationnumber/partners/v1.0";
        String realUrl = buildOmpUrl(url);

        // 申明对象
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(subscriptionId)) {
            map.put("subscriptionId", subscriptionId); // 绑定关系ID
        }
        if (StringUtils.isNotBlank(relationNum)) {
            map.put("relationNum", relationNum); // X号码(关系号码)
        }

        String result = HttpUtil.sendDelete(appKey, appSecret, realUrl, HttpUtil.map2UrlEncodeString(map));
        logger.info("Response is :" + result);
    }

    @Override
    public void axbQueryBindRelation(String subscriptionId, String relationNum) {
        if (StringUtils.isBlank(subscriptionId) && StringUtils.isBlank(relationNum)) {
            logger.info("axbQueryBindRelation set params error");
            return;
        }

        // 必填,AXB模式绑定信息查询接口访问URI
        String url = "/rest/caas/relationnumber/partners/v1.0";
        String realUrl = buildOmpUrl(url);

        // 申明对象
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(subscriptionId)) {
            map.put("subscriptionId", subscriptionId); // 绑定关系ID
        }
        if (StringUtils.isNotBlank(relationNum)) {
            map.put("relationNum", relationNum); // X号码(关系号码)
        }
        /**
         * 选填,当携带了subscriptionId时无需关注如下参数
         */
        // map.put("pageIndex", 1); //查询的分页索引,从1开始编号
        // map.put("pageSize", 20); //查询的分页大小,即每次查询返回多少条数据

        String result = HttpUtil.sendGet(appKey, appSecret, realUrl, HttpUtil.map2UrlEncodeString(map));
        logger.info("Response is :" + result);
    }

    @Override
    public void axbGetRecordDownloadLink(String recordDomain, String fileName) {
        if (StringUtils.isBlank(recordDomain) || StringUtils.isBlank(fileName)) {
            logger.info("axbGetRecordDownloadLink set params error");
            return;
        }
        // 必填,AXB模式获取录音文件下载地址接口访问URI
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
    public void axbStopCall(String sessionid) {
        if (StringUtils.isBlank(sessionid)) {
            logger.info("axbStopCall set params error");
            return;
        }

        // 必填,AXB模式终止呼叫接口访问URI
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