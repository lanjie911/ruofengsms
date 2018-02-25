package com.sms.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.csource.service.DownloadFile;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelUtil {
	private StringBuffer excelStr = new StringBuffer();
	private StringBuffer excelDisplay = new StringBuffer();
	private StringBuffer excelHidden = new StringBuffer();
	private String[] excelArray;
	private String textAreaVal;
	private String hiddenVal;
	private String headStr;
	private int maxRows = 500000;

	public String[] getExcelArray() {
		return excelArray;
	}

	public String getTextAreaVal() {
		return textAreaVal;
	}

	public String getHiddenVal() {
		return hiddenVal;
	}

	public String getHeadStr() {
		return headStr;
	}

	/**
	 * Excel导入导出
	 */
	public void download(String path, HttpServletResponse response) throws IOException {
		OutputStream toClient = null;
		try {
			// path是指欲下载的文件的路径。
			File file = new File(path);
			// 取得文件名。
			String filename = file.getName();
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
			response.addHeader("Content-Length", "" + file.length());
			toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/vnd.ms-excel;charset=gb2312");
			toClient.write(buffer);

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			toClient.flush();
			toClient.close();
		}
	}

	/**
	 * 设置excel数据，基于反射会查出对象的所有字段，需要修改查询的sql
	 * 
	 * @param response
	 * @param fp
	 * @param Title
	 * @param list
	 * @return
	 */
	public File setExcelData(HttpServletResponse response, File fp, String[] Title, List list) {
		response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=" + fp.getName());
		WritableWorkbook book = null;
		try {
			// 打开文件
			fp.createNewFile();
			book = Workbook.createWorkbook(fp);
			// 生成名为"学生"的工作表，参数0表示这是第一页
			WritableSheet sheet = book.createSheet(fp.getName().substring(0, fp.getName().indexOf(".")), 0);
			WritableFont wfcNav = new WritableFont(WritableFont.ARIAL, 13, WritableFont.BOLD, false,
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
			WritableCellFormat wcfN = new WritableCellFormat(wfcNav);
			wcfN.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK); // BorderLineStyle边框
			wcfN.setAlignment(Alignment.CENTRE); // 设置水平对齐
			wcfN.setWrap(false); // 设置自动换行
			for (int i = 0; i < Title.length; i++) {
				sheet.setColumnView(i, 20);// 根据内容自动设置列宽
				sheet.addCell(new Label(i, 0, Title[i], wcfN));
			}
			WritableFont wfcNavnew = new WritableFont(WritableFont.ARIAL, 11, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE);
			WritableCellFormat wcfNnew = new WritableCellFormat(wfcNavnew);
			if (!list.isEmpty()) {
				for (int i = 1; i < list.size(); i++) {
					sheet.setColumnView(i, 20);// 根据内容自动设置列宽
					Object ob = list.get(i);
					Class cl = ob.getClass();
					Field[] fi = cl.getDeclaredFields();
					for (int j = 0; j < fi.length; j++) {
						fi[j].setAccessible(true);
						Label la = new Label(j, i, String.valueOf(fi[j].get(ob)), wcfNnew);
						sheet.addCell(la);
					}
				}
			}
			// 写入数据并关闭文件
			book.write();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			if (book != null) {
				try {
					book.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return fp;
	}

	public static Map<String, Object> getFileFromFastDfs(String downUrl, String fileName, HttpServletResponse response,
			HttpServletRequest request) throws IOException {

		Map<String, Object> result = new HashMap<>();
		String[] szTrackerServers = new String[1];
		szTrackerServers[0] = new String("192.16.1.252:22122"); // 开发地址
		DownloadFile downloadFile = DownloadFile.getInstance(szTrackerServers);
		DownloadFile.DownloadResponse downloadResponse = downloadFile.new DownloadResponse();
		downloadResponse = downloadFile.doDownloadFile(downUrl);

		if (downloadResponse.getDownloadResult() == DownloadFile.SUCCESS) {
			result.put("success", true);
		} else {
			result.put("success", false);
			System.out.println(downloadResponse.getDownloadText());
		}

		OutputStream toClient = null;
		try {
			response.reset();
			fileName = URLEncoder.encode(fileName, "UTF8");// 其他浏览器
			// 设置response的Header
			response.setHeader("content-disposition", "attachment;filename=" + fileName);
			toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/x-msdownload;charset=utf-8");
			toClient.write(downloadResponse.getFileData());

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			toClient.flush();
			toClient.close();
		}
		return result;
	}

	/**
	 * 解析Excel文件
	 * 
	 * @param filename 文件磁盘绝对路径
	 * @param result  执行结果
	 * @throws Exception
	 */
	// public void processExcel(String filename) throws Exception {
	public void processExcel(InputStream in) throws Exception {
		// try (OPCPackage pkg = OPCPackage.open(filename, PackageAccess.READ))
		// {
		try (OPCPackage pkg = OPCPackage.open(in)) {
			XSSFReader r = new XSSFReader(pkg);
			SharedStringsTable sst = r.getSharedStringsTable();

			XMLReader parser = fetchSheetParser(sst);

			// process the first sheet
			InputStream sheet2 = r.getSheetsData().next();
			InputSource sheetSource = new InputSource(sheet2);
			parser.parse(sheetSource);
			sheet2.close();

			excelArray = excelStr.toString().split("#NN#");
			textAreaVal = excelDisplay.toString();
			hiddenVal = excelHidden.toString();
			String[] headArray = excelArray[0].split("#TT#");
			headStr = "";
			for (int i = 0; i < headArray.length; i++) {
				headStr += "序列" + (i + 1) + "\t\t";
			}
			headStr += "\n";
		}
	}
	
	public void processExcelNormal(InputStream in) throws Exception {
//		public void processExcelNormal(String filename) throws Exception {
//		 try (OPCPackage pkg = OPCPackage.open(filename, PackageAccess.READ)){
		try (OPCPackage pkg = OPCPackage.open(in)) {
			XSSFReader r = new XSSFReader(pkg);
			SharedStringsTable sst = r.getSharedStringsTable();
			
			XMLReader parser = fetchSheetParserNormal(sst);
			
			// process the first sheet
			InputStream sheet2 = r.getSheetsData().next();
			InputSource sheetSource = new InputSource(sheet2);
			parser.parse(sheetSource);
			sheet2.close();
			
			excelArray = excelStr.toString().split("#NN#");
			textAreaVal = excelDisplay.toString();
		}
	}

	public void processExcel2(String filename) throws Exception {
		try (OPCPackage pkg = OPCPackage.open(filename, PackageAccess.READ)) {
			XSSFReader r = new XSSFReader(pkg);
			SharedStringsTable sst = r.getSharedStringsTable();

			XMLReader parser = fetchSheetParser(sst);

			// process the first sheet
			InputStream sheet2 = r.getSheetsData().next();
			InputSource sheetSource = new InputSource(sheet2);
			parser.parse(sheetSource);
			sheet2.close();

			excelArray = excelStr.toString().split("#NN#");
			textAreaVal = excelDisplay.toString();
			hiddenVal = excelHidden.toString();
			String[] headArray = excelArray[0].split("#TT#");
			headStr = "";
			for (int i = 0; i < headArray.length; i++) {
				headStr += "序列" + (i + 1) + "\t\t";
			}
			headStr += "\n";
			
			System.out.println(excelArray[0]);
		}
	}

	public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
		XMLReader parser = XMLReaderFactory.createXMLReader();
		ContentHandler handler = new SheetHandler(sst);
		parser.setContentHandler(handler);
		return parser;
	}
	
	public XMLReader fetchSheetParserNormal(SharedStringsTable sstNormal) throws SAXException {
		XMLReader parser = XMLReaderFactory.createXMLReader();
		ContentHandler handler = new SheetHandlerNormal(sstNormal);
		parser.setContentHandler(handler);
		return parser;
	}

	private class SheetHandler extends DefaultHandler {
		private final SharedStringsTable sst;
		private String lastContents;
		private boolean nextIsString;
		private boolean inlineStr;
		private int loopIncrement;
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

		private SheetHandler(SharedStringsTable sst) {
			this.sst = sst;
		}

		@Override
		public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
			// c => cell
			if (name.equals("c")) {
				// Figure out if the value is an index in the SST
				String cellType = attributes.getValue("t");
				nextIsString = cellType != null && cellType.equals("s");
				inlineStr = cellType != null && cellType.equals("inlineStr");
			}
			// Clear contents cache
			lastContents = "";
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
				// System.out.println(lastContents);
				excelStr.append(lastContents).append("#TT#");
				if (loopIncrement < 1000) {
					excelDisplay.append(lastContents).append("\t\t");
					excelHidden.append(lastContents).append("#TT#");
				}
			} else if (name.equals("row")) {
				if (loopIncrement > maxRows)
					throw new RuntimeException("数据量过大，请控制50万条以内");
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
	
	
	private class SheetHandlerNormal extends DefaultHandler {
		private final SharedStringsTable sstNormal;
		private String lastContents;
		private boolean nextIsString;
		private boolean inlineStr;
		private int loopIncrement;
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

		private SheetHandlerNormal(SharedStringsTable sstNormal) {
			this.sstNormal = sstNormal;
		}

		@Override
		public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
			// c => cell
			if (name.equals("c")) {
				// Figure out if the value is an index in the SST
				String cellType = attributes.getValue("t");
				nextIsString = cellType != null && cellType.equals("s");
				inlineStr = cellType != null && cellType.equals("inlineStr");
			}
			// Clear contents cache
			lastContents = "";
		}

		@Override
		public void endElement(String uri, String localName, String name) throws SAXException {
			// Process the last contents as required.
			// Do now, as characters() may be called more than once
			if (nextIsString) {
				Integer idx = Integer.valueOf(lastContents);
				lastContents = lruCache.get(idx);
				if (lastContents == null && !lruCache.containsKey(idx)) {
					lastContents = new XSSFRichTextString(sstNormal.getEntryAt(idx)).toString();
					lruCache.put(idx, lastContents);
				}
				nextIsString = false;
			}

			// v => contents of a cell
			// Output after we've seen the string contents
			if (name.equals("v") || (inlineStr && name.equals("c"))) {
//				 System.out.println(lastContents);
				excelStr.append(lastContents).append("#NN#");
				if (loopIncrement < 1000)
					excelDisplay.append(lastContents).append("\n");
			} else if (name.equals("row")) {
				if (loopIncrement > maxRows)
					throw new RuntimeException("数据量过大，请控制50万条以内");
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
			ExcelUtil sc = new ExcelUtil();
			String filePath = "D:/tmp/50-normal.xlsx";
//			 sc.processExcelNormal(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}