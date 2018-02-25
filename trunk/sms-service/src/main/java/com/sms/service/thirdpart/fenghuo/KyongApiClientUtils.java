package com.sms.service.thirdpart.fenghuo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.sms.service.thirdpart.fenghuo.strategy.SmsSendStrategy;
import com.sms.service.thirdpart.fenghuo.struts.SmsSendParam;
import com.sms.service.thirdpart.fenghuo.struts.SmsSendResult;
import com.sms.service.thirdpart.fenghuo.utils.KyongHttpUtils;

/**
 * Created by jerry lee on 2017/5/8.
 */
public class KyongApiClientUtils {


    /**
     *
     * @param paramList 发送手机和内容的list
     * @param apiClient 发送http请求的驱动客户端
     * @param httpMethod 以get还是post发送
     * @param sleepMillis 调用接口的每次休眠时间，0标识不休眠
     * @return
     * @throws Exception
     */
    public static List<SmsSendResult> batchSmsSend(List<SmsSendParam> paramList, KyongApiClient apiClient , KyongHttpUtils.HttpMethod httpMethod ,Long sleepMillis) throws Exception{

        List<SmsSendResult> resultList = new ArrayList<SmsSendResult>() ;
        Map<String,HashSet<String>> map = new HashMap<String, HashSet<String>>();

        HashSet<String> hashSet = null ;
        for (SmsSendParam param : paramList) {
            if(map.containsKey(param.getContent())) {
                hashSet = map.get(param.getContent()) ;
                if(!hashSet.contains(param.getMobile())) {
                    hashSet.add(param.getMobile()) ;
                }
            }else {
                hashSet = new HashSet<String>() ;
                hashSet.add(param.getMobile());
                map.put(param.getContent() ,hashSet) ;
            }
        }

        SmsSendStrategy smsSendStrategy = new SmsSendStrategy(null,null) ;
        StringBuilder mobileSb = new StringBuilder() ;

        List<SmsSendResult> tempResList = new ArrayList<SmsSendResult>();
        for (Map.Entry<String,HashSet<String>> entry : map.entrySet()) {
            if(sleepMillis > 0 ) {
                try {
                    Thread.sleep(sleepMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw e ;
                }
            }
            tempResList.clear();

            smsSendStrategy.setContent(entry.getKey());
            hashSet = entry.getValue() ;

            if(hashSet.size() == 1) {
                for (String mobile : hashSet) {
                    smsSendStrategy.setDest(mobile);
                    tempResList.add(new SmsSendResult(mobile,entry.getKey())) ;
                }
            }else {
                mobileSb.delete(0,mobileSb.length()) ;
                int i = 0 ;
                for (String mobile : hashSet) {
                    mobileSb.append(mobile) ;
                    tempResList.add(new SmsSendResult(mobile,entry.getKey())) ;
                    if( i < hashSet.size()-1) {
                        mobileSb.append("," ) ;
                    }
                    i++;
                }
                smsSendStrategy.setDest(mobileSb.toString());
            }
            String result = null ;
            try {
                 result = apiClient.call(httpMethod , smsSendStrategy) ;

            } catch (Exception e) {
                e.printStackTrace();
                result = "请求发送短信接口失败";
            }
            for (SmsSendResult smsSendResult : tempResList) {
                smsSendResult.setResult(result);
            }
            resultList.addAll(tempResList);
        }
        return  resultList ;
    }
}
