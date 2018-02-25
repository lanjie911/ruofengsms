package com.godai.trade.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.godai.trade.common.util.StringUtil;
import com.godai.trade.common.util.TimeUtil;
import com.godai.trade.entity.smsupload.SmsApplayDetail;

public class PackageRequestUtil {
	private static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// 拼接请求报文
	public static String packageRequest(Map<String, Object> requestparam) {

		Map<String, Object> request = new HashMap<String, Object>();
		Map<String, Object> reqHeader = new HashMap<String, Object>();
		reqHeader.put("jrn_no", TimeUtil.getCurrentFullTime3() + "100" + StringUtil.genRandomNum(4));
		reqHeader.put("req_time", TimeUtil.getCurrentFullTime2());
		reqHeader.put("req_cnl", "300");
		request.put("req_header", reqHeader);
		request.put("req_content", requestparam);
		return JSON.toJSONString(request);
	}
	
	public static String packageNormalSmsJsonStr(Long accountNo, Integer mobileOperator, Long batchNo, 
			String smsContent, String signTip, List<SmsApplayDetail> list, int orderFlag, Timestamp appointmentTime,
			int accountType){
		Map<String, Object> request = new HashMap<String, Object>();
		request.put("account_no", String.valueOf(accountNo));
		request.put("mobile_operator", String.valueOf(mobileOperator));
		request.put("batch_no", String.valueOf(batchNo));
		request.put("sms_content", smsContent);
		request.put("sign_tip", signTip);
		request.put("order_flag", orderFlag);
		request.put("account_type", accountType);
		String appointment_time = "";
		if(null != appointmentTime)
			appointment_time = sdf.format(appointmentTime);
		request.put("appointment_time", appointment_time);
		StringBuffer mobileData = new StringBuffer();
		for(SmsApplayDetail sc : list) {
			String tmpStr = mobileData.toString();
			if(null == tmpStr || tmpStr.equals("")){
				mobileData.append(sc.getMobilesData());
				continue;
			}
			if(tmpStr.substring(tmpStr.length()-1, tmpStr.length()).equals(",")){
				mobileData.append(sc.getMobilesData());
			}else{
				mobileData.append(",").append(sc.getMobilesData());
			}
		}
		request.put("mobile_data", String.valueOf(mobileData));
		int smsCount = smsContentCount(smsContent+signTip);
		request.put("charge_count", String.valueOf(smsCount));
		
		return JSON.toJSONString(request);
	}
	
	private static int smsContentCount(String smsContent){
		int sendNum = 1;
		if(smsContent.length()>70)
			sendNum = (int) (Math.ceil(smsContent.length()/68d));
		return sendNum;
	}
	
