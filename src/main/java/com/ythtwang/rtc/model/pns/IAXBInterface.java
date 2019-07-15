package com.ythtwang.rtc.model.pns;

/**
 * AXB模式接口
 */
public interface IAXBInterface {
    /**
     * Set X number to be the privacy number between number a and number b |
     * 隐私号码AXB绑定
     *
     * @param relationNum 关系号码
     * @param callerNum   主叫号码
     * @param calleeNum   被叫号码
     */
    void axbBindNumber(String relationNum, String callerNum, String calleeNum);

    /**
     * Modify number a/b of the privacy relationship assigned by subscriptionId |
     * 隐私号码AXB绑定信息修改
     *
     * @param subscriptionId 绑定关系ID
     * @param callerNum      主叫号码
     * @param calleeNum      被叫号码
     */
    void axbModifyNumber(String subscriptionId, String callerNum, String calleeNum);

    /**
     * Unbind the privacy relationship between number a and number b | 隐私号码AXB解绑
     *
     * @param subscriptionId 绑定关系ID
     * @param relationNum    关系号码 都传时以subscriptionId优先
     */
    void axbUnbindNumber(String subscriptionId, String relationNum);

    /**
     * Query the privacy binding relationship on the X number | 查询AXB绑定信息
     *
     * @param subscriptionId 绑定关系ID
     * @param relationNum    关系号码 都传时以subscriptionId优先
     */
    void axbQueryBindRelation(String subscriptionId, String relationNum);

    /**
     * Get download link of the record file created in call | 获取录音文件下载地址
     *
     * @param recordDomain 录音文件存储的服务器域名
     * @param fileName     录音文件名
     */
    void axbGetRecordDownloadLink(String recordDomain, String fileName);

    /**
     * Stop the call on the X number assigned by sessionid | 终止呼叫
     *
     * @param sessionid 呼叫会话ID 通过"呼叫事件通知接口"获取
     */
    void axbStopCall(String sessionid);
}