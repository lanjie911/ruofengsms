package com.sms.service.smsupload;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dao.smsupload.SmsApplayDao;
import com.sms.dao.smsupload.SmsApplayDetailDao;
import com.sms.entity.MercAccount;
import com.sms.entity.smsupload.SmsApplay;
import com.sms.entity.smsupload.SmsApplayDetail;
import com.sms.service.idfactory.SerialIdFactory;
import com.sms.util.IpUtil;

@Service
public class SmsApplayService {

	private Logger logger = LoggerFactory.getLogger(SmsApplayService.class);

	@Autowired
	private SmsApplayDao smsApplayDao;
	
	@Autowired
	private SmsApplayDetailDao smsApplayDetailDao;

	@Autowired
	private SerialIdFactory idFactory;

	public void insertApply(Integer orderFlag, String reservationDatetime, String messageId, String signTip,
			String content, MercAccount mercAccount, Integer sendNum, String mobile) {
		int mobileCount = 0;
		Long batchNo = null ;
		try {
			idFactory.setLength(0);
			idFactory.setDatePrefix(true);
			idFactory.setSuffix(IpUtil.getLocalIP().trim().substring(IpUtil.getLocalIP().trim().lastIndexOf(".") + 1));
			 batchNo = Long.valueOf(idFactory.generate());
			 mobileCount = mobile.split(",").length;
			SmsApplay smsApplay = new SmsApplay();
			smsApplay.setBatchNo(batchNo);
			smsApplay.setBatchType(100);
			smsApplay.setAccountNo(mercAccount.getAccountNo());
			smsApplay.setAccountType(200);
			smsApplay.setAccountName(mercAccount.getMerchantNameAbbreviation());
			smsApplay.setMobileCount(mobileCount);
			smsApplay.setSuccCount(mobileCount);
			smsApplay.setOperator(mercAccount.getOperator());
			smsApplay.setOrderFlag(orderFlag);
			logger.info("mobileCount:" + mobileCount);
			smsApplay.setSignTip(signTip);
			smsApplay.setSmsContent(content);
			smsApplayDao.insert(smsApplay);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		
		//插入审核明细表
		 List<SmsApplayDetail> list = new ArrayList<SmsApplayDetail>();
		if(mobile.trim().length() > 1){
			String[] mobileArray = mobile.trim().split(",");
			ArrayList<String> MobilesData = new ArrayList<>();
			for(int k = 0; k<mobileArray.length; k++) {
				MobilesData.add(mobileArray[k].trim());
			}
			List<List<String>> mobileList = averageAssign(MobilesData,40);
			for (int i = 0; i < mobileList.size(); i++) {
					SmsApplayDetail newSmsApplayDetail = new SmsApplayDetail();
					StringBuffer mobileStr = new StringBuffer("");
					newSmsApplayDetail.setMobileOperator(200);
					newSmsApplayDetail.setBatchType(100);
					newSmsApplayDetail.setAccountNo(mercAccount.getAccountNo());
					newSmsApplayDetail.setAccountType(200);
					newSmsApplayDetail.setBatchNo(batchNo);
					List<String> mobiles = mobileList.get(i);
					for (int j = 0; j < mobiles.size(); j++) {
						mobileStr.append(mobiles.get(j)).append(",");
					}
					newSmsApplayDetail.setMobilesData(mobileStr.toString());
					//System.out.println(mobileStr.toString());
					newSmsApplayDetail.setMobilesCount(mobiles.size());
					list.add(newSmsApplayDetail);
			}
			
		}
		
		
		if(list.size() < 200){
			// 数据200条以内，一个批次处理
			batchInsert(list);
		} else {
			// 数据超过200条，分批处理
			asynchronousRecordDetail(list);
		}
		
		
		

	}
	/**
	 * 数据超过200条，分批处理 
	 */
	private void asynchronousRecordDetail(final List<SmsApplayDetail> list) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
			    List<List<SmsApplayDetail>> tempList = averageAssign(list,200);
			    for(int i = 0; i<tempList.size(); i++){
			    	List<SmsApplayDetail> smsApplayDetaillist = tempList.get(i);
			    	for (SmsApplayDetail smsApplayDetail : smsApplayDetaillist) {
			    		 smsApplayDetailDao.insert(smsApplayDetail);	
					}
			    }
				
			}
		}).start();
	}
	
	// 数据200条以内，一个批次处理
	private int batchInsert(List<SmsApplayDetail> list) {
		int smsApplayDetailCount =0;
		for (SmsApplayDetail smsApplayDetail : list) {
			 smsApplayDetailDao.insert(smsApplayDetail);
			 smsApplayDetailCount ++;
		}
		return smsApplayDetailCount;
	}
	
	
	private <T> List<List<T>> averageAssign(List<T> source,int n){  
		int rows = source.size();
		int pageSize = n;
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