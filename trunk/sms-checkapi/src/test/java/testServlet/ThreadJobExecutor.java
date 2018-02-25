package testServlet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
*chenqi@baiccapital.com
* @createDatetime 2016年12月5日 下午2:32:31
*/
public class ThreadJobExecutor {
	private ExecutorService exec = null;
	
	public ThreadJobExecutor(){
		exec = Executors.newFixedThreadPool(10); 
	}
	
	public void execute(final Runnable r){
		exec.submit(r);
	}
}