package com.sms.service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dao.primarydao.PlainSendRecordDao;
import com.sms.dao.primarydao.PlainSendRespDao;
import com.sms.dto.jumeng.ReportDataDto;
import com.sms.dto.jumeng.ReportDto;
import com.sms.entity.MercAccount;
import com.sms.entity.PlainSendRecord;
import com.sms.entity.PlainSendResp;
import com.sms.service.executor.MyExecutor;
import com.sms.service.send.MercAccountService;

@Service
public class DealJuMengCallbackService {
	private static Logger logger = LoggerFactory.getLogger(DealJuMengCallbackService.class);

	@Autowired
	private PlainSendRecordDao plainSendRecordDao;
	@Autowired
	private PlainSendRespDao plainSendRespDao;
	
	@Autowired
	protected PrepareParamService prepareParamService;

	@Autowired
	protected MercAccountService mercAccountService;

	@Autowired
	private MyExecutor myExecutor;

	private ExecutorService dealRespExec = null;

	@SuppressWarnings("static-access")
	@PostConstruct
	private void init() {
		this.logger = LoggerFactory.getLogger(this.getClass());
		dealRespExec = myExecutor.getExecutors();
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	private Class getEntityClass() {
		return null;
	}

	public void prepareJuMengRespData(String reportXml,String dateTime) {
		dealRespExec.submit(new Runnable() {
			@Override
			public void run() {
				try {
					dealReportXml(reportXml,dateTime);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		});
	}

	private void dealReportXml(String reportXml,String dateTime) throws Exception {

		JAXBContext context = JAXBContext.newInstance(ReportDataDto.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		ReportDataDto sc = (ReportDataDto) unmarshaller.unmarshal(new StringReader(reportXml));
		List<ReportDto> list = sc.getList();
		if (list.isEmpty() || list.size() == 0)
			return;
		int count =0;
		int sendStatus;
		List<PlainSendResp> respList = new ArrayList<PlainSendResp>();
		PlainSendResp tmpBean = null;
		for (ReportDto dto : list) {
			sendStatus = dto.getStatus().equals("0") ? 500 : 300;
			tmpBean = new PlainSendResp(dto.getTaskid(),dto.getMobile(),sendStatus,dto.getErrcode(),dateTime);
			count = plainSendRespDao.insert(tmpBean);
			if(count>0){
				respList.add(tmpBean);
			}
		}
		int successcount=0;//成功数,失败数
		int faiedcount=0;
		if(count>0){
			PlainSendRecord plainSendRecord = plainSendRecordDao.getByreqMsgIdAndMobile(respList.get(0).getReqMsgId(), respList.get(0).getMobile());
			if (null != plainSendRecord) {
				MercAccount mercAccount = prepareParamService.getMercAccount(plainSendRecord.getAccountNo());
				if (null != mercAccount) {
					if (null != respList && respList.size() > 0) {
						for (int i = 0; i < respList.size(); i++) {
							PlainSendRecord plainSend = plainSendRecordDao.getByreqMsgIdAndMobile(respList.get(i).getReqMsgId(), respList.get(i).getMobile());
							 String content = plainSend.getContent();
							 if(500==respList.get(i).getSendStatus()){
									 if(content.length() <= 70){
										 successcount+=1;
									 }else{
										 successcount+= (int) (Math.ceil(content.length()/67d)); 
									 }
								}else{
									 if(content.length() <= 70){
										 faiedcount+=1;
									 }else{
										 faiedcount+= (int) (Math.ceil(content.length()/67d)); 
									 }
								}	 
						}
					}	
					
				}
				if(successcount>0){
					mercAccountService.batchUnFrozenBalance(mercAccount.getAccountNo(), successcount);
					logger.info("mercAccountService.batchUnFrozenBalance():"+mercAccount.getAccountNo()+",successcount:" + successcount);
				}
				if(faiedcount>0){
					if (100 == mercAccount.getChargingMethods()) {
						mercAccountService.batchUnFrozenBalance(mercAccount.getAccountNo(), faiedcount);
						logger.info("mercAccount.getChargingMethods():" + mercAccount.getChargingMethods()+",faiedcount:"+faiedcount);
					} else if (200 == mercAccount.getChargingMethods()) {
						mercAccountService.batchDoCorect(mercAccount.getAccountNo(), faiedcount);
						logger.info("mercAccount.getChargingMethods():" + mercAccount.getChargingMethods()+",faiedcount:"+faiedcount);
					}
				
				}
				
			}
		}
	}
}
	