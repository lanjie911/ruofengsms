//package com.sms;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.util.Set;
//
//import javax.servlet.AsyncContext;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.log4j.Logger;
//
//import com.alibaba.fastjson.JSONObject;
//import com.sms.util.SensitivewordFilter;
//
///**
// * CaoChengfan
// * Servlet implementation class BaseServlet
// */
//@WebServlet("/SensitiveApi")
//public class SensitiveServlet extends BaseServlet{
//
//	private static Logger logger = Logger.getLogger(SensitiveServlet.class);
//	private static final long serialVersionUID = 1L;
//	
//	public SensitiveServlet() throws IOException {
//		super();
//	}	
//    
//    public void doPost(final HttpServletRequest request, final HttpServletResponse response)
//            throws ServletException, IOException {
//    	final AsyncContext t = request.startAsync();
//		t.start(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					excuteMain(request, response);
//				} catch (Exception e) {
//					logger.error("SensitiveServlet.doPost-ERROR:", e);
//				}
//				t.complete();
//			}
//		});
//    }
//
//    private void excuteMain(HttpServletRequest request, HttpServletResponse response) throws Exception {
//    	BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
//    	String jsonStr = null;
//    	StringBuilder requestStr = new StringBuilder();
//    	
//    	try {
//	    	while ((jsonStr = reader.readLine()) != null) {
//	    		requestStr.append(jsonStr);
//	    	}
//    	} catch (IOException e) {
//    		e.printStackTrace();
//    	}
//    	reader.close();// 关闭输入流
//    	PrintWriter out = null;
//    	
//    	response.setCharacterEncoding("UTF-8");							//初始化返回参数
//		response.setContentType("application/json; charset=utf-8");
//		String resultStr = "{\"msg\":\"SUCCESS\",\"code\":\"0000\"}";
//		
//		JSONObject jsonObject = JSONObject.parseObject(requestStr.toString()); // 取一个json转换为对象
//    	logger.info("SMS服务接收请求信息："+jsonObject);
//        try {
//        	String transCode = jsonObject.getString("transCode");
//        	if(null != transCode && "100".equals(transCode)){
//        		resultStr = doSensitive(jsonObject,resultStr);		//敏感字
//        	}else{
//        		resultStr = doTemplate(jsonObject,resultStr);		//模板
//        	}
//        	logger.info("SMS服务响应信息："+resultStr);	
//    		out = response.getWriter();
//    		out.write(resultStr);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally{
//            //关闭资源
//        	 if (out != null)
//        		 out.close();
//        	if(reader != null){
//        		try {
//        			reader.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//        		reader=null;
//        	}
//        }
//    }
//
//    //模板
//	private String doTemplate(JSONObject jsonObject, String resultStr) throws IOException {
//		String accountNo = jsonObject.getString("accountNo");
//		String msgContent = jsonObject.getString("msgContent");
//		SensitivewordFilter filter = new SensitivewordFilter();
//		boolean b = filter.matchTemplate(msgContent,accountNo);
//		if(!b)
//			resultStr = "{\"msg\":\"FALSE\",\"code\":\"9999\"}";
//		return resultStr;
//	}
//
//	//敏感字过滤
//	private String doSensitive(JSONObject jsonObject, String resultStr) throws IOException {
//		String msgContent = jsonObject.getString("msgContent");
//    	SensitivewordFilter filter = new SensitivewordFilter();
//    	Set<String> set = filter.getSensitiveWord(msgContent, 1);
//    	logger.info(set);
//    	if(null == set || 0 != set.size())
//    		resultStr = "{\"msg\":\"FALSE\",\"code\":\"9999\"}";
//    	return resultStr;
//	}
//
//}
