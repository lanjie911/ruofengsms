package testServlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.eclipse.jetty.servlet.ServletContextHandler.JspConfig;

import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("deprecation")
public class HttpUtil{
	
	public static String httpPost(String url, String transCode, JSONObject j){
	        //post请求返回结果
	        DefaultHttpClient httpClient = new DefaultHttpClient();
	        HttpPost method = new HttpPost(url);
	        String response = "";
	        try {
            	List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            	nvps.add(new BasicNameValuePair("transCode", transCode));
            	nvps.add(new BasicNameValuePair("content",URLEncoder.encode(j.toJSONString(),"UTF-8")));
                method.setEntity(new UrlEncodedFormEntity(nvps));
	           
	            HttpResponse result = httpClient.execute(method);
	            url = URLDecoder.decode(url, "UTF-8");
	            /**请求发送成功，并得到响应**/
	            if (result.getStatusLine().getStatusCode() == 200) {
	               
	                try {
	                    /**读取服务器返回过来的json字符串数据**/
	                	response = EntityUtils.toString(result.getEntity());
	                	System.out.println("=======>>>>>" + response);
	                } catch (Exception e) {
	                }
	                httpClient.close();
	            }
	        } catch (IOException e) {
	        }
	        return response;
	    }
	
		public static void main(String[] args) {
			
//			Executor exec = Executors.newFixedThreadPool(200);
//			
//			for(int i = 0; i<200;i++){
//				exec.execute(new Runnable() {
//					
//					@Override
//					public void run() {
						try {
					        String url ="http://127.0.0.1:10010/SmsApi";
					        JSONObject j = new JSONObject();
			            	j.put("accountNo", "10000001");
			    			j.put("msgContent", "验证码为123098，提示您保管好自己的验证码，请勿泄露！");
					        String jsonResult = httpPost(url,"300",j);
							System.out.println(jsonResult);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
//					}
//				});
//			}
			
		}

}
