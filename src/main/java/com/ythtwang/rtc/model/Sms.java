package com.ythtwang.rtc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Component
public class Sms extends BaseRequest {
    @Value("${sms.url}")
    private String url;
    @Value("${sms.sender}")
    private String sender;
    @Value("${sms.templateId}")
    private String templateId;
    @Value("${sms.signature}")
    private String signature;
    private int StateCode;
    private ResponseBody rsp;

    public Sms() {
        super(System.getenv("RTC_SMS_APP_KEY"), System.getenv("RTC_SMS_APP_SECRET"));
    }

    /**
     * templateParas选填,使用无变量模板时请赋空值 String templateParas = "";
     * 单变量模板示例:模板内容为"您的验证码是${NUM_6}"时,templateParas可填写为"[\"369751\"]"
     * 双变量模板示例:模板内容为"您有${NUM_2}件快递请到${TXT_32}领取"时,templateParas可填写为"[\"3\",\"人民公园正门\"]"
     * 查看更多模板格式规范:常见问题>业务规则>短信模板内容审核标准
     */

    public boolean send(String receiver, String statusCallBack, String templateParas) throws Exception {
        //System.out.println(this.toString());
        //请求Body,不携带签名名称时,signature请填null
        String body = buildRequestBody(sender, receiver, templateId, templateParas, statusCallBack, signature);
        if (null == body || body.isEmpty()) {
            System.out.println("body is null.");
            return false;
        }

        //请求Headers中的X-WSSE参数值
        String wsseHeader = buildWsseHeader(appKey, appSecret);
        if (null == wsseHeader || wsseHeader.isEmpty()) {
            System.out.println("wsse header is null.");
            return false;
        }

        //如果JDK版本是1.8,可使用如下代码
        //为防止因HTTPS证书认证失败造成API调用失败,需要先忽略证书信任问题
        CloseableHttpClient client = HttpClients.custom()
                .setSSLContext(new SSLContextBuilder().loadTrustMaterial(null,
                        (x509CertChain, authType) -> true).build())
                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                .build();

        HttpResponse response = client.execute(RequestBuilder.create("POST")//请求方法POST
                .setUri(url)
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .addHeader(HttpHeaders.AUTHORIZATION, AUTH_HEADER_VALUE)
                .addHeader("X-WSSE", wsseHeader)
                .setEntity(new StringEntity(body)).build());

        String rspBody = EntityUtils.toString(response.getEntity());
        System.out.println(response.toString()); //打印响应头域信息
        System.out.println(rspBody); //打印响应消息实体

        ObjectMapper mapper = new ObjectMapper();
        rsp = mapper.readValue(rspBody, ResponseBody.class);
        StateCode = response.getStatusLine().getStatusCode();
        return StateCode == 200;

    }

    public String getSmsResult() {
        return this.rsp.result.toString();
    }

    public int getStateCode() {
        return this.StateCode;
    }

    //receiver必填,全局号码格式(包含国家码),示例:"+8615123456789,+8615234567890",多个号码之间用英文逗号分隔
    //statusCallBack选填,短信状态报告接收地址,推荐使用域名,为空或者不填表示不接收状态报告

    /**
     * 构造请求Body体
     *
     * @param sender
     * @param receiver
     * @param templateId
     * @param templateParas
     * @param statusCallbackUrl
     * @param signature         | 签名名称,使用国内短信通用模板时填写
     * @return
     */
    static String buildRequestBody(String sender, String receiver, String templateId, String templateParas,
                                   String statusCallbackUrl, String signature) {
        if (null == sender || null == receiver || null == templateId || sender.isEmpty() || receiver.isEmpty()
                || templateId.isEmpty()) {
            System.out.println("buildRequestBody(): sender, receiver or templateId is null.");
            return null;
        }
        List<NameValuePair> keyValues = new ArrayList<NameValuePair>();

        keyValues.add(new BasicNameValuePair("from", sender));
        keyValues.add(new BasicNameValuePair("to", receiver));
        keyValues.add(new BasicNameValuePair("templateId", templateId));
        if (null != templateParas && !templateParas.isEmpty()) {
            keyValues.add(new BasicNameValuePair("templateParas", templateParas));
        }
        if (null != statusCallbackUrl && !statusCallbackUrl.isEmpty()) {
            keyValues.add(new BasicNameValuePair("statusCallback", statusCallbackUrl));
        }
        if (null != signature && !signature.isEmpty()) {
            keyValues.add(new BasicNameValuePair("signature", signature));
        }

        return URLEncodedUtils.format(keyValues, Charset.forName("UTF-8"));
    }

    public static class ResponseBody extends BaseResponse {
        @JsonProperty("result")
        List<SmsID> result;

        public ResponseBody() {
            super();
        }

        public static class SmsID implements Serializable {
            public String smsMsgId;
            public String from;
            public String originTo;
            public String status;
            public String createTime;

            public SmsID() {
            }

            @Override
            public String toString() {
                return "SmsID{" +
                        "smsMsgId='" + smsMsgId + '\'' +
                        ", from='" + from + '\'' +
                        ", originTo='" + originTo + '\'' +
                        ", status='" + status + '\'' +
                        ", createTime='" + createTime + '\'' +
                        '}';
            }
        }
    }

    @Override
    public String toString() {
        return "Sms{" +
                super.toString() +
                ", url='" + url + '\'' +
                ", sender='" + sender + '\'' +
                ", templateId='" + templateId + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}