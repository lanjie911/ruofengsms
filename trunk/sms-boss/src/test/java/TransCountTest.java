import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sms.criteria.dashboard.TransCountCritreia;
import com.sms.entity.dashboard.TransCount;
import com.sms.service.dashboard.DashBoradService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring-config.xml"})
@ActiveProfiles("dev")
public class TransCountTest {
	@Autowired
	private DashBoradService dashBoradService;
	
	@Test
	public void query(){
		TransCountCritreia criteria = new TransCountCritreia();
		criteria.setPaging(true);
		criteria.setOffset(0);
		criteria.setLimit(10);
		List<TransCount> records = dashBoradService.queryTransCount(criteria);
		System.out.println("长度:" + records.size());
	}
}