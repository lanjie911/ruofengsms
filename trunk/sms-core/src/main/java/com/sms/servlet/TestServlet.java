package com.sms.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader reader = null;
		String jsonStr = null;
		StringBuilder reqStr = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			while ((jsonStr = reader.readLine()) != null)
				reqStr.append(jsonStr);
			reader.close();// 关闭输入流
			String requestStr = reqStr.toString();
			System.out.println("请求数据：" + requestStr);
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			response.getOutputStream().write(requestStr.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}