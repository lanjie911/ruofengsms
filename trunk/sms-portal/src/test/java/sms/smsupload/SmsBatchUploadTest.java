package sms.smsupload;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sms.dao.smsupload.SmsBatchUploadDao;
import com.sms.entity.smsupload.SmsBatchUpload;

import sms.BaseTest;

public class SmsBatchUploadTest extends BaseTest {
	
	@Autowired
	private SmsBatchUploadDao smsBatchUploadDao;
	
	@Test
	public void insert(){
		SmsBatchUpload sc = new SmsBatchUpload();
		sc.setAccountNo(1029910l);
		sc.setAccountType(200);
		sc.setMobileCount(1000000);
		sc.setOperator("张国强");
		sc.setBatchType(200);
		int resultRow = smsBatchUploadDao.insert(sc);
		System.out.println("受影响行数：" + resultRow);
		
		SmsBatchUpload newSmsBatchUpload = smsBatchUploadDao.getById(sc.getSmsUploadId());
		System.out.println(newSmsBatchUpload.toString());
	}
}