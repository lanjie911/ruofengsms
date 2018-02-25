package com.sms.service.thirdpart.fenghuo.strategy;

import java.util.Map;

/**
 * Created by jerry lee on 2017/4/20.
 */
public interface KyReqtStrategy {
    String getUrl()  ;
    void setUrl(String url) ;
    Map<String,String> getParam() ;
    void setParam( Map<String,String> param) ;
   

}
