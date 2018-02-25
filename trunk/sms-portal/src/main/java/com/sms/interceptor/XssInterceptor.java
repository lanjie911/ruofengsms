package com.sms.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**  防止SQL注入的拦截器 **/
public class XssInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String[] values = request.getParameterValues(name);
			for (String value : values) {
				value = clearXss(value);
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e)
			throws Exception {
	}

	/** * 处理字符转义 * * @param value * @return */
	private String clearXss(String value) {
		if (value == null || "".equals(value)) {
			return value;
		}
		value = value.replaceAll("<", "<").replaceAll(">", ">");
		value = value.replaceAll("\\(", "(").replace("\\)", ")");
		value = value.replaceAll("'", "'");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replace("script", "");
		return value;
	}
}
