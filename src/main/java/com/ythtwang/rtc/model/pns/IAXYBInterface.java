package com.ythtwang.rtc.model.pns;

/**
 * AXYB模式接口
 */
public interface IAXYBInterface {
    /**
     * Set the X/Y number to the user's privacy number | AXYB绑定
     *
     * @param origNum        用户号码
     * @param areaCode       区号，标示小号归属的区域
     * @param subscriptionId 绑定关系ID
     */
    void xyBindNumber(String origNum, String areaCode, String subscriptionId);

    /**
     * Unbind the AXYB bind relation | AXYB解绑
     *
     * @param subscriptionId 绑定关系ID
     */
    void xyUnbindNumber(String subscriptionId);

    /**
     * Query the AXYB bind relation on the X number or subscriptionId | 查询AXYB绑定信息
     *
     * @param subscriptionId 绑定关系ID
     * @param relationNum    X号码
     *                       subscriptionId和relationNum二选一即可,当都传入时,优先选用subscriptionId
     */
    void xyQueryBindRelation(String subscriptionId, String relationNum);

    /**
     * Get download link of the record file created in call | 获取录音文件下载地址
     *
     * @param recordDomain 录音文件存储的服务器域名
     * @param fileName     录音文件名
     */
    void xyGetRecordDownloadLink(String recordDomain, String fileName);

    /**
     * Stop the call on the X number assigned by sessionid | 终止呼叫
     *
     * @param sessionid 呼叫会话ID 通过"呼叫事件通知接口"获取
     */
    void xyStopCall(String sessionid);
}