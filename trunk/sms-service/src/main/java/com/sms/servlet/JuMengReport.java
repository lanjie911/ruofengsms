package com.sms.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sms.service.DealJuMengCallbackService;

public class JuMengReport extends HttpServlet {
	private static final long serialVersionUID = -6047241528661422447L;
	private static Logger logger = LoggerFactory.getLogger(JuMengReport.class);
	private ServletConfig config;
	private WebApplicationContext context;
	private DealJuMengCallbackService dealJuMengCallbackService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		this.config = config;
		context = WebApplicationContextUtils.findWebApplicationContext(config.getServletContext());
		dealJuMengCallbackService = context.getBean(DealJuMengCallbackService.class);
	}

	public void service(HttpServletRequest request, HttpServletResponse response) {

		BufferedReader reader = null;
		String jsonStr = null;
		StringBuilder result = new StringBuilder();
		try {
			String requestIp = getIpAddr(request);
			logger.info("聚梦报告请求IP：{}", requestIp);
			reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			while ((jsonStr = reader.readLine()) != null)
				result.append(jsonStr);
			reader.close(); // 关闭输入流
			String data = URLDecoder.decode(result.toString(), "UTF-8");
			logger.info("聚梦发送结果回调：{}", data);
			if (null != data && !"".equals(data)) {
				dealJuMengCallbackService.prepareJuMengRespData(data);
			}
			String responseStr = genRespStr();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			response.getOutputStream().write(responseStr.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("JuMengReport.service-Exception:{}", e);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		service(request, response);
	}

	private String genRespStr() {
		Map<String, Object> respStr = new HashMap<String, Object>();
		respStr.put("trans_code", "0000");
		respStr.put("trans_msg", "操作成功");
		return respStr.toString();
	}

	private String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP"); // 先从nginx自定义配置获取
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
			ip = request.getRemoteAddr();
		return ip;
	}

}