package mercinfomanager;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sms.criteria.mercmanager.MercInfoCriteria;
import com.sms.entity.mercmanager.MercInfo;
import com.sms.service.mercmanager.MercInfoService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/spring-config.xml","classpath*:/spring-hessian.xml" })
public class MercInfoTest {
	
	
	@Autowired
	private MercInfoService mercInfoService;
	
	@Test
	public void validateCardNo(){
		MercInfoCriteria criteria = new MercInfoCriteria();
		
		List<MercInfo> list = mercInfoService.query(criteria);
		
		System.out.println(list.toString());
	}
	
/*	@Test
	public void batchValidateCardNo(){
		//批量银行卡卡BIN信息获取
		//String[] bankCardNoStr = new String[]{"6225880164931256","377155022417148","6013820100012168590","6228481985018695370","爱上空间打开撒"};
		String[] bankCardNoStr = new String[]{"爱上空间打开撒"};
		List<CardBinResponse> list = cardBinValidateService.batchValidateCardNo(bankCardNoStr);
		for(CardBinResponse sc : list){
			System.out.println(sc.getReponseResult() + "\t" + sc.getReponseMsg() +"\t"+ sc.getBankName() +"\t"+ sc.getBankCardNo() +"\t"+ sc.getCardName() +"\t"+sc.getCardType() +"\t"+ sc.getBankCardNoLength());
		}
	}*/
	

}