package com.sms.service.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class MyExecutor {

	private ExecutorService exec;

	@PostConstruct
	public void init() {
		exec = Executors.newFixedThreadPool(3);
	}

	public ExecutorService getExecutors() {
		return exec;
	}

}
