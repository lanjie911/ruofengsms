package com.sms.service.thirdpart;

import com.sms.service.thirdpart.fenghuo.KyongApiClient;
import com.sms.service.thirdpart.fenghuo.strategy.SmsBalanceStrategy;
import com.sms.service.thirdpart.fenghuo.strategy.SmsSendStrategy;
import com.sms.service.thirdpart.fenghuo.utils.KyongHttpUtils;

/**
 * Created by jerry lee on 2017/5/4.
 */
public class Test {
    public static void main(String[] args) {

        String username = "rfwyhy" ;
        String password = "0h1we528" ;
        //构造嗲用接口的客户端
        KyongApiClient client = new KyongApiClient(username,password) ;
        try {

            //演示发送下行请求
           String dest = "15010477761" ;
            String content = "【若峰伟业】 验证码为376244，提示您保管好自己的验证码，请勿泄露!" ;
            SmsSendStrategy smsSendStrategy = new SmsSendStrategy(dest,content,null,null) ;
//            //以GET方式发送下行拉取请求
//            System.out.println(client.call(KyongHttpUtils.HttpMethod.GET,smsSendStrategy));
//            //以POST方式发送下行拉取请求
            System.out.println(client.call(KyongHttpUtils.HttpMethod.POST ,smsSendStrategy));

            //演示发送获取余额请求
//        	SmsBalanceStrategy smsBalance = new SmsBalanceStrategy() ;
//            //以GET方式发送获取余额请求
//            System.out.println(client.call(KyongHttpUtils.HttpMethod.GET,smsBalance));
//            //以POST方式发送获取余额请求
//            System.out.println(client.call(KyongHttpUtils.HttpMethod.POST,smsBalance));

//           //演示发送拉取上行请求
//            SmsMoStrategy smsMo = new SmsMoStrategy() ;
//            //以GET方式发送上行拉取请求
//            System.out.println(client.call(KyongHttpUtils.HttpMethod.GET,smsMo));
//            //以POST方式发送上行拉取请求
//            System.out.println(client.call(KyongHttpUtils.HttpMethod.POST,smsMo));

            //演示发送状态报告
         /*  SmsReportStrategy smsReport = new SmsReportStrategy() ;
            //以GET方式状态报告拉取请求
            System.out.println(client.call(KyongHttpUtils.HttpMethod.GET,smsReport));
            //以POST方式状态报告拉取请求
            System.out.println(client.call(KyongHttpUtils.HttpMethod.POST,smsReport));*/

            //演示批量发送短信

//            List<SmsSendParam> list =  new ArrayList<SmsSendParam>() ;
//            list.add(new SmsSendParam("手机号","【快用科技】验证码 ")) ;
//            list.add(new SmsSendParam("手机号","【快用科技】验证码 ")) ;
//            list.add(new SmsSendParam("手机号","【快用科技】验证码 ")) ;
//            list.add(new SmsSendParam("手机号","【快用科技】验证码 ")) ;
//            /***
//             *
//             */
//            List<SmsSendResult> resultList = KyongApiClientUtils.batchSmsSend(list,client , HttpMethod.POST , 0L) ;
//            for (SmsSendResult smsSendResult : resultList ) {
//                System.out.println(smsSendResult.getMobile()+"----"+smsSendResult.getContent()+"----"+smsSendResult.getResult());
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
