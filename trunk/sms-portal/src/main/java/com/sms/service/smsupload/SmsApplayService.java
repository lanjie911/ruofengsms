package com.sms.service.smsupload;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dao.smsupload.SmsApplayDao;
import com.sms.dao.smsupload.SmsApplayDetailDao;
import com.sms.dao.smsupload.SmsDetailUploadDao;
import com.sms.entity.smsupload.SmsApplay;
import com.sms.entity.smsupload.SmsApplayDetail;
import com.sms.entity.smsupload.SmsDetailUpload;
import com.sms.service.idfactory.SerialIdFactory;
import com.sms.util.IndividuationExcel;
import com.sms.util.IpUtil;
import com.sms.util.NormalExcelUtil;

@Service
public class SmsApplayService {
	
	private Logger logger = LoggerFactory.getLogger(SmsApplayService.class);

	@Autowired
	private SmsApplayDao smsApplayDao;
	
	@Autowired
	private SmsApplayDetailDao smsApplayDetailDao;
	
	@Autowired
	private SmsDetailUploadDao smsDetailUploadDao;
	
	@Autowired
	private SerialIdFactory idFactory;
	
	@Autowired
	private AnalysisIndividuation analysisIndividuation;
	
	public void doSendSms(SmsApplay smsApplay) {
		int modifyRow = smsApplayDao.uploadFinish(smsApplay);
		if(modifyRow != 1)
			throw new RuntimeException("数据提交失败");
		if(100 == smsApplay.getBatchType()){
			// 普通批量发送
			smsApplayDetailDao.uploadFinish(smsApplay.getBatchNo());
		}else if(200 == smsApplay.getBatchType()){
			// 高级批量发送
			analysisIndividuation.analysisSms(smsApplay.getSmsContent(), 
					smsApplay.getBatchNo(), smsApplay.getMobileColumn(), smsApplay.getSignTip());
		}
	}
	
	public SmsApplay genSmsApplayByExcel(InputStream input, Long accountNo, Integer accountType, 
			String merchantName, String operator) throws Exception {
		idFactory.setLength(0);
		idFactory.setDatePrefix(true);
		idFactory.setSuffix(IpUtil.getLocalIP().trim().substring(IpUtil.getLocalIP().trim().lastIndexOf(".") + 1));
		Long batchNo = Long.valueOf(idFactory.generate());
		
		NormalExcelUtil normalExcelUtil = new NormalExcelUtil();
		normalExcelUtil.readExcelNormal(input, true, batchNo, accountNo, accountType);
		List<SmsApplayDetail> list = normalExcelUtil.getSmsApplayDetailList();
		if(list.isEmpty() || list.size() == 0)
			throw new RuntimeException("Excel数据加载为空");
        String textAreaVal = normalExcelUtil.getTextAreaVal();
        
        logger.info("批次号：{}，错误数据：{}", batchNo, normalExcelUtil.errorMobiles);
        System.out.println("批次号：" +batchNo+ "，错误数据：" + normalExcelUtil.errorMobiles);
        
        int excelRowNum = normalExcelUtil.total;			// Excel总记录条数
        int repeatCount = normalExcelUtil.repeatCount;		// Excel手机号重复数量
        int succCount = normalExcelUtil.succCount;			// 正确处理的手机号
        int errorCount = normalExcelUtil.errorCount;		// 格式错误手机号数量
        
        SmsApplay smsApplay = new SmsApplay();
        smsApplay.setBatchNo(batchNo);
        smsApplay.setAccountNo(accountNo);
        smsApplay.setAccountType(200);
        smsApplay.setAccountName(merchantName);
        smsApplay.setMobileCount(excelRowNum);				// Excel总记录条数
        smsApplay.setRepeatCount(repeatCount);
        smsApplay.setOutlierCount(errorCount);
        smsApplay.setSuccCount(succCount);
        smsApplay.setBatchType(100);
        smsApplay.setOperator(operator);
        smsApplay.setTextAreaVal(textAreaVal);
		
		int rows = smsApplayDao.insert(smsApplay);
		if(rows != 1)
			throw new RuntimeException("Excel数据缓存失败");
		
		if(list.size() < 20000){
			// 数据2万条以内，一个批次处理
			batchInsert(list);
		} else {
			// 数据超过1万条，分批处理
			asynchronousRecordDetail(list);
		}
		return smsApplay;
	}
	
