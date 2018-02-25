package com.sms.service.thirdpart.fenghuo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sms.service.thirdpart.fenghuo.strategy.KyReqtStrategy;
import com.sms.service.thirdpart.fenghuo.utils.KyongHttpUtils;
import com.sms.service.thirdpart.fenghuo.utils.codec.MD5Util;

/**
 * Created by jerry lee on 2017/4/20.
 */
public class KyongApiClient {
    private String name;
    private String password ;

    private int connectTimeout = 3000;
    private int readTimeout = 80000;


    public KyongApiClient(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public KyongApiClient(String name, String password, int connectTimeout, int readTimeout) {
        this.name = name;
        this.password = password;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
    }



    private static final String DATE_PATTERN = "yyyyMMddHHmmss" ;
    public String call(KyongHttpUtils.HttpMethod method , KyReqtStrategy reqtStrategy) throws Exception {
        try {

            Map<String,String> param = new HashMap<String,String>() ;
            String url = reqtStrategy.getUrl() ;
            Map<String,String> paramMap = reqtStrategy.getParam() ;
            if(paramMap != null) {
                for (Map.Entry<String,String> entry : paramMap.entrySet()) {
                    param.put(entry.getKey(),entry.getValue()) ;
                }
            }
            param.put("name",this.name) ;
            String seed = new SimpleDateFormat(DATE_PATTERN).format(new Date()) ;
            param.put("seed",seed) ;
            String key = MD5Util.MD5(MD5Util.MD5(this.password, "GBK",true)+seed , "GBK" , true ) ;
            param.put("key",key) ;

            if(method.equals(KyongHttpUtils.HttpMethod.POST)) {
                return KyongHttpUtils.doPost(url , param , this.connectTimeout ,this.readTimeout , null) ;
            }else if (method.equals(KyongHttpUtils.HttpMethod.GET)){
                return KyongHttpUtils.doGet(url,param) ;
            }
        }catch (Exception e) {
            throw e ;
        }
        return  null ;
    }
}
