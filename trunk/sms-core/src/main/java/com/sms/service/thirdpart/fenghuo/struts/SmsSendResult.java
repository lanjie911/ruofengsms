package com.sms.service.thirdpart.fenghuo.struts;

/**
 * Created by jerry lee on 2017/5/8.
 */
public class SmsSendResult {
    private String mobile ;
    private String content ;
    private String result ;

    public SmsSendResult() {
    }

    public SmsSendResult(String mobile, String content) {
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
