package com.godai.trade.domain;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sun.misc.Signal;

public class App {
	public static void main(String[] args) {
		AbstractApplicationContext application = new ClassPathXmlApplicationContext("classpath:spring-application.xml");
		// 添加关闭的监听，和平的关闭spring 容器
		ShutDown shutdown = new ShutDown(application);
		Signal.handle(new Signal("USR2"), shutdown);
	}
}