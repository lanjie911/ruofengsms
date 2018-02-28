package com.sms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.sms.entity.smsupload.SmsApplayDetail;
import com.sms.service.smsupload.SmsApplayService;


public class NormalExcelUtil {
	
	private Logger logger =  LoggerFactory.getLogger(NormalExcelUtil.class);
	
	private StringBuffer textareaVal = new StringBuffer();
	private StringBuffer CMStr = new StringBuffer();
	private StringBuffer CUStr = new StringBuffer();
	private StringBuffer CTStr = new StringBuffer();
	public StringBuffer errorMobiles = new StringBuffer();
	private String textAreaVal;
	private int maxRows = 500000;
	private int CULoopVal = 1, CMLoopVal = 1, CTLoopVal = 1;
	public int loopCount, errorCount, succCount, repeatCount;
	public int total;
	private List<SmsApplayDetail> list = new ArrayList<SmsApplayDetail>();
	
	public List<SmsApplayDetail> getSmsApplayDetailList() {
		return list;
	}

	public String getTextAreaVal() {
		return textAreaVal;
	}
	
	public void readExcelNormal(InputStream in, boolean distinctFlag, Long batchNo, Long accountNo, Integer accountType) throws Exception {
		try (OPCPackage pkg = OPCPackage.open(in)) {
			XSSFReader r = new XSSFReader(pkg);
			SharedStringsTable sst = r.getSharedStringsTable();
			
			XMLReader parser = fetchSheetParserNormal(sst, distinctFlag);
			
			// process the first sheet
			InputStream sheet2 = r.getSheetsData().next();
			InputSource sheetSource = new InputSource(sheet2);
			parser.parse(sheetSource);
			sheet2.close();
			
			textAreaVal = textareaVal.toString();
			
			if(CUStr.toString().trim().length() > 1){
				String[] CUArray = CUStr.toString().trim().split("#N#");
				for(int k = 0; k<CUArray.length; k++) {
					SmsApplayDetail newSmsApplayDetail = new SmsApplayDetail();
					newSmsApplayDetail.setMobileOperator(200);
					newSmsApplayDetail.setBatchType(100);
					newSmsApplayDetail.setAccountNo(accountNo);
					newSmsApplayDetail.setAccountType(accountType);
					newSmsApplayDetail.setMobilesData(CUArray[k]);
					newSmsApplayDetail.setMobilesCount(CUArray[k].split(",").length);
					newSmsApplayDetail.setBatchNo(batchNo);
					list.add(newSmsApplayDetail);
				}
			}
			
			if(CTStr.toString().trim().length() > 1){
				String[] CTArray = CTStr.toString().trim().split("#N#");
				for(int q = 0; q<CTArray.length; q++) {
					SmsApplayDetail newSmsApplayDetail = new SmsApplayDetail();
					newSmsApplayDetail.setMobileOperator(100);
					newSmsApplayDetail.setBatchType(100);
					newSmsApplayDetail.setAccountNo(accountNo);
					newSmsApplayDetail.setAccountType(accountType);
					newSmsApplayDetail.setMobilesData(CTArray[q]);
					newSmsApplayDetail.setMobilesCount(CTArray[q].split(",").length);
					newSmsApplayDetail.setBatchNo(batchNo);
					list.add(newSmsApplayDetail);
				}
			}
			
			if(CMStr.toString().trim().length() > 1){
				String[] CMArray = CMStr.toString().trim().split("#N#");
				for(int j = 0; j<CMArray.length; j++) {
					SmsApplayDetail newSmsApplayDetail = new SmsApplayDetail();
					newSmsApplayDetail.setMobileOperator(300);
					newSmsApplayDetail.setBatchType(100);
					newSmsApplayDetail.setAccountNo(accountNo);
					newSmsApplayDetail.setAccountType(accountType);
					newSmsApplayDetail.setMobilesData(CMArray[j]);
					newSmsApplayDetail.setMobilesCount(CMArray[j].split(",").length);
					newSmsApplayDetail.setBatchNo(batchNo);
					list.add(newSmsApplayDetail);
				}
			}
		}
	}
	
	public XMLReader fetchSheetParserNormal(SharedStringsTable sstNormal, boolean distinctFlag) throws SAXException {
		XMLReader parser = XMLReaderFactory.createXMLReader();
		ContentHandler handler = new SheetHandlerNormal(sstNormal, distinctFlag);
		parser.setContentHandler(handler);
		return parser;
	}
	
	private class SheetHandlerNormal extends DefaultHandler {
		private final SharedStringsTable sstNormal;
		private String lastContents;
		private boolean nextIsString;
		private boolean inlineStr;
		private boolean distinctFlag;
		private final LruCache<Integer, String> lruCache = new LruCache<>(50);

		private class LruCache<A, B> extends LinkedHashMap<A, B> {
			private final int maxEntries;

			public LruCache(final int maxEntries) {
				super(maxEntries + 1, 1.0f, true);
				this.maxEntries = maxEntries;
			}

			@Override
			protected boolean removeEldestEntry(final Map.Entry<A, B> eldest) {
				return super.size() > maxEntries;
			}
		}

		private SheetHandlerNormal(SharedStringsTable sstNormal, boolean distinctFlag) {
			this.sstNormal = sstNormal;
			this.distinctFlag = distinctFlag;
		}

		@Override
		public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
			if (name.equals("c")) {
				String cellType = attributes.getValue("t");
				nextIsString = cellType != null && cellType.equals("s");
				inlineStr = cellType != null && cellType.equals("inlineStr");
			}else if (name.equals("dimension")){    
		        //获得总计录数    
		        String d = attributes.getValue("ref");    
		        total = getNumber(d.substring(d.indexOf(":")+1,d.length()));
		        if (total > maxRows)
					throw new RuntimeException("数据量过大，请控制50万条以内");
		    }    
			lastContents = "";
		}
		
		private int getNumber(String column) {
		    String c = column.toUpperCase().replaceAll("[A-Z]", "");    
		    return Integer.parseInt(c);    
		}  

		@Override
		public void endElement(String uri, String localName, String name) throws SAXException {
			if (nextIsString) {
				Integer idx = Integer.valueOf(lastContents);
				lastContents = lruCache.get(idx);
				if (lastContents == null && !lruCache.containsKey(idx)) {
					lastContents = new XSSFRichTextString(sstNormal.getEntryAt(idx)).toString();
					lruCache.put(idx, lastContents);
				}
				nextIsString = false;
			}

			if (name.equals("v") || (inlineStr && name.equals("c"))) {
				if(!isMobile(lastContents)) {
					errorMobiles.append(lastContents).append(",");
					errorCount++;
					return;
				}
				
				/*if(distinctFlag && (CMStr.indexOf(lastContents) != -1 || CUStr.indexOf(lastContents) != -1 || CTStr.indexOf(lastContents) != -1)) {
					repeatCount++;
					return;
				}*/
				String phoneShip = "";
				if(lastContents.startsWith("170")){
					phoneShip = PhoneShipUtil.query170OperatorMobile(lastContents);
				} else {
					phoneShip = PhoneShipUtil.mobileOperator.get(lastContents.substring(0, 3));
				}
				
				//TODO hai you wei sha zhe ge phoneShip cha bu chu lai
				//TODO zhe ge phoneShip shi ge mao ???????
				if(null == phoneShip || phoneShip.equals("")) {
					errorCount++;
					//TODO zhe ge difang gei wo jia ge ri zhi !!!!!!!!!
					logger.info("phoneShip:"+phoneShip);
					return;
				}
				
				if (loopCount < 1000)
					textareaVal.append(lastContents).append("\n");
				succCount++;
				
				if(CMLoopVal < 40 && phoneShip.equals("300")){
					CMStr.append(lastContents).append(",");
					CMLoopVal++;
					return;
				}else if(CMLoopVal >= 40  && phoneShip.equals("300")){
					CMStr.append(lastContents).append("#N#");
					CMLoopVal = 1;
					return;
				}
				
				if(CULoopVal < 40 && phoneShip.equals("200")){
					CUStr.append(lastContents).append(",");
					CULoopVal++;
					return;
				}else if(CULoopVal >= 40 && phoneShip.equals("200")){
					CUStr.append(lastContents).append("#N#");
					CULoopVal = 1;
					return;
				}
				
				if(CTLoopVal < 40 && phoneShip.equals("100")){
					CTStr.append(lastContents).append(",");
					CTLoopVal++;
					return;
				}else if(CTLoopVal >= 40 && phoneShip.equals("100")){
					CTStr.append(lastContents).append("#N#");
					CTLoopVal = 1;
					return;
				}
				
			} else if (name.equals("row")) {
				loopCount++;
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException { // NOSONAR
			lastContents += new String(ch, start, length);
		}
	}
	
	 /**
     * 校验手机号
     * 
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    private static boolean isMobile(String mobile) {
        return Pattern.matches(PhoneShipUtil.REGEX_MOBILE, mobile);
    }

	public static void main(String[] args) {
		/*try {
			Long startTime = System.currentTimeMillis();
			Long batchNo = 201920192019201l;
			NormalExcelUtil sc = new NormalExcelUtil();
//			String filePath = "D:/tmp/50-normal.xlsx";
			String filePath = "D:/tmp/large.xlsx";
//			String filePath = "D:/tmp/30个-normal.xlsx";
			File file = new File(filePath);
			InputStream input = new FileInputStream(file);
			sc.readExcelNormal(input, true, batchNo, 12310l, 200);
			
			List<SmsApplayDetail> list = sc.getSmsApplayDetailList();
			
			for(SmsApplayDetail newSmsApplayDetail : list) {
				System.out.println("所属运营商："+ newSmsApplayDetail.getMobileOperator() + "\t手机号计数：" + newSmsApplayDetail.getMobilesCount() + "\t手机号：" + newSmsApplayDetail.getMobilesData());
			}
			
			System.out.println("数据总数：" + sc.total +"\t系统计数："+ sc.loopCount);
			System.out.println("成功总数：" + sc.succCount);
			System.out.println("错误总数：" + sc.errorCount);
			System.out.println("重复总数：" + sc.repeatCount);
			System.out.println("List长度：" + list.size());
			Long endTime = System.currentTimeMillis();
			System.out.println("用时:" + (endTime-startTime)/1000 + "秒");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		System.out.println(isMobile("18311016679"));
	}

}