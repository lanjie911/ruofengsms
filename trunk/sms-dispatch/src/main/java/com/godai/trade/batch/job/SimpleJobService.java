package com.godai.trade.batch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.godai.trade.batch.api.TaskService;
import com.godai.trade.service.api.IdFactory;

/**
 * JOB处理服务
 * @Description 
 * @author bqct_bya
 * @CreateTime 2017年3月6日 上午10:57:53
 */
public abstract class SimpleJobService implements TaskService {

	protected static final Logger logger = LoggerFactory.getLogger(SimpleJobService.class);

	private String taskName; // 任务名称

	
	@Autowired
	@Qualifier("simpleIdFactory")
	private IdFactory idFactory; 

	@Override
	public void doit() {
//		logger.info(">>>>> {} start doit#####", taskName);
		try {
			handleTask();
		} catch (Exception e) {
			logger.error("do " + taskName + " exception", e);
		}
//		logger.info("<<<<< {} end doit#####", taskName);
	}

	/**
	 * 任务处理
	 * @throws Exception 
	 */
	protected abstract void handleTask() throws Exception;

	/**
	 * 注册任务名称
	 * @param taskName
	 */
	protected abstract void registerTaskName(String taskName);


	public IdFactory getIdFactory() {
		return idFactory;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setIdFactory(IdFactory idFactory) {
		this.idFactory = idFactory;
	}
	
}
