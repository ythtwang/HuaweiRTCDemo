package com.ythtwang.rtc.model.pns;

import com.ythtwang.rtc.model.base.BaseRequest;
import com.ythtwang.rtc.model.pns.impl.AXEInterfaceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * AXE模式(具体场景请参考接口说明)
 */
@Component
public class AXE extends BaseRequest {
    @Value("${pns.axe.url}")
    private String url;

    public AXE() {
        super(System.getenv("RTC_AXE_APP_KEY"), System.getenv("RTC_AXE_APP_SECRET"));
    }

    public void run(String aNumber, String xNumber) throws Exception {
        IAXEInterface axe = new AXEInterfaceImpl(appKey, appSecret, url);

        // 第一步: 设置绑定关系,即调用AXE模式绑定接口,绑定成功获取分机号E,如: 1234
        // axe.axeBindNumber("+8617010000001", "+8618612345678");

        // 当用户发起通话时,隐私保护通话平台会将呼叫事件推送到商户应用,参考: AXVoiceEventImpl

        // 第二步: 用户通过隐私号码发起呼叫后,商户可随时终止一路呼叫,即调用终止呼叫接口
        // axe.axeStopCall("1200_366_0_20161228102743@callenabler.home1.com");

        // 第三步: 用户通话结束,若设置录音,则商户可以获取录音文件下载地址,即调用获取录音文件下载地址接口
        // axe.axeGetRecordDownloadLink("ostor.huawei.com", "1200_366_0_20161228102743.wav");

        // 第四步: 隐私号码循环使用,商户可将绑定关系解绑,即调用AXE模式解绑接口
        // axe.axeUnbindNumber(null, "+8617010000001", "1234");

        // 第五步: 商户可查询已订购的隐私号码的绑定信息,即调用AXE模式绑定信息查询接口
        axe.axeQueryBindRelation(null, "+8617010000001", "1234");
    }
}