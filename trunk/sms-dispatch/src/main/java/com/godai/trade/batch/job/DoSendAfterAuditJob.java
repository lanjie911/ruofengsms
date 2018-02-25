package com.godai.trade.batch.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.godai.trade.batch.task.DoSendAfterAuditTask;
import com.godai.trade.common.exception.TradeException;
import com.godai.trade.dao.AuditingDao;
import com.godai.trade.entity.Auditing;
import com.sms.tradeservice.api.service.TradeForDispatchService;

/**
 * 每日授信到期处理任务
 */
@Component("doSendAfterAuditJob")
public class DoSendAfterAuditJob extends SimpleJobService {

	private static final Logger logger = LoggerFactory.getLogger(DoSendAfterAuditJob.class);
	
	@Value("${tk0003.batchSize}")
	private int batchSize; // 一批次多少条记录
	
	@Value("${tk0003.dispatchPoolSize}")
	private int dispatchPoolSize;

	@Autowired
	private TradeForDispatchService tradeForDispatchService;
	
	@Autowired
	private AuditingDao auditingDao;
	
	/**
	 * 具体任务执行
	 * @param callables
	 * @param es 
	 * @throws CrmMsException
	 * @throws ExecutionException
	 * @throws InterruptedException 
	 */
	private void doDispatchTask(List<Callable<String>> callables, ExecutorService es)
			throws TradeException, ExecutionException, InterruptedException {
		/*List<Future<String>> futures = es.invokeAll(callables, 60, TimeUnit.MINUTES);
		for (Future<String> future : futures) {
			JSONObject context = JSON.parseObject(future.get());
			String respCode = context.getString("code"); // 响应码
			if (null == respCode) {
				logger.error("romote doSendAfterAuditJob.handleTask fail, response: {} ", context);
				throw new TradeException("unkown error", "未知异常", context);
			} else if (!"0000".equals(respCode)) {
				logger.error("romote doSendAfterAuditJob.handleTask fail, response: {} ", context);
				throw new TradeException(respCode, context.getString("message"), context);
			} else {
				logger.info("handle doSendAfterAuditJob.handleTask [{}] successfully.", context);
			}
		}*/
	}

	@Override
	protected void handleTask() throws Exception {
		/*Map<String, Object> params = new HashMap<String, Object>();
		params.put("startIndex", 0);
		params.put("offset", batchSize);
		
		List<Auditing> list = auditingDao.getNeedToSend(params);
		
		if (null == list || list.size() == 0) return; 
		
		// 创建线程池
		ExecutorService es = null;
		try {
			// 组织一次分发要执行的任务集合
			List<Callable<String>> callables = new ArrayList<Callable<String>>();
			for (int j = 0; j < list.size(); j++) {
				Auditing auditing = list.get(j);
				callables.add(new DoSendAfterAuditTask(tradeForDispatchService, auditing));
			}

			// 执行任务
			if (callables.size() > 0) {
				es = Executors.newFixedThreadPool(50);
				doDispatchTask(callables, es);
			}
		} catch (TradeException e) {
			e.printStackTrace();
			logger.error("执行远程发送审核短信任务发生响应数据异常", e);
			throw e;
		} catch (ExecutionException e) {
			e.printStackTrace();
			// 任务返回的异常
			logger.error("执行远程发送审核短信任务发生响应数据异常", e);
			throw e;
		} catch (InterruptedException e) {
			e.printStackTrace();
			// 如果等待时发生中断
			logger.warn("doDispatchTask InterruptedException", e);
			throw e;
		} finally {
			if (null != es) {
				es.shutdown(); // 关闭
				logger.info("#####关闭线程池#####");
			}
		}*/
	}

	@Override
	@Autowired
	protected void registerTaskName(@Value(value = "发送审核通过短信") String taskName) {
		//logger.info("***doSendAfterAuditJob register taskName:{}", taskName);
		super.setTaskName(taskName);
	}

}