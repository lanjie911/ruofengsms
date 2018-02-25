package com.sms.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AbstractServlet extends HttpServlet {
	private static final long serialVersionUID = -2818309304778455126L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		service(request, response);
	}

	protected String genRespStr() {
		Map<String, Object> respStr = new HashMap<String, Object>();
		respStr.put("trans_code", "0000");
		respStr.put("trans_msg", "操作成功");
		return respStr.toString();
	}
}