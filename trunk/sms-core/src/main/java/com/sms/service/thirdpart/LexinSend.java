package com.sms.service.thirdpart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sms.service.PrepareParamService;

/**
 *
 * @author Administrator
 */
@Service
public class LexinSend {
	
	private static Logger logger = LoggerFactory.getLogger(MeiLianSend.class);
	
	@Autowired
	private PrepareParamService prepareParamService;
	
	 public Map<String, Object> sendMsgCMCC(String mobile,String content){
		Map<String,Object> result = new HashMap<>();
		try {
			String username = prepareParamService.getParam("lexin_name_cmcc").getParamValue();
			String password = prepareParamService.getParam("lexin_pwd_cmcc").getParamValue();
			String sprdid = prepareParamService.getParam("lexin_product_id").getParamValue();
			String PostData = "sname="+username+"&spwd="+password+"&scorpid=&sprdid="+sprdid+"&sdst="+mobile+"&smsg="+java.net.URLEncoder.encode(content,"utf-8");
			String ret = SMS(PostData, "http://cf.51welink.com/submitdata/Service.asmx/g_Submit");
			String State =dealSubString(ret,"<State>","</State>");
	    	String MsgID =dealSubString(ret,"<MsgID>","</MsgID>");
	    	if("0".equals(State)){
	    		result.put("status", true);
            	result.put("reqMsgId", MsgID);
	    	}else{
	    		result.put("status", false);
	    	}
		} catch (UnsupportedEncodingException e) {
			logger.info(e.toString());
		}
		return result;
	 }
	 
	 public Map<String, Object> sendMsgUN(String mobile,String content){
			Map<String,Object> result = new HashMap<>();
			try {
				String username = prepareParamService.getParam("lexin_name_un").getParamValue();
				String password = prepareParamService.getParam("lexin_pwd_un").getParamValue();
				String sprdid = prepareParamService.getParam("lexin_product_id").getParamValue();
				String PostData = "sname="+username+"&spwd="+password+"&scorpid=&sprdid="+sprdid+"&sdst="+mobile+"&smsg="+java.net.URLEncoder.encode(content,"utf-8");
				String ret = SMS(PostData, "http://cf.51welink.com/submitdata/Service.asmx/g_Submit");
				String State =dealSubString(ret,"<State>","</State>");
		    	String MsgID =dealSubString(ret,"<MsgID>","</MsgID>");
		    	if("0".equals(State)){
		    		result.put("status", true);
	            	result.put("reqMsgId", MsgID);
		    	}else{
		    		result.put("status", false);
		    	}
			} catch (UnsupportedEncodingException e) {
				logger.info(e.toString());
			}
			return result;
		 }
	 
	 public Map<String, Object> sendMsgCN(String mobile,String content){
			Map<String,Object> result = new HashMap<>();
			try {
				String username = prepareParamService.getParam("lexin_name_cn").getParamValue();
				String password = prepareParamService.getParam("lexin_pwd_cn").getParamValue();
				String sprdid = prepareParamService.getParam("lexin_product_id").getParamValue();
				String PostData = "sname="+username+"&spwd="+password+"&scorpid=&sprdid="+sprdid+"&sdst="+mobile+"&smsg="+java.net.URLEncoder.encode(content,"utf-8");
				String ret = SMS(PostData, "http://cf.51welink.com/submitdata/Service.asmx/g_Submit");
				String State =dealSubString(ret,"<State>","</State>");
		    	String MsgID =dealSubString(ret,"<MsgID>","</MsgID>");
		    	if("0".equals(State)){
		    		result.put("status", true);
	            	result.put("reqMsgId", MsgID);
		    	}else{
		    		result.put("status", false);
		    	}
			} catch (UnsupportedEncodingException e) {
				logger.info(e.toString());
			}
			return result;
		 }

    public String SMS(String postData, String postUrl) {
        try {
            //发送POST请求
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();

            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("connect failed!");
                return "";
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return "";
    }
    
    public String dealSubString(String oldStr,String front,String behind){
		return oldStr.substring(oldStr.indexOf(front)+front.length(),oldStr.indexOf(behind));
    }
    
}
