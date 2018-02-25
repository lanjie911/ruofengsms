package com.sms.service.thirdpart.fenghuo.struts;

/**
 * Created by jerry lee on 2017/5/8.
 */
public class SmsSendParam {
    private String mobile ;
    private String content ;

    public SmsSendParam() {
    }

    public SmsSendParam(String mobile, String content) {
        this.mobile = mobile;
        this.content = content;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
