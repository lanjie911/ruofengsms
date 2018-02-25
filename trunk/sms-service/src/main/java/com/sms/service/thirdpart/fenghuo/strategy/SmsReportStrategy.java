package com.sms.service.thirdpart.fenghuo.strategy;

import java.util.Map;

import com.sms.service.thirdpart.fenghuo.utils.UrlEnum;

/**
 * Created by jerry lee on 2017/4/20.
 */
public class SmsReportStrategy implements KyReqtStrategy {

    private String url ;
    private Map<String, String>  param;
    public SmsReportStrategy(){
        url =  UrlEnum.SMS_REPORT_URL.getUrl();
    }

 
    public String getUrl() {
        return url;
    }

 
    public void setUrl(String url) {
        this.url = url ;
    }

 
    
    public Map<String, String> getParam() {
        return param;
    }


	public void setParam(Map<String, String> param) {
		this.param = param;
 
	}
}
