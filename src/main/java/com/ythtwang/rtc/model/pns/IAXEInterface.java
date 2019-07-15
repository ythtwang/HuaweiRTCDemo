package com.ythtwang.rtc.model.pns;

/**
 * AXE模式接口
 */
public interface IAXEInterface {
    /**
     * Set the X number to the user's virtual number | 隐私号码AXE绑定
     *
     * @param virtualNum X号码
     * @param bindNum    AXE中的A号码
     */
    void axeBindNumber(String virtualNum, String bindNum);

    /**
     * Unbind the virtual number and extend number from number a | 隐私号码AXE解绑
     *
     * @param subscriptionId 绑定关系ID
     * @param virtualNum     X号码
     * @param extendNum      分机号E
     *                       subscriptionId和(virtualNum+extendNum)二选一即可,当都传入时,优先选用subscriptionId
     */
    void axeUnbindNumber(String subscriptionId, String virtualNum, String extendNum);

    /**
     * Query the binding information for virtual number and extend number |
     * 查询AXE绑定信息
     *
     * @param subscriptionId 绑定关系ID
     * @param virtualNum     X号码
     * @param extendNum      分机号E
     *                       subscriptionId和(virtualNum+extendNum)二选一即可,当都传入时,优先选用subscriptionId
     */
    void axeQueryBindRelation(String subscriptionId, String virtualNum, String extendNum);

    /**
     * Get download link of the record file created in call | 获取录音文件下载地址
     *
     * @param recordDomain 录音文件存储的服务器域名
     * @param fileName     录音文件名
     */
    void axeGetRecordDownloadLink(String recordDomain, String fileName);

    /**
     * Stop the call on the X number assigned by sessionid | 终止呼叫
     *
     * @param sessionid 呼叫会话ID 通过"呼叫事件通知接口"获取
     */
    void axeStopCall(String sessionid);
}