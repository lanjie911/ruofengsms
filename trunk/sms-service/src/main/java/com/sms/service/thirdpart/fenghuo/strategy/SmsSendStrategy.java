package com.sms.service.thirdpart.fenghuo.strategy;

import java.util.HashMap;
import java.util.Map;

import com.sms.service.thirdpart.fenghuo.utils.UrlEnum;

/**
 * Created by jerry lee on 2017/4/20.
 */
public class SmsSendStrategy implements KyReqtStrategy {

    private String url  ;

    private Map<String,String> paramMap ;


    public SmsSendStrategy(String dest , String content , String ext , String reference) {
        paramMap = new HashMap<String,String>() ;
        paramMap.put("dest",dest) ;
        paramMap.put("content",content) ;
        if(ext != null) {
            paramMap.put("ext",ext) ;
        }
        if(reference != null) {
            paramMap.put("reference",reference) ;
        }
        url = UrlEnum.SMS_SEND_URL.getUrl() ;
    }

    public SmsSendStrategy(String ext , String reference) {
        paramMap = new HashMap<String,String>() ;
        if(ext != null) {
            paramMap.put("ext",ext) ;
        }
        if(reference != null) {
            paramMap.put("reference",reference) ;
        }
        url = UrlEnum.SMS_SEND_URL.getUrl() ;
    }

    public void setDest(String dest) {
        paramMap.put("dest",dest) ;
    }

    public void setContent(String content) {
        paramMap.put("content",content) ;
    }


    public String getUrl() {
        return url;
    }

 
    public void setUrl(String url) {
        this.url = url ;
    }

 
    
    public Map<String, String> getParam() {
        return paramMap;
    }


	public void setParam(Map<String, String> param) {
		this.paramMap = param;
 
	}
}
