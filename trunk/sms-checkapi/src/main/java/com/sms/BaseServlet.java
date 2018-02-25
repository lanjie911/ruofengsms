package com.sms;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import com.sms.util.SensitiveWordInit;

/**
 * CaoChengfan
 * Servlet implementation class HttpServlet
 */
@WebServlet("/SmsApi")
public class BaseServlet  extends HttpServlet{
	
	private static final long serialVersionUID = 1629057448325888066L;

	private static String resource="mybatis-config.xml";
	private static Logger logger = Logger.getLogger(BaseServlet.class);
	public SqlSessionFactory factory;
	
	public static Map sensitiveWordMap = null;
	public static Map<String,Object>templateMap = null;
	public static int minMatchTYpe = 1;
	public static int maxMatchType = 2;
	
	public void init() throws ServletException {  
        this.sensitiveWordMap = new SensitiveWordInit().initKeyWord();
        this.templateMap = new SensitiveWordInit().initTemplate();
        logger.info("模板已加载完毕。");
    } 
	
	public BaseServlet() throws IOException {
		InputStream is = Resources.getResourceAsStream(resource);
		factory = new SqlSessionFactoryBuilder().build(is);
	}
	
}
