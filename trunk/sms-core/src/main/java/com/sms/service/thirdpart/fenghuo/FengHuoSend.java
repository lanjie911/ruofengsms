package com.sms.service.thirdpart.fenghuo;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.service.PrepareParamService;
import com.sms.service.thirdpart.fenghuo.strategy.SmsSendStrategy;
import com.sms.service.thirdpart.fenghuo.utils.KyongHttpUtils;

/**
 * Created by jerry lee on 2017/5/4.
 */
@Service
public class FengHuoSend {
	
	private static Logger logger = LoggerFactory.getLogger(FengHuoSend.class);
	
	@Autowired
	private PrepareParamService prepareParamService;
	// 用户名
	
	public Map<String, Object> smsSend(String dest,String content){
		Map<String,Object> result = new HashMap<>();
		String resultStr = "";
		String username = prepareParamService.getParam("fenghuo_name").getParamValue();
		String password = prepareParamService.getParam("fenghuo_pwd").getParamValue();
		KyongApiClient client = new KyongApiClient(username,password);
		try {
            SmsSendStrategy smsSendStrategy = new SmsSendStrategy(dest,content,null,null) ;
            smsSendStrategy.setUrl(prepareParamService.getParam("fenghuo_url").getParamValue());
            resultStr = client.call(KyongHttpUtils.HttpMethod.POST ,smsSendStrategy);
            logger.info("调用烽火移动响应信息："+resultStr);
            String[] strs = resultStr.split(":");
            if("success".equals(strs[0])){
            	result.put("status", true);
            	result.put("msg", strs[0]);
            	result.put("reqMsgId", strs[1]);
            }else{
            	result.put("status", false);
            	result.put("msg", strs[1]);
            }
		} catch (Exception e) {
			logger.error("短信发送异常"+e);
        }
		return result;
	}
	
	public Map<String, Object> smsSendCN(String dest,String content){
		Map<String,Object> result = new HashMap<>();
		String resultStr = "";
		String username = prepareParamService.getParam("fenghuo_name_cn").getParamValue();
		String password = prepareParamService.getParam("fenghuo_pwd_cn").getParamValue();
		KyongApiClient client = new KyongApiClient(username,password);
		try {
            SmsSendStrategy smsSendStrategy = new SmsSendStrategy(dest,content,null,null) ;
            smsSendStrategy.setUrl(prepareParamService.getParam("fenghuo_url").getParamValue());
            resultStr = client.call(KyongHttpUtils.HttpMethod.POST ,smsSendStrategy);
            logger.info("调用烽火电信响应信息："+resultStr);
            String[] strs = resultStr.split(":");
            if("success".equals(strs[0])){
            	result.put("status", true);
            	result.put("msg", strs[0]);
            	result.put("reqMsgId", strs[1]);
            }else{
            	result.put("status", false);
            	result.put("msg", strs[1]);
            }
		} catch (Exception e) {
			logger.error("短信发送异常"+e);
        }
		return result;
	}
	
	public Map<String, Object> smsSendUN(String dest,String content){
		Map<String,Object> result = new HashMap<>();
		String resultStr = "";
		String username = prepareParamService.getParam("fenghuo_name_un").getParamValue();
		String password = prepareParamService.getParam("fenghuo_pwd_un").getParamValue();
		KyongApiClient client = new KyongApiClient(username,password);
		try {
            SmsSendStrategy smsSendStrategy = new SmsSendStrategy(dest,content,null,null) ;
            smsSendStrategy.setUrl(prepareParamService.getParam("fenghuo_url").getParamValue());
            resultStr = client.call(KyongHttpUtils.HttpMethod.POST ,smsSendStrategy);
            logger.info("调用烽火联通响应信息："+resultStr);
            String[] strs = resultStr.split(":");
            if("success".equals(strs[0])){
            	result.put("status", true);
            	result.put("msg", strs[0]);
            	result.put("reqMsgId", strs[1]);
            }else{
            	result.put("status", false);
            	result.put("msg", strs[1]);
            }
		} catch (Exception e) {
			logger.error("短信发送异常"+e);
        }
		return result;
	}
	
	public Map<String, Object> smsSendYX(String dest,String content){
		Map<String,Object> result = new HashMap<>();
		String resultStr = "";
		String username = prepareParamService.getParam("fenghuo_yx_name").getParamValue();
		String password = prepareParamService.getParam("fenghuo_yx_pwd").getParamValue();
		KyongApiClient client = new KyongApiClient(username,password);
		try {
            SmsSendStrategy smsSendStrategy = new SmsSendStrategy(dest,content,null,null) ;
            smsSendStrategy.setUrl(prepareParamService.getParam("fenghuo_url").getParamValue());
            resultStr = client.call(KyongHttpUtils.HttpMethod.POST ,smsSendStrategy);
            logger.info("调用烽火营销响应信息："+resultStr);
            String[] strs = resultStr.split(":");
            if("success".equals(strs[0])){
            	result.put("status", true);
            	result.put("msg", strs[0]);
            	result.put("reqMsgId", strs[1]);
            }else{
            	result.put("status", false);
            	result.put("msg", strs[1]);
            }
		} catch (Exception e) {
			logger.error("短信发送异常"+e);
        }
		return result;
	}
	
	
//    public static void main(String[] args) {
//        String username = "rfwyhy" ;
//        String password = "0h1we528" ;
//        //构造嗲用接口的客户端
//        KyongApiClient client = new KyongApiClient(username,password) ;
//        try {
//
//            //演示发送下行请求
//           String dest = "15010477761" ;
//            String content = "【快用科技】您的验证码是 123。" ;
//            SmsSendStrategy smsSendStrategy = new SmsSendStrategy(dest,content,null,null) ;
//            //以GET方式发送下行拉取请求
//            System.out.println(client.call(KyongHttpUtils.HttpMethod.GET,smsSendStrategy));
//            //以POST方式发送下行拉取请求
//            System.out.println(client.call(KyongHttpUtils.HttpMethod.POST ,smsSendStrategy));

            //演示发送获取余额请求
//      SmsBalanceStrategy smsBalance = new SmsBalanceStrategy() ;
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
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
