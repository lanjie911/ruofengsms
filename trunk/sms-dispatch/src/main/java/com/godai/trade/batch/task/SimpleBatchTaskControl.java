package com.godai.trade.batch.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SimpleBatchTaskControl {

	private static final Logger logger = LoggerFactory.getLogger(SimpleBatchTaskControl.class);
	private final long TIMEOUT = 60l;

	/**
	 * 关闭线程池
	 * @param pool
	 */
	public void shutdownAndAwaitTermination(ExecutorService pool, String poolName) {
		pool.shutdown(); // Disable new tasks from being submitted
		try {
			// Wait a while for existing tasks to terminate
			if (!pool.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
				pool.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!pool.awaitTermination(TIMEOUT, TimeUnit.SECONDS)) {
					logger.error("@@@#####executor service pool:{} shutdown error", poolName);
				}else{
					logger.info("#####executor service pool:{} shutdown successfully", poolName);
				}
			} else {
				logger.info("#####executor service pool:{} shutdown successfully", poolName);
			}
		} catch (InterruptedException ie) {
			logger.error("@@@#####executor service pool shutdown error", ie);
			// (Re-)Cancel if current thread also interrupted
			pool.shutdownNow(); 
		}
	}
}