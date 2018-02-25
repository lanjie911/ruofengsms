package com.sms.service.thirdpart.fenghuo.utils;

/**
 * Created by jerry lee on 2017/4/20.
 */
public enum  UrlEnum {

    SMS_SEND_URL("http://apisms.kyong.net:8080/eums/sms/send.do","下行协议") ,
    SMS_BALANCE_URL("http://apisms.kyong.net:8080/eums/sms/balance.do","下行协议")  ,
    SMS_REPORT_URL("http://apisms.kyong.net:8080/eums/sms/report.do","状态报告拉取协议") ,
    SMS_MO_URL("http://apisms.kyong.net:8080/eums/sms/mo.do","上行拉取协议") ;


    private String url ;
    private String desc ;

     UrlEnum(String url, String desc) {
        this.url = url;
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public String getDesc() {
        return desc;
    }
}
