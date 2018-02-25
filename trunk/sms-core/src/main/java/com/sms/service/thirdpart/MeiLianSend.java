package com.sms.service.thirdpart;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.service.PrepareParamService;
import com.sms.util.DatetimeUtil;

@Service
public class MeiLianSend {
	
	private static Logger logger = LoggerFactory.getLogger(MeiLianSend.class);

	@Autowired
	private PrepareParamService prepareParamService;
	
	// apikey秘钥（请登录http://m.5c.com.cn 短信平台-->账号管理-->我的信息 中复制apikey）
	
	/**
	 * HTTPS 单条短信发送
	 * @return 
	 */
	public Map<String, Object> sendSmsByMeiLian(String mobile,String content) {
		Map<String,Object> result = new HashMap<>();
		String responseStr = "";
		String encode = prepareParamService.getParam("meilian_encode").getParamValue();
		String username = prepareParamService.getParam("meilian_name").getParamValue();
		String password_md5 = toMD5(prepareParamService.getParam("meilian_pwd").getParamValue());
		String apikey = prepareParamService.getParam("meilian_apikey").getParamValue();
		String url = prepareParamService.getParam("meilian_url").getParamValue();
		// 连接超时及读取超时设置
		System.setProperty("sun.net.client.defaultConnectTimeout", "30000"); // 连接超时：30秒
		System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时：30秒

		// 新建一个StringBuffer链接
		StringBuffer buffer = new StringBuffer();

		// String encode = "GBK";
		// 页面编码和短信内容编码为GBK。重要说明：如提交短信后收到乱码，请将GBK改为UTF-8测试。如本程序页面为编码格式为：ASCII/GB2312/GBK则该处为GBK。如本页面编码为UTF-8或需要支持繁体，阿拉伯文等Unicode，请将此处写为：UTF-8

		try {
			String smsContent = URLEncoder.encode(content, encode);	// 对短信内容做Urlencode编码操作。注意：如

			// 把发送链接存入buffer中，如连接超时，可能是您服务器不支持域名解析，请将下面连接中的：【m.5c.com.cn】修改为IP：【115.28.23.78】
			buffer.append(url+"?username=" + username + "&password_md5="
					+ password_md5 + "&mobile=" + mobile + "&apikey=" + apikey + "&content=" + smsContent
					+ "&encode=" + encode);

			// https://m.5c.com.cn/api/send/index.php?username=nuoxinhy&password_md5=168699e2ce97cc86fe470a861b7ee001&mobile=18511293080&apikey=a504a8886fdb225b891ba4b758534a21&content=%E6%82%A8%E5%A5%BD%EF%BC%8C%E6%82%A8%E7%9A%84%E9%AA%8C%E8%AF%81%E7%A0%81%E6%98%AF%EF%BC%9A781234%E3%80%90%E7%BE%8E%E8%81%94%E3%80%91&encode=UTF-8
			responseStr = httpReq(buffer.toString());
			logger.info("美联响应信息：" + responseStr);
			String[] results = responseStr.split(":");
			// success:32559889527596
            if("success".equals(results[0])){
            	result.put("status", true);
            	result.put("reqMsgId", results[1]);
            }else{
            	result.put("status", false);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	//营销短信
	public Map<String, Object> sendSmsByMeiLianYX(String mobile,String content) {
		Map<String,Object> result = new HashMap<>();
		String responseStr = "";
		
		String encode = prepareParamService.getParam("meilian_encode").getParamValue();
		String username = prepareParamService.getParam("meilian_yx_name").getParamValue();
		String password_md5 = toMD5(prepareParamService.getParam("meilian_yx_pwd").getParamValue());
		String apikey = prepareParamService.getParam("meilian_yx_apikey").getParamValue();
		String url = prepareParamService.getParam("meilian_url").getParamValue();
		
		// 连接超时及读取超时设置
		System.setProperty("sun.net.client.defaultConnectTimeout", "30000"); // 连接超时：30秒
		System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时：30秒

		// 新建一个StringBuffer链接
		StringBuffer buffer = new StringBuffer();

		// String encode = "GBK";
		// 页面编码和短信内容编码为GBK。重要说明：如提交短信后收到乱码，请将GBK改为UTF-8测试。如本程序页面为编码格式为：ASCII/GB2312/GBK则该处为GBK。如本页面编码为UTF-8或需要支持繁体，阿拉伯文等Unicode，请将此处写为：UTF-8

		try {
			String smsContent = URLEncoder.encode(content, encode);	// 对短信内容做Urlencode编码操作。注意：如

			// 把发送链接存入buffer中，如连接超时，可能是您服务器不支持域名解析，请将下面连接中的：【m.5c.com.cn】修改为IP：【115.28.23.78】
			buffer.append(url+"?username=" + username + "&password_md5="
					+ password_md5 + "&mobile=" + mobile + "&apikey=" + apikey + "&content=" + smsContent
					+ "&encode=" + encode);

			// https://m.5c.com.cn/api/send/index.php?username=nuoxinhy&password_md5=168699e2ce97cc86fe470a861b7ee001&mobile=18511293080&apikey=a504a8886fdb225b891ba4b758534a21&content=%E6%82%A8%E5%A5%BD%EF%BC%8C%E6%82%A8%E7%9A%84%E9%AA%8C%E8%AF%81%E7%A0%81%E6%98%AF%EF%BC%9A781234%E3%80%90%E7%BE%8E%E8%81%94%E3%80%91&encode=UTF-8

			responseStr = httpReq(buffer.toString());
			logger.info("美联营销响应信息：" + responseStr);
			String[] results = responseStr.split(":");
			if("success".equals(results[0])){
            	result.put("status", true);
            	result.put("reqMsgId", results[1]);
            }else{
            	result.put("status", false);
            }
			// success:32559889527596
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 查询余额
	 */
	private void queryBalance() {
		try {
			String username = prepareParamService.getParam("meilian_name").getParamValue();
			String password_md5 = toMD5(prepareParamService.getParam("meilian_pwd").getParamValue());
			String apikey = prepareParamService.getParam("meilian_apikey").getParamValue();
			
			StringBuffer str = new StringBuffer("https://m.5c.com.cn/api/query/index.php?username=" + username + "&password_md5=" + password_md5 + "&apikey=" + apikey);
			System.out.println("==================请求URL==================");
			System.out.println(str);
			
			String responseStr = httpReq(str.toString());
			System.out.println("响应信息=====>>>>" + responseStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取指定号码报告
	 */
	private void querySendResultByMobile(){
		try {
			String username = prepareParamService.getParam("meilian_name").getParamValue();
			String password_md5 = toMD5(prepareParamService.getParam("meilian_pwd").getParamValue());
			String apikey = prepareParamService.getParam("meilian_apikey").getParamValue();
			
			String format = "yyyy-MM-dd HH:mm:ss";
			String startDateTime = "2017-07-28 00:00:00";
			String endDateTime = "2017-07-28 23:23:59";
			StringBuffer str = new StringBuffer("https://m.5c.com.cn/api/recv/index.php?username=" + username + "&password_md5=" + password_md5 + "&apikey=" + apikey
					+ "&from=" + DatetimeUtil.Date2TimeStamp(startDateTime, format) + "&to=" + DatetimeUtil.Date2TimeStamp(endDateTime, format) + "&mobile=18511293080");
			
			System.out.println("==================请求URL==================");
			System.out.println(str);
			
			String responseStr = httpReq(str.toString());
			System.out.println("响应信息=====>>>>" + responseStr);
			//74825207002367,18511293080,DELIVRD,2017-07-28 15:54:47;32559889527596,18511293080,DELIVRD,2017-07-28 15:55:39;79752882151165,18511293080,DELIVRD,2017-07-28 16:03:26;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 验证手机号是否平台黑名单
	 */
	private void checkMobileIsNotBlackList() {
		try {
			String username = prepareParamService.getParam("meilian_name").getParamValue();
			String password_md5 = toMD5(prepareParamService.getParam("meilian_pwd").getParamValue());
			String apikey = prepareParamService.getParam("meilian_apikey").getParamValue();
			
			StringBuffer str = new StringBuffer("https://m.5c.com.cn/api/black/index.php?username=" + username + "&password_md5=" + password_md5 + "&apikey=" + apikey + "&action=check&mobile=18511293080");
			
			System.out.println("==================请求URL==================");
			System.out.println(str);
			
			String responseStr = httpReq(str.toString());
			System.out.println("响应信息=====>>>>" + responseStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 手机号解除黑名单
	 */
	private void cancleMobileBlackList() {
		try {
			String username = prepareParamService.getParam("meilian_name").getParamValue();
			String password_md5 = toMD5(prepareParamService.getParam("meilian_pwd").getParamValue());
			String apikey = prepareParamService.getParam("meilian_apikey").getParamValue();
			
			StringBuffer str = new StringBuffer("https://m.5c.com.cn/api/black/index.php?username=" + username + "&password_md5=" + password_md5 + "&apikey=" + apikey + "&action=add&mobile=18511293080");
			
			System.out.println("==================请求URL==================");
			System.out.println(str);
			
			String responseStr = httpReq(str.toString());
			System.out.println("响应信息=====>>>>" + responseStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String httpReq(String reqStr) {
		String result = "";
		try {
			// 把buffer链接存入新建的URL中
			URL url = new URL(reqStr);

			// 打开URL链接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// 使用POST方式发送
			connection.setRequestMethod("POST");

			// 使用长链接方式
			connection.setRequestProperty("Connection", "Keep-Alive");

			// 发送短信内容
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

			// 获取返回值
			result = reader.readLine();
			reader.close();
			// 输出result内容，查看返回值，成功为success，错误为error，详见该文档起始注释
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static String toMD5(String plainText) {
		String entryStr = null;
		try {
			// 生成实现指定摘要算法的 MessageDigest 对象。
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 使用指定的字节数组更新摘要。
			md.update(plainText.getBytes());
			// 通过执行诸如填充之类的最终操作完成哈希计算。
			byte b[] = md.digest();
			// 生成具体的md5密码到buf数组
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			entryStr = buf.toString();
			// System.out.println("32位: " + buf.toString());// 32位的加密
			// System.out.println("16位: " + buf.toString().substring(8, 24));//
			// 16位的加密，其实就是32位加密后的截取
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entryStr;
	}
	
	public static void main(String[] args) {
		System.out.println(toMD5("asdf1234"));
	}
}