	public SmsApplay genSmsApplayIndividuationByExcel(InputStream input, Long accountNo, Integer accountType, 
			String merchantName, String operator) throws Exception {
		idFactory.setLength(0);
		idFactory.setDatePrefix(true);
		idFactory.setSuffix(IpUtil.getLocalIP().trim().substring(IpUtil.getLocalIP().trim().lastIndexOf(".") + 1));
		Long batchNo = Long.valueOf(idFactory.generate());
		
		IndividuationExcel individuationExcel = new IndividuationExcel();
		individuationExcel.processExcel(input, false, batchNo, accountNo, accountType);
		List<SmsDetailUpload> list = individuationExcel.smsDetailUploadList;
		if(list.isEmpty() || list.size() == 0)
			throw new RuntimeException("Excel数据加载为空");
        
        int excelRowNum = individuationExcel.total;			// Excel总记录条数
        int repeatCount = individuationExcel.repeatCount;	// Excel手机号重复数量
        int succCount = individuationExcel.succCount;		// 正确处理的手机号
        
        SmsApplay smsApplay = new SmsApplay();
        smsApplay.setBatchNo(batchNo);
        smsApplay.setAccountNo(accountNo);
        smsApplay.setAccountType(200);
        smsApplay.setAccountName(merchantName);
        smsApplay.setMobileCount(excelRowNum);				// Excel总记录条数
        smsApplay.setRepeatCount(repeatCount);
        smsApplay.setOutlierCount(0);
        smsApplay.setSuccCount(succCount);
        smsApplay.setBatchType(200);
        smsApplay.setOperator(operator);
        smsApplay.setTextAreaVal(individuationExcel.textAreaVal);
        smsApplay.setHeadStr(individuationExcel.headStr);
        smsApplay.setHiddenVal(individuationExcel.hiddenVal);
		
		int rows = smsApplayDao.insert(smsApplay);
		if(rows != 1)
			throw new RuntimeException("Excel数据缓存失败");
		
		if(list.size() < 20000){
			// 数据2万条以内，一个批次处理
			batchInsertSmsDetailUpload(list);
		} else {
			// 数据超过2万条，分批处理
			asynchronousRecordUploadDetail(list);
		}
		
		return smsApplay;
	}
	
	/**
	 * 数据超过2万条，分批处理 - 普通发送
	 */
	private void asynchronousRecordDetail(final List<SmsApplayDetail> list) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
			    List<List<SmsApplayDetail>> tempList = averageAssign(list);
			    for(int i = 0; i<tempList.size(); i++){
			    	batchInsert(tempList.get(i));
			    }
				
			}
		}).start();
	}
	
	// 数据2万条以内，一个批次处理
	private int batchInsert(List<SmsApplayDetail> list) {
		return smsApplayDetailDao.insertBatch(list);
	}
	
	/**
	 * 数据超过2万条，分批处理 - 高级发送
	 */
	private void asynchronousRecordUploadDetail(final List<SmsDetailUpload> list) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				ExecutorService exec = Executors.newFixedThreadPool(20);
			    List<List<SmsDetailUpload>> tempList = averageAssign(list);
			    for(int i = 0; i<tempList.size(); i++){
			    	List<SmsDetailUpload> ss = tempList.get(i);
			    	exec.submit(new Runnable() {
						@Override
						public void run() {
							batchInsertSmsDetailUpload(ss);
						}
					});
			    }
			    exec.shutdown();
			}
		}).start();
	}
	
	private int batchInsertSmsDetailUpload(List<SmsDetailUpload> list) {
		return smsDetailUploadDao.insertSmsDetailUploadBatch(list);
	}
	
	private <T> List<List<T>> averageAssign(List<T> source){  
		int rows = source.size();
		int pageSize = 20000;
		int pageSum = rows%pageSize==0 ? rows/pageSize : rows/pageSize+1;
		List<List<T>> result=new ArrayList<List<T>>(); 
		for(int currentPage = 1; currentPage <= pageSum; currentPage++){
			List<T> value=null;
			int limit = (currentPage- 1)*pageSize;
			int offset;
			if(currentPage == pageSum){
				 offset = source.size();
			}else{
				 offset = currentPage*pageSize;
			}
			
			value=source.subList(limit, offset);
			result.add(value);
		}
	    return result;  
	}  
}