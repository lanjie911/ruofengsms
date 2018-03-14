package com.sms.servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sms.service.AbstractCallbackService;
import com.sms.util.DatetimeUtil;

public class XunQiServlet extends AbstractServlet {
	private static final long serialVersionUID = -2818309304778455126L;
	private static Logger logger = LoggerFactory.getLogger(XunQiServlet.class);
	private WebApplicationContext context;
	private AbstractCallbackService xunQiCallbackService;

	public void init(ServletConfig config) throws ServletException {
		context = WebApplicationContextUtils.findWebApplicationContext(config.getServletContext());
		xunQiCallbackService = context.getBean("xunQiCallbackService", AbstractCallbackService.class);
	}

	public void service(HttpServletRequest request, HttpServletResponse response) {
		BufferedReader reader = null;
		String jsonStr = null;
		StringBuilder result = new StringBuilder();

		try {
			reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			while ((jsonStr = reader.readLine()) != null)
				result.append(jsonStr);
			reader.close();// 关闭输入流
			String data = URLDecoder.decode(result.toString(), "UTF-8");
			logger.info("讯奇发送结果回调：{}", data);
			String currentDateTime = DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
			if (null != data && !"".equals(data))
				xunQiCallbackService.prepareRespData(data,currentDateTime);
			// 生成响应报文
			String responseStr = genRespStr();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			response.getOutputStream().write(responseStr.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("XunQiServlet.service-Exception:{}", e);
		}
	}
}