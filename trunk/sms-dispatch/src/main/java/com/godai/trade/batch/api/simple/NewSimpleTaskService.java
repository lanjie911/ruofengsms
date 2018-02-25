package com.godai.trade.batch.api.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.godai.trade.batch.api.TaskService;


/**
 * 新任务处理服务
 * @Description 
 * @author bqct_bya
 * @CreateTime 2017年3月6日 上午10:57:53
 */
public abstract class NewSimpleTaskService implements Runnable, TaskService{

	protected static final Logger logger = LoggerFactory.getLogger(NewSimpleTaskService.class);

	private String taskName; // 任务名称
	
	@Value("${email.username}")
	private String fromMail; // 发送邮箱
	


	@Override
	public void run() {
		logger.info("#####{} task begin#####", taskName);
		doit();
		logger.info("#####{} task over#####", taskName);
	}


	@Override
	public void doit() {
		logger.info("#####{} start doit#####", taskName);
		try {
			handleTask();
		} catch (Exception e) {
			logger.error("do " + taskName + " exception", e);
		}
		logger.info("#####{} end doit#####", taskName);
	}
	
	/**
	 * 任务处理
	 * @throws Exception 
	 */
	protected abstract void handleTask() throws Exception;

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * 注册任务名称
	 * @param taskName
	 */
	protected abstract void registerTaskName(String taskName);

}
