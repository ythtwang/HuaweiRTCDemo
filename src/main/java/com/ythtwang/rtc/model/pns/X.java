package com.ythtwang.rtc.model.pns;


import com.ythtwang.rtc.model.base.BaseRequest;
import com.ythtwang.rtc.model.pns.impl.XInterfaceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * X模式(具体场景请参考接口说明)
 */
@Component
public class X extends BaseRequest {
    @Value("${pns.x.url}")
    private String url;

    public X() {
        super(System.getenv("RTC_X_APP_KEY"), System.getenv("RTC_X_APP_SECRET"));
    }

    public void run(String xNumber) throws Exception {
        IXInterface x = new XInterfaceImpl(appKey, appSecret, url);

        // 当用户发起通话时,隐私保护通话平台会将呼叫事件推送到商户应用,参考: HostingVoiceEventImpl
        // 当用户使用短信功能,隐私保护通话平台将短信事件推送到商户应用,参考: HostingVoiceEventImpl

        // X模式中的AXYB,Y为主显号码,当前暂不支持自定义号码(固定显示X号码)
        //
        //      商户A         X号码                 隐私保护通话平台                                用户B
        //        |--A call X-->|
        //        |             |----route----->|
        //        |<-----query bind number------|
        //        |------return number Y&B----->|
        //        |                             |------call B-------->|
        //        |<-------------------talk A&B---------------------->|

        // 第一步: 用户通过隐私号码发起呼叫后,商户可随时终止一路呼叫,即调用终止呼叫接口
        // x.xStopCall("1200_366_0_20161228102743@callenabler.home1.com");

        // 第二步: 用户通话结束,若设置录音,则商户可以获取录音文件下载地址,即调用获取录音文件下载地址接口
        x.xGetRecordDownloadLink("ostor.huawei.com", "1200_366_0_20161228102743.wav");
    }
}