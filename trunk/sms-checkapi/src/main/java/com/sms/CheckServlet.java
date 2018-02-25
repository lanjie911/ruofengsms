package com.sms;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Set;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sms.util.SensitivewordFilter;

/**
 * CaoChengfan
 * Servlet implementation class BaseServlet
 */
@WebServlet("/CheckApi")
public class CheckServlet extends BaseServlet{

	private static Logger logger = Logger.getLogger(CheckServlet.class);
	private static final long serialVersionUID = 1L;
	
	private static CheckServlet instance;  
	
	public static CheckServlet getInstance() throws IOException {  
	    if (instance == null) {
	        instance = new CheckServlet();  
	    }  
	    return instance;  
	    } 
	
	public CheckServlet() throws IOException {
		super();
	}	
    
    public void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
    	final AsyncContext t = request.startAsync();
		t.start(new Runnable() {
			@Override
			public void run() {
				try {
					excuteMain(request, response);
				} catch (Exception e) {
					logger.error("SensitiveServlet.doPost-ERROR:", e);
				}
				t.complete();
			}
		});
    }

    private void excuteMain(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		
    	String data = URLDecoder.decode(request.getParameter("content"), "UTF-8");
        logger.info("请求信息content:"+data);
        JSONObject jsonObject = JSON.parseObject(data);
        String transCode = request.getParameter("transCode");
		String resultStr = response("0000", "SUCCESS").toString();
		
        try {
        	if(null != transCode && "300".equals(transCode)){
        		resultStr = doAll(jsonObject,resultStr);		//模板+敏感字
        	}else if(null != transCode && "200".equals(transCode)){
        		resultStr = doTemplate(jsonObject,resultStr);		//模板
        	}else if(null != transCode && "100".equals(transCode)){
        		resultStr = doSensitive(jsonObject,resultStr);		//敏感字
        	}else{
        		reload(resultStr);								//重载模板敏感字
        	}
        	logger.info("SMS服务响应信息："+resultStr);
        } catch (Exception e) {
        	logger.info("SMS服务异常："+e);
        }finally{
        	out = response.getWriter();
    		out.write(resultStr);
        	if (out != null)
        		out.close();
        }
    }

    //模板+敏感字
    private String doAll(JSONObject jsonObject, String resultStr) throws IOException {
    	String accountNo = jsonObject.getString("accountNo");
		String msgContent = jsonObject.getString("msgContent");
		SensitivewordFilter filter = SensitivewordFilter.getInstance();
		
		boolean b = filter.matchTemplate(msgContent,accountNo);				//模板检验
		if(!b)
			return resultStr = response("9999", "模板匹配失败").toString();
		Set<String> set = filter.getSensitiveWord(msgContent, 1);			//敏感词
    	if(null == set || 0 != set.size())
    		resultStr = response("9999", "包含敏感字").toString();
    	return resultStr;
	}

	//模板
	private String doTemplate(JSONObject jsonObject, String resultStr) throws IOException {
		String accountNo = jsonObject.getString("accountNo");
		String msgContent = jsonObject.getString("msgContent");
		SensitivewordFilter filter = SensitivewordFilter.getInstance();
		boolean b = filter.matchTemplate(msgContent,accountNo);
		if(!b)
			resultStr = response("9999", "FALSE").toString();
		return resultStr;
	}

	//敏感字过滤
	private String doSensitive(JSONObject jsonObject, String resultStr) throws IOException {
		String msgContent = jsonObject.getString("msgContent");
		SensitivewordFilter filter = SensitivewordFilter.getInstance();
    	Set<String> set = filter.getSensitiveWord(msgContent, 1);
    	if(null == set || 0 != set.size())
    		resultStr = response("9999", "FALSE").toString();
    	return resultStr;
	}
	
	public  JSONObject response(String retcode,String retmsg){
		JSONObject json = new JSONObject();
		json.put("code", retcode);
		json.put("msg", retmsg);
		return json;
	}
	
	public void reload(String resultStr){
		try {
			init();
		} catch (ServletException e) {
			resultStr = response("9999", "FALSE").toString();
		}
	}

}
