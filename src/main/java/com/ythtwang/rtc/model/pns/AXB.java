package com.ythtwang.rtc.model.pns;

import com.ythtwang.rtc.model.base.BaseRequest;
import com.ythtwang.rtc.model.pns.impl.AXBInterfaceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * AXB模式(具体场景请参考接口说明)
 */
@Component
public class AXB extends BaseRequest {
    @Value("${pns.axb.url}")
    private String url;

    public AXB() {
        super(System.getenv("RTC_AXB_APP_KEY"), System.getenv("RTC_AXB_APP_SECRET"));
    }

    public void run(String aNumber, String xNumber, String bNumber) throws Exception {
        IAXBInterface axb = new AXBInterfaceImpl(appKey, appSecret, url);

        // 第一步: 号码绑定,即调用AXB模式绑定接口
        // axb.axbBindNumber("+8617010000001", "+8618612345678", "+8618612345679");

        // 当用户发起通话时,隐私保护通话平台会将呼叫事件推送到商户应用,参考: AXVoiceEventImpl
        // 当用户使用短信功能,隐私保护通话平台将短信事件推送到商户应用,参考: AXVoiceEventImpl

        // 第二步: 用户通过隐私号码发起呼叫后,商户可随时终止一路呼叫,即调用终止呼叫接口
        // axb.axbStopCall("1200_366_0_20161228102743@callenabler.home1.com");

        // 第三步: 用户通话结束,若设置录音,则商户可以获取录音文件下载地址,即调用获取录音文件下载地址接口
        // axb.axbGetRecordDownloadLink("ostor.huawei.com", "1200_366_0_20161228102743.wav");

        // 第四步: 根据业务需求,可更改绑定关系,即调用AXB模式绑定信息修改接口
        // axb.axbModifyNumber("efw89efwf7fea324252", "+8618612345679", null);

        // 第五步: 隐私号码循环使用,商户可将绑定关系解绑,即调用AXB模式解绑接口
        // axb.axbUnbindNumber(null, "+8617010000001");

        // 第六步: 商户可查询已订购的隐私号码的绑定信息,即调用AXB模式绑定信息查询接口
        axb.axbQueryBindRelation(null, "+8617010000001");
    }
}