package com.sms.service.send;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;


/**
* Cao
* 
*/
@Service
public class ThreadJobExecutor {
	private ExecutorService exec = null;
	
	public ThreadJobExecutor(){
		exec = Executors.newFixedThreadPool(10); 
	}
	
	public void execute(final Runnable r){
		exec.submit(r);
	}
}