package com.ythtwang.rtc.model.pns;

/**
 * X模式接口
 */
public interface IXInterface {
    /**
     * Get download link of the record file created in call | 获取录音文件下载地址
     *
     * @param recordDomain 录音文件存储的服务器域名
     * @param fileName     录音文件名
     */
    void xGetRecordDownloadLink(String recordDomain, String fileName);

    /**
     * Stop the call on the X number assigned by sessionid | 终止呼叫
     *
     * @param sessionid 呼叫会话ID 通过"呼叫事件通知接口"获取
     */
    void xStopCall(String sessionid);
}