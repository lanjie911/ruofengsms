package com.sms.service.send;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sms.entity.MercAccount;
import com.sms.service.PrepareParamService;
import com.sms.util.SignUtil;
import com.sms.util.TradeException;
/**
 * @author Cao
 * 参数校验
 */
@Service
public class ValidatePramService {
	
	private static Logger logger = LoggerFactory.getLogger(ValidatePramService.class);
	
	@Value("${sms.sign.key}")
	private String validateSignKey;
	
	@Autowired
	private PrepareParamService prepareParamService;
	
	public void validateSign(JSONObject jsonObject, String reqIp) throws TradeException{
		if(StringUtils.isBlank(jsonObject.getString("password")))
			throw new TradeException("1000","{\"msg\":\"password参数为空\",\"code\":\"1000\"}");
		if(StringUtils.isBlank(jsonObject.getString("sign")))
			throw new TradeException("1000","{\"msg\":\"sign参数为空\",\"code\":\"1000\"}");
		if(StringUtils.isBlank(jsonObject.getString("accountNo")))
			throw new TradeException("1000","{\"msg\":\"accountNo参数为空\",\"code\":\"1000\"}");
		Long accountNo = jsonObject.getLongValue("accountNo");
		MercAccount mercAccount = prepareParamService.getMercAccount(accountNo);
		if(null == mercAccount)
			throw new TradeException("1000","{\"msg\":\"商户不存在\",\"code\":\"1000\"}");
		if(100 == mercAccount.getBindingIpFlag()){
			String[] ipstr = mercAccount.getAuthorizationIp().split(",");
			boolean b = false;
			for(int i=0;i<ipstr.length;i++)
				if(reqIp.equals(ipstr[i])){
					b = true;
					break;
				}
			if(!b){
				logger.error("商户账号：{}，请求IP:{} IP未授信", accountNo, reqIp);
				throw new TradeException("1000","{\"msg\":\"IP未授信\",\"code\":\"1000\"}");
			}
		}
		if(!mercAccount.getPassWord().equals(SignUtil.getSign(jsonObject.getString("password"), jsonObject.getString("sign"))))
			throw new TradeException("1000","{\"msg\":\"验签失败\",\"code\":\"1000\"}");
		if(StringUtils.isBlank(jsonObject.getString("mobile")))
			throw new TradeException("1000","{\"msg\":\"mobile参数为空\",\"code\":\"1000\"}");
		if(StringUtils.isBlank(jsonObject.getString("content")))
			throw new TradeException("1000","{\"msg\":\"content参数为空\",\"code\":\"1000\"}");
		if(StringUtils.isBlank(jsonObject.getString("messageId")))
			throw new TradeException("1000","{\"msg\":\"messageId参数为空\",\"code\":\"1000\"}");
		if(null == jsonObject.getInteger("orderFlag"))
			throw new TradeException("1000","{\"msg\":\"orderFlag参数为空\",\"code\":\"1000\"}");
		if(null == jsonObject.getString("signTip"))
			throw new TradeException("1000","{\"msg\":\"signTip参数为空\",\"code\":\"1000\"}");
		String[] signTips = mercAccount.getSignatureContent().split(",");
		boolean bo = false;
		for(int i=0;i<signTips.length;i++)
			if(jsonObject.getString("signTip").equals(signTips[i])){
				bo = true;
				break;
			}
		if(!bo && 100 == mercAccount.getValidSignMethod())
			throw new TradeException("1000","{\"msg\":\"签名未报备\",\"code\":\"1000\"}");
		if(200 == jsonObject.getInteger("orderFlag") && StringUtils.isBlank(jsonObject.getString("reservationDatetime")))
			throw new TradeException("1000","{\"msg\":\"reservationDatetime参数为空\",\"code\":\"1000\"}");
	}
	
	public void validateSignDiff(JSONObject jsonObject, String reqIp) throws TradeException{
		if(StringUtils.isBlank(jsonObject.getString("password")))
			throw new TradeException("1000","{\"msg\":\"password参数为空\",\"code\":\"1000\"}");
		if(StringUtils.isBlank(jsonObject.getString("sign")))
			throw new TradeException("1000","{\"msg\":\"sign参数为空\",\"code\":\"1000\"}");
		if(StringUtils.isBlank(jsonObject.getString("accountNo")))
			throw new TradeException("1000","{\"msg\":\"accountNo参数为空\",\"code\":\"1000\"}");
		MercAccount mercAccount = prepareParamService.getMercAccount(jsonObject.getLong("accountNo"));
		if(null == mercAccount)
			throw new TradeException("1000","{\"msg\":\"商户不存在\",\"code\":\"1000\"}");
		String[] ipstr = mercAccount.getAuthorizationIp().split(",");
		boolean b = false;
		for(int i=0;i<ipstr.length;i++)
			if(reqIp.equals(ipstr[i])){
				b = true;
				break;
			}
		if(!b)
			throw new TradeException("1000","{\"msg\":\"IP未授信\",\"code\":\"1000\"}");
		if(!mercAccount.getPassWord().equals(SignUtil.getSign(jsonObject.getString("password"), jsonObject.getString("sign"))))
			throw new TradeException("1000","{\"msg\":\"验签失败\",\"code\":\"1000\"}");
		if(StringUtils.isBlank(jsonObject.getString("content")))
			throw new TradeException("1000","{\"msg\":\"content参数为空\",\"code\":\"1000\"}");
		if(StringUtils.isBlank(jsonObject.getString("messageId")))
			throw new TradeException("1000","{\"msg\":\"messageId参数为空\",\"code\":\"1000\"}");
		if(null == jsonObject.getInteger("orderFlag"))
			throw new TradeException("1000","{\"msg\":\"orderFlag参数为空\",\"code\":\"1000\"}");
		if(null == jsonObject.getString("signTip"))
			throw new TradeException("1000","{\"msg\":\"signTip参数为空\",\"code\":\"1000\"}");
		String[] signTips = mercAccount.getSignatureContent().split(",");
		boolean bo = false;
		for(int i=0;i<signTips.length;i++)
			if(jsonObject.getString("signTip").equals(signTips[i])){
				bo = true;
				break;
			}
		if(!bo && 100 == mercAccount.getValidSignMethod())
			throw new TradeException("1000","{\"msg\":\"签名未报备\",\"code\":\"1000\"}");
		if(200 == jsonObject.getInteger("orderFlag") && StringUtils.isBlank(jsonObject.getString("reservationDatetime")))
			throw new TradeException("1000","{\"msg\":\"reservationDatetime参数为空\",\"code\":\"1000\"}");
	}
	
	public void validateSignReceive(JSONObject jsonObject,String reqIp) throws TradeException{
		if(StringUtils.isBlank(jsonObject.getString("password")))
			throw new TradeException("1000","{\"msg\":\"password参数为空\",\"code\":\"1000\"}");
		if(StringUtils.isBlank(jsonObject.getString("sign")))
			throw new TradeException("1000","{\"msg\":\"sign参数为空\",\"code\":\"1000\"}");
		if(StringUtils.isBlank(jsonObject.getString("accountNo")))
			throw new TradeException("1000","{\"msg\":\"accountNo参数为空\",\"code\":\"1000\"}");
		MercAccount mercAccount = prepareParamService.getMercAccount(jsonObject.getLong("accountNo"));
		if(null == mercAccount)
			throw new TradeException("1000","{\"msg\":\"商户不存在\",\"code\":\"1000\"}");
		String[] ipstr = mercAccount.getAuthorizationIp().split(",");
		boolean b = false;
		for(int i=0;i<ipstr.length;i++)
			if(reqIp.equals(ipstr[i])){
				b = true;
				break;
			}
		if(!b)
			throw new TradeException("1000","{\"msg\":\"IP未授信\",\"code\":\"1000\"}");
		if(!mercAccount.getPassWord().equals(SignUtil.getSign(jsonObject.getString("password"), jsonObject.getString("sign"))))
			throw new TradeException("1000","{\"msg\":\"验签失败\",\"code\":\"1000\"}");
		if(StringUtils.isBlank(jsonObject.getString("mobile")))
			throw new TradeException("1000","{\"msg\":\"accountNo参数为空\",\"code\":\"1000\"}");
		if(StringUtils.isBlank(jsonObject.getString("content")))
			throw new TradeException("1000","{\"msg\":\"content参数为空\",\"code\":\"1000\"}");
	}
	