	public static void main(String[] args) {
		String mobile1 = "13352911809,18925806954,15377720803,18933455658,13367540072,17787094626,18179221156,18149843555,13311651518,18952745776,18172911207,17712582806,18983792386,13355814061,18982399149,18177658554,13333092933,18067437307,15364092169,13345071996,17714536435,13396460728,17711708115,17710803820,18193540337,17312233843,18989065017,18082611521,18101115190,18088182269,18177168726,15345095946,18080305564,15359879111,17708589768,17758753017,15307528660,17778111351,15393204448,18152304080";
		String mobile2 = "13328064367,13302222307,17762438096,18981937076,18938888094,18055053316,18001603178,13399909533,18989716699,13335642371,15379597993,18138301390,18970737555,13379430478,18908819846,18025168317,15309963131,15343338718,17795240923,18929365395,17330149111,18171222697,15313088937,18155425209,17311225867,15342280145,17791406793,17373061615,18181132211,15379618011,18902299987,17313103099,18965853992,18904729914,18931737775,18971497532,17762920269,15393499760,17784383060,15360659872";
		String mobile3 = "17709152232,17781890167,18080563190,13314960213,15328779659,18081063910,18124168329,15303217343,18076307714,13343922211,18975842236,18981707216,18140409100,17723081582,15399156022,13343062282,17731409810,13373329991,13377202563,18997829000,18117769540,13306141500,13388579566,18184013633,13378056866,13353680863,18028914150,13336680500,17376005974,15343057289,18951014424,15372639702,13332360022,17347661371,17780528271,13304966779,17788583676,15370602721,13317373689,13394453699";
		String mobile4 = "18949865467,15362158520,18964107336,17369730809,15307883033,13317838184,18062888181,18972010988,15364818531,18003530773,13368526791,15396566166,18088208854,18113888546,13324839272,13303756319,18971422854,18152878137,17797684869,18115491173,15335574069,18907777327,13381799989,13373336937,18176866017,17398228226,17731103517,18971116082,18165540285,18035106665,13314918303,13351898332,13353570127,17776180271,18950597959,17304865997,15388287118,18073118856,18074918587,17793001927";
		String mobile5 = "18029958668,13335673568,15349999591,17789754596,18078287771,15383937127,13332921916,15375511798,17712917704,13318688829,18910033352,18096218571,15384006635,18166353589,13359561226,18172563302,18171701525,18970522507,18164651541,17705097001,18034032833,13388334571,17730821199,18074923132,17726138366,13366967953,17730406777,18053950760,13321460363,15328680012,17789073332,18972299119,17752565530,18028018340,17768294699,18983681789,18907221646,17737403310,15363186819,18170199111";
		String mobile6 = "18990301644,13398219075,17768732105,15305662527,17723553442,18975619468,15344011943,18113550778,18965096992,13349824776,18187063146,15323297868,18195400515,15327168208,18150185768,13377661010,18931896021,18066511812,17793302982,18939500803,17733606154,18116893313,17750782295,18017075720,15344144777,17394085110,15369964265,17766992020,18087597281,18165271077,18923378267,18059000611,18113711576,18076666883,18121869913,18172242779,18037178203,18072056369,18914672699,15371646766";
		String mobile7 = "17330364444,18026691774,17776176776,18032107023,18988163266,18028632665,18057130066,15329190173,18065501671,17373845396,18102282828,17738285051,17726202478,13361793209,15377387989,18107175440,17709806752,17754791443,17337836366,18173375368,17709661343,18076909717,17746678882,18188303723,18096223688,18143254311,18093641185,17332622207,18006051140,13375963901,18922177969,13397377332,17300294230,15342607012,17715030588,18919406851,17720773975,13363162700,17723437972,18979535949";
		String mobile8 = "18176201424,17782253200,18950003906,18152446338,17731097895,17733012398,18078798994,17774729169,17775995944,15347876186,18135948899,17361054628,13377142688,18058095799,15367225972,17398088859,18177053339,18961243376,18100448063,18902328308,13309426009,17719273633,18978607060,18007384107,15325513338,13393893809,18195796656,18977482801,18908102781,18197066888,18992521897,13398498868,13398833718,17310305515,18089652735,18928468234,17373711238,15355797321,15352666384,18159169137";
		
		List<SmsApplayDetail> list = new ArrayList<SmsApplayDetail>();
		SmsApplayDetail sc = new SmsApplayDetail();
		sc.setMobilesData(mobile1);
		list.add(sc);
		
		sc = new SmsApplayDetail();
		sc.setMobilesData(mobile2);
		list.add(sc);
		
		sc = new SmsApplayDetail();
		sc.setMobilesData(mobile3);
		list.add(sc);
		
		sc = new SmsApplayDetail();
		sc.setMobilesData(mobile4);
		list.add(sc);
		
		sc = new SmsApplayDetail();
		sc.setMobilesData(mobile5);
		list.add(sc);
		
		sc = new SmsApplayDetail();
		sc.setMobilesData(mobile6);
		list.add(sc);
		
		sc = new SmsApplayDetail();
		sc.setMobilesData(mobile7);
		list.add(sc);
		
		sc = new SmsApplayDetail();
		sc.setMobilesData(mobile8);
		list.add(sc);
		
		StringBuffer mobileData = new StringBuffer();
		for(SmsApplayDetail sc2 : list) {
			String tmpStr = mobileData.toString();
			if(null == tmpStr || tmpStr.equals("")){
				mobileData.append(sc2.getMobilesData());
				continue;
			}
			if(tmpStr.substring(tmpStr.length()-1, tmpStr.length()).equals(",")){
				mobileData.append(sc2.getMobilesData());
			}else{
				mobileData.append(",").append(sc2.getMobilesData());
			}
		}
		
		System.out.println(mobileData.toString());
	}
}