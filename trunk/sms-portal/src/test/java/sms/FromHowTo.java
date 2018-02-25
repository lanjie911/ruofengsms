package sms;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
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

public class FromHowTo {
	private StringBuffer mobilesStr = new StringBuffer();
	private StringBuffer mobilesStrDisplay = new StringBuffer();
	public void processFirstSheet(String filename) throws Exception {
		try (OPCPackage pkg = OPCPackage.open(filename, PackageAccess.READ)) {
			XSSFReader r = new XSSFReader(pkg);
			SharedStringsTable sst = r.getSharedStringsTable();

			XMLReader parser = fetchSheetParser(sst);

			// process the first sheet
			InputStream sheet2 = r.getSheetsData().next();
			InputSource sheetSource = new InputSource(sheet2);
			parser.parse(sheetSource);
			sheet2.close();
			
//			String[] mobileArray = mobilesStr.toString().split("#N#");
			/*System.out.println("短信总数：" + mobileArray.length);
			for(int index = 0; index<mobileArray.length; index++){
				System.out.println("序号：" + (index+1) +"\t内容："+ mobileArray[index]);
			}*/
			String[] mobileDisplayArray = mobilesStrDisplay.toString().split("#N#");
			String rowStr = mobileDisplayArray[0];
			String[] rowStrArray = rowStr.replaceAll("\n", "").split("\t");
			int strHreadLength = rowStrArray.length;
			StringBuffer strHeadContent = new StringBuffer();
			for(int i = 0; i<strHreadLength; i++) {
				strHeadContent.append("序列").append(i+1).append("\t");
			}
			strHeadContent.append("\r\n");
			System.out.println(strHeadContent.toString());
			System.out.println("短信总数：" + mobileDisplayArray.length);
			for(int index = 0; index < mobileDisplayArray.length; index++){
				System.out.println(mobileDisplayArray[index]);
			}
		}
	}
	
	public void processFirstSheet2(String filename) throws Exception {
		try (OPCPackage pkg = OPCPackage.open(filename, PackageAccess.READ)) {
			XSSFReader r = new XSSFReader(pkg);
			String jsonStr;
			StringBuilder result = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(r.getThemesData()));
			while ((jsonStr = reader.readLine()) != null)
	    		result.append(jsonStr);
	    	reader.close();// 关闭输入流
			System.out.println(result.toString());
			
			
			/*SharedStringsTable sst = r.getSharedStringsTable();
			
			XMLReader parser = fetchSheetParser(sst);
			InputStream sheet2 = r.getSheetsData().next();
			InputSource sheetSource = new InputSource(sheet2);
			parser.parse(sheetSource);
			sheet2.close();*/
		}
	}

	public void processAllSheets(String filename) throws Exception {
		try (OPCPackage pkg = OPCPackage.open(filename, PackageAccess.READ)) {
			XSSFReader r = new XSSFReader(pkg);
			SharedStringsTable sst = r.getSharedStringsTable();

			XMLReader parser = fetchSheetParser(sst);

			Iterator<InputStream> sheets = r.getSheetsData();
			while (sheets.hasNext()) {
				System.out.println("Processing new sheet:\n");
				InputStream sheet = sheets.next();
				InputSource sheetSource = new InputSource(sheet);
				parser.parse(sheetSource);
				sheet.close();
				System.out.println("");
			}
		}
	}

	public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
		XMLReader parser = XMLReaderFactory.createXMLReader();
		ContentHandler handler = new SheetHandler(sst);
		parser.setContentHandler(handler);
		return parser;
	}

	/**
	 * See org.xml.sax.helpers.DefaultHandler javadocs
	 */
	private class SheetHandler extends DefaultHandler {
		private final SharedStringsTable sst;
		private String lastContents;
		private boolean nextIsString;
		private boolean inlineStr;
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
				// Print the cell reference
//				System.out.print(attributes.getValue("r") + " - ");
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
//				System.out.println(lastContents);
				mobilesStr.append(lastContents).append("#T#");
				mobilesStrDisplay.append(lastContents).append("\t");
			} else if(name.equals("row")) {
				mobilesStr.append("#N#");
				mobilesStrDisplay.append("\n#N#");
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException { // NOSONAR
			lastContents += new String(ch, start, length);
		}
	}

	public static void main(String[] args) throws Exception {
		Long startTime = System.currentTimeMillis();
		//String filePath = "D:/tmp/large.xlsx";
//		String filePath = "/Users/cheney/Downloads/LargreExcel.xlsx";
		String filePath = "D:/tmp/ExcelTemplate.xlsx";
		FromHowTo howto = new FromHowTo();
		howto.processFirstSheet(filePath);
		Long endTime = System.currentTimeMillis();
		System.out.println("用时：" + (endTime-startTime)/1000 + "\t秒");
		//howto.processAllSheets(args[0]);
	}

}
