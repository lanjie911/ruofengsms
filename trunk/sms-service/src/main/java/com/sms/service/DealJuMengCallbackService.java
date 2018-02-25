package com.sms.service;

import java.io.StringReader;
import java.util.ArrayList;
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
import com.sms.dto.jumeng.ReportDataDto;
import com.sms.dto.jumeng.ReportDto;
import com.sms.entity.PlainSendRecord;
import com.sms.service.executor.MyExecutor;

@Service
public class DealJuMengCallbackService {
	private static Logger logger = LoggerFactory.getLogger(DealJuMengCallbackService.class);

	@Autowired
	private PlainSendRecordDao plainSendRecordDao;

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

	public void prepareJuMengRespData(String reportXml) {
		dealRespExec.submit(new Runnable() {
			@Override
			public void run() {
				try {
					dealReportXml(reportXml);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		});
	}

	private void dealReportXml(String reportXml) throws Exception {

		JAXBContext context = JAXBContext.newInstance(ReportDataDto.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		ReportDataDto sc = (ReportDataDto) unmarshaller.unmarshal(new StringReader(reportXml));
		List<ReportDto> list = sc.getList();
		if (list.isEmpty() || list.size() == 0)
			return;

		List<PlainSendRecord> planList = new ArrayList<PlainSendRecord>();
		int sendStatus, failedNum;
		PlainSendRecord tmpBean = null;
		for (ReportDto dto : list) {
			sendStatus = dto.getStatus().equals("0") ? 500 : 300;
			failedNum = dto.getStatus().equals("0") ? 0 : 1;
			tmpBean = new PlainSendRecord(dto.getMobile(), sendStatus, dto.getErrcode(), failedNum, dto.getTaskid(),
					dto.getReceivetime());
			planList.add(tmpBean);
		}
		plainSendRecordDao.batchUpdateByJuMeng(planList);
	}
}