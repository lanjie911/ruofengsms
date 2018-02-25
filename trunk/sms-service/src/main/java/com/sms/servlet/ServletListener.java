package com.sms.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Servlet implementation class DealServlet
 */
public class ServletListener extends DispatcherServlet {
       
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		WebApplicationContextUtils.findWebApplicationContext(this.getServletContext());
	}

}
