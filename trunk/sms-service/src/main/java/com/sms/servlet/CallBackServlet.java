package com.sms.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sms.service.DealCallbackService;
import com.sms.util.DatetimeUtil;

public class CallBackServlet extends HttpServlet {
	private static final long serialVersionUID = -2818309304778455126L;
	private static Logger logger = LoggerFactory.getLogger(CallBackServlet.class);
	private ServletConfig config;
	private WebApplicationContext context;
	private DealCallbackService dealCallbackService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		this.config = config;
		context = WebApplicationContextUtils.findWebApplicationContext(config.getServletContext());
		dealCallbackService = context.getBean(DealCallbackService.class);
	}

	public void service(HttpServletRequest request, HttpServletResponse response) {

		BufferedReader reader = null;
		String jsonStr = null;
		StringBuilder result = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			while ((jsonStr = reader.readLine()) != null)
				result.append(jsonStr);
			reader.close();// 关闭输入流
			String data = URLDecoder.decode(result.toString(), "UTF-8");
			logger.info("聚信发送结果回调：{}", data);
			// 获取当前时间
			String currentDateTime = DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
			logger.info("聚信报告返回时间：{}", currentDateTime);
			if (null != data && !"".equals(data)) {
				dealCallbackService.prepareJuxinRespData(data, currentDateTime);
			}
			// 生产响应报文
			String responseStr = genRespStr();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			response.getOutputStream().write(responseStr.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("CallBackServlet.service-Exception:{}", e);
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		service(request, response);
	}

	private String genRespStr() {
		Map<String, Object> respStr = new HashMap<String, Object>();
		respStr.put("trans_code", "0000");
		respStr.put("trans_msg", "操作成功");
		return respStr.toString();
	}

	public static void main(String[] args) {
		String data = "mid=[\"3181f72ed211bdb7,17609471060,DELIVRD\",\"a97483011ef8a213,13209496387,CU3:12\",\"e90a3a23b24feec5,18621379633,DELIVRD\",\"f9f07fb4830d9183,18639925933,DELIVRD\",\"eb435eb52447ac6b,15506998551,DELIVRD\",\"b779f74a2347b713,17609229672,DELIVRD\",\"f58e942f1cd3d103,15697368777,DELIVRD\",\"7388b3908e8e6828,18608522895,DELIVRD\",\"aa2157678fa9f829,15670881558,DELIVRD\",\"56d761bdff3d59a8,15659689959,CU3:12\",\"40d2cd969f9f5881,15574950276,DELIVRD\",\"92c072455161cf85,18604627197,DELIVRD\",\"d01798ae62de0479,13224621000,DELIVRD\",\"a0cc2d9d22d7bfae,17183600035,DELIVRD\",\"fead1a2f04094bea,18679011144,DELIVRD\",\"144ccfb4f02d10a8,18620093492,DELIVRD\",\"5cb7fb0089b85b6e,18526583488,CU3:1\",\"dc21257865515fb8,18653370708,DELIVRD\",\"69c504bfda26daed,13058983800,DELIVRD\",\"662f014e5aeb8150,13069802086,DELIVRD\",\"a40a251b8f3abc3f,13076775562,DELIVRD\",\"cc23c55c5ebd26f0,13066772058,DELIVRD\",\"1d788a45a81d0789,18676573123,DELIVRD\",\"7560a57ef56967d6,17501048078,DELIVRD\",\"1b12b000696c4122,15695569552,DELIVRD\",\"e11de870da9eff74,13093666722,DELIVRD\",\"3969709180e63376,13166996667,DELIVRD\",\"1356b178e28f8299,18645300122,DELIVRD\",\"5ef29d4b0b40bb8f,18699175470,DELIVRD\",\"f1d8f87a475e1d36,13044923187,DELIVRD\",\"ff7d39481379cc8f,15666777857,DELIVRD\",\"f4008936c9d4d20f,13039090282,CU3:54\",\"69b31d883e21bbfd,13100862348,CU3:1\",\"9db19cda8d04159a,18646010995,DELIVRD\",\"e9630f5ccd3a2420,18580566923,CU3:24\",\"f999ec1b558ad405,18522768793,DELIVRD\",\"e73c5594c06fee6a,13108919308,CU3:1\",\"ffcc78bd448e1123,18602027518,DELIVRD\",\"d31f7c207ef00ba8,18580226739,DELIVRD\",\"7ead027b281db8e5,13082901001,DELIVRD\",\"0c6eb83c32d92648,18633131391,DELIVRD\",\"c632dc147951184f,17554230614,DELIVRD\",\"afe17e99504cf7ee,13210036989,DELIVRD\",\"e63fdfca847a5238,13202037578,DELIVRD\",\"3567f41613b5b2f9,18518315631,DELIVRD\",\"d1e0d76d01a046ae,17520646511,DELIVRD\",\"075bcda6c8302d71,15577588141,DELIVRD\",\"915ff013cb056036,18515501935,DELIVRD\",\"7a3b44661d7b6462,18683656089,DELIVRD\",\"64c73aa415aadd7f,18670957133,DELIVRD\",\"a6480eb1297f0a32,18511794948,DELIVRD\",\"7cb393aa8ef705cb,18584378047,CU3:13\",\"304aa2e7a1e3ccbd,13281608375,DELIVRD\",\"51a6916375a0a503,13127749771,DELIVRD\",\"dde772ffffe5edff,13272098883,DELIVRD\",\"484e980bbba01feb,18678385556,DELIVRD\",\"41c0cf5619defa0d,15523093086,DELIVRD\",\"0f8fd808de385e5f,15626473959,DELIVRD\",\"d473aa16df3a1387,18659500985,DELIVRD\",\"25bab26df23c01b3,18607703390,DELIVRD\",\"2484877af0f4317b,15533058098,DELIVRD\",\"5149410fbe1a9880,13002394522,DELIVRD\",\"cbb58b9633fca195,18680187921,DELIVRD\",\"795c78c79912d186,18625971607,DELIVRD\",\"75777e3f53893406,17603093108,DELIVRD\",\"ffd5d06e39b72193,17602092221,DELIVRD\",\"50c192c86db467dd,13271519987,DELIVRD\",\"0f698bf31ccd045e,13268322199,DELIVRD\",\"5601fe347b2b57aa,13094302440,CU3:12\",\"705e2fa46796619d,18699567166,DELIVRD\",\"0237f16225e48a51,17620540111,CU3:182\"]";
		data = data.replace("mid=[", "").replace("]", "");
		String[] mid = StringUtils.commaDelimitedListToStringArray(data);
		// StringUtils.addStringToArray(mid, data);
		System.out.println(mid.length);
		for (int i = 0; i < mid.length; i++) {
			System.out.println(mid[i]);
		}

		/*
		 * String[] reports = data.substring(6, data.length() - 2).split("\",\"");
		 * System.out.println("长度:" + reports.length); for (int i = 0; i <
		 * reports.length; i++) { try { String[] reportOne = reports[i].split(",");
		 * String reqid = reportOne[0]; int count = 0; System.out.println(reportOne[0]
		 * +"\t"+ reportOne[1] + "\t" + reportOne[2]); if
		 * ("DELIVRD".equals(reportOne[2])) { System.out.println(reportOne[0] +"\t"+
		 * reportOne[1] + "\t成功"); } else { System.out.println(reportOne[0] +"\t"+
		 * reportOne[1] + "\t失败"); } } catch (Exception e) { continue; } }
		 */
	}
}