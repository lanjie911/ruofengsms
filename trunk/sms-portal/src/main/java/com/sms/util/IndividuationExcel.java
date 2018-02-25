package com.sms.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.sms.entity.smsupload.SmsDetailUpload;

public class IndividuationExcel {
	private StringBuffer excelStr = new StringBuffer();
	private StringBuffer excelDisplay = new StringBuffer();
	private StringBuffer excelHidden = new StringBuffer();
	private int maxRows = 500000;

	public String textAreaVal;
	public  String headStr;
	public String hiddenVal;
	public int errorCount, succCount, repeatCount;
	public int total;
	public List<SmsDetailUpload> smsDetailUploadList;
	
	public void processExcel(InputStream in, boolean distinctFlag, Long batchNo, Long accountNo, Integer accountType) throws Exception {
		try (OPCPackage pkg = OPCPackage.open(in)) {
			XSSFReader r = new XSSFReader(pkg);
			SharedStringsTable sst = r.getSharedStringsTable();

			XMLReader parser = fetchSheetParser(sst, distinctFlag);
			InputStream sheet2 = r.getSheetsData().next();
			InputSource sheetSource = new InputSource(sheet2);
			parser.parse(sheetSource);
			sheet2.close();

			String[] excelArray = excelStr.toString().split("#NN#");
			textAreaVal = excelDisplay.toString();
			hiddenVal = excelHidden.toString();
			String[] headArray = excelArray[0].split("#TT#");
			headStr = "";
			for (int i = 0; i < headArray.length; i++) {
				headStr += "序列" + (i + 1) + "\t\t";
			}
			headStr += "\n";
			
			List<String> tmpList = new ArrayList<String>();
			if(distinctFlag){
				for(int k = 0; k < excelArray.length; k++) {
					if(!tmpList.contains(excelArray[k])) {    
						tmpList.add(excelArray[k]);
						succCount++;
					}else{
						repeatCount++;
					}
				}
			}else{
				for(int k = 0; k < excelArray.length; k++) {
					tmpList.add(excelArray[k]);    
				}
				succCount = excelArray.length;
			}
			
			SmsDetailUpload tempSmsDetailUpload;
			smsDetailUploadList = new ArrayList<SmsDetailUpload>();
			for(String tmpStr : tmpList){
				tempSmsDetailUpload = new SmsDetailUpload();
				tempSmsDetailUpload.setBatchNo(batchNo);
				tempSmsDetailUpload.setAccountNo(accountNo);
				tempSmsDetailUpload.setAccountType(accountType);
				tempSmsDetailUpload.setUploadContent(tmpStr);
				smsDetailUploadList.add(tempSmsDetailUpload);
			}
		}
	}

	public XMLReader fetchSheetParser(SharedStringsTable sst, boolean distinctFlag) throws SAXException {
		XMLReader parser = XMLReaderFactory.createXMLReader();
		ContentHandler handler = new SheetHandler(sst, distinctFlag);
		parser.setContentHandler(handler);
		return parser;
	}

	private class SheetHandler extends DefaultHandler {
		private final SharedStringsTable sst;
		private String lastContents;
		private boolean nextIsString;
		private boolean inlineStr;
		private int loopIncrement;
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

		private SheetHandler(SharedStringsTable sst, boolean distinctFlag) {
			this.sst = sst;
			this.distinctFlag = distinctFlag;
		}

		@Override
		public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
			// c => cell
			if (name.equals("c")) {
				// Figure out if the value is an index in the SST
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
			// Clear contents cache
			lastContents = "";
		}
		
		private int getNumber(String column) {
		    String c = column.toUpperCase().replaceAll("[A-Z]", "");    
		    return Integer.parseInt(c);    
		}  

		@Override
		public void endElement(String uri, String localName, String name) throws SAXException {
			// Process the last contents as required.
			// Do now, as characters() may be called more than once
			if (nextIsString) {
				Integer idx = Integer.valueOf(lastContents);
				lastContents = lruCache.get(idx);
				if (lastContents == null && !lruCache.containsKey(idx)) {
					lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
					lruCache.put(idx, lastContents);
				}
				nextIsString = false;
			}

			// v => contents of a cell
			// Output after we've seen the string contents
			if (name.equals("v") || (inlineStr && name.equals("c"))) {
				excelStr.append(lastContents).append("#TT#");
				if (loopIncrement < 1000) {
					excelDisplay.append(lastContents).append("\t\t");
					excelHidden.append(lastContents).append("#TT#");
				}
			} else if (name.equals("row")) {
				excelStr.append("#NN#");
				if (loopIncrement < 1000) {
					excelDisplay.append("\n");
					excelHidden.append("#NN#");
				}
				loopIncrement++;
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException { // NOSONAR
			lastContents += new String(ch, start, length);
		}
	}

	public static void main(String[] args) {
		try {
			Long startTime = System.currentTimeMillis();
			IndividuationExcel sc = new IndividuationExcel();
			String filePath = "D:/tmp/50.xlsx";
			File file = new File(filePath);
			InputStream input = new FileInputStream(file);
			Long batchNo = 201920192019201l;
			sc.processExcel(input, true, batchNo, 12310l, 200);
			/*System.out.println("总数：" + sc.total);
			System.out.println("成功：" + sc.succCount);
			System.out.println("重复：" + sc.repeatCount);
			System.out.println("List长度：" + sc.smsDetailUploadList.size());
			System.out.println("Header显示：" + sc.headStr);*/
			System.out.println(sc.hiddenVal);
//			System.out.println("TextArea显示： " + sc.textAreaVal);
			/*for(SmsDetailUpload kk : sc.smsDetailUploadList){
				System.out.println(kk.toString());
			}*/
			
			Long endTime = System.currentTimeMillis();
			System.out.println("用时:" + (endTime-startTime)/1000 + "秒");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}