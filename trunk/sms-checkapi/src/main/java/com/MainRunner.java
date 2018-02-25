package com;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.sms.BaseConf;
import com.sms.CheckServlet;

public class MainRunner {
	private static Logger logger = Logger.getLogger(MainRunner.class);
	
	private static Integer serverPort = Integer.valueOf(BaseConf.getString("serverPort"));
	
	public static void main(String[] args){
		
		Server server = new Server(serverPort);						//监听端口
		try {
			ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);  
	        context.setContextPath("/");
	        server.setHandler(context);
	        logger.info("SMS Server is starting.");
	        
	        // http://localhost:8088/SmsApi
	        
	        context.addServlet(new ServletHolder(CheckServlet.getInstance()), "/SmsApi");  
	        
	        server.start();  
	        server.join(); 
		} catch (Exception e) {
			logger.error("系统错误："+e);
		}
		
	}
}