	public void validateSignBalance(JSONObject jsonObject,String reqIp) throws TradeException{
		if(StringUtils.isBlank(jsonObject.getString("password")))
			throw new TradeException("1000","{\"msg\":\"password参数为空\",\"code\":\"1000\"}");
		if(StringUtils.isBlank(jsonObject.getString("sign")))
			throw new TradeException("1000","{\"msg\":\"sign参数为空\",\"code\":\"1000\"}");
		if(StringUtils.isBlank(jsonObject.getString("accountNo")))
			throw new TradeException("1000","{\"msg\":\"accountNo参数为空\",\"code\":\"1000\"}");
		MercAccount mercAccount = prepareParamService.getMercAccount(jsonObject.getLong("accountNo"));
		if(null == mercAccount)
			throw new TradeException("1000","{\"msg\":\"商户不存在\",\"code\":\"1000\"}");
		String[] ipstr = mercAccount.getAuthorizationIp().split(",");
		boolean b = false;
		for(int i=0;i<ipstr.length;i++)
			if(reqIp.equals(ipstr[i])){
				b = true;
				break;
			}
		if(!b)
			throw new TradeException("1000","{\"msg\":\"IP未授信\",\"code\":\"1000\"}");
		if(!mercAccount.getPassWord().equals(SignUtil.getSign(jsonObject.getString("password"), jsonObject.getString("sign"))))
			throw new TradeException("1000","{\"msg\":\"验签失败\",\"code\":\"1000\"}");
	}
	
	public void validateSignResult(JSONObject jsonObject,String reqIp) throws TradeException{
		if(StringUtils.isBlank(jsonObject.getString("password")))
			throw new TradeException("1000","{\"msg\":\"password参数为空\",\"code\":\"1000\"}");
		if(StringUtils.isBlank(jsonObject.getString("sign")))
			throw new TradeException("1000","{\"msg\":\"sign参数为空\",\"code\":\"1000\"}");
		if(StringUtils.isBlank(jsonObject.getString("accountNo")))
			throw new TradeException("1000","{\"msg\":\"accountNo参数为空\",\"code\":\"1000\"}");
		MercAccount mercAccount = prepareParamService.getMercAccount(jsonObject.getLong("accountNo"));
		if(null == mercAccount)
			throw new TradeException("1000","{\"msg\":\"商户不存在\",\"code\":\"1000\"}");
		String[] ipstr = mercAccount.getAuthorizationIp().split(",");
		boolean b = false;
		for(int i=0;i<ipstr.length;i++)
			if(reqIp.equals(ipstr[i])){
				b = true;
				break;
			}
		if(!b)
			throw new TradeException("1000","{\"msg\":\"IP未授信\",\"code\":\"1000\"}");
		if(!mercAccount.getPassWord().equals(SignUtil.getSign(jsonObject.getString("password"), jsonObject.getString("sign"))))
			throw new TradeException("1000","{\"msg\":\"验签失败\",\"code\":\"1000\"}");
		if(StringUtils.isBlank(jsonObject.getString("orderNo")))
			throw new TradeException("1000","{\"msg\":\"orderNo参数为空\",\"code\":\"1000\"}");
	}
	
	public void validateSignReload(JSONObject jsonObject) throws TradeException{
		if(StringUtils.isBlank(jsonObject.getString("password")))
			throw new TradeException("1000","{\"msg\":\"password参数为空\",\"code\":\"1000\"}");
		if(!"boss".equals(jsonObject.getString("password")))
			throw new TradeException("1000","{\"msg\":\"验签失败\",\"code\":\"1000\"}");
		if(StringUtils.isBlank(jsonObject.getString("transCode")))
			throw new TradeException("1000","{\"msg\":\"transCode参数为空\",\"code\":\"1000\"}");
	}
	
	public static void main(String[] args) {
		String ss = "【掌e贷】";
		String[]  sskk =  ss.split(",");
		System.out.println(sskk[0]);
	}
	
}
