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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.csource.service.DownloadFile;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
public class ExcelUtil {
	/**
	 * Excel导入导出
	 * 
	 * @author bjs
	 * @version M
	 */

	    public void download(String path, HttpServletResponse response) throws IOException{
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
				toClient = new BufferedOutputStream(
						response.getOutputStream());
				response.setContentType("application/vnd.ms-excel;charset=gb2312");
				toClient.write(buffer);
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}finally {
				toClient.flush();
				toClient.close();
			}
			
	    }	
	    
	    /**
	     * 设置excel数据，基于反射会查出对象的所有字段，需要修改查询的sql
	     * @param response
	     * @param fp
	     * @param Title
	     * @param list
	     * @return
	     */
	   public File setExcelData(HttpServletResponse response,File fp,
			   String[] Title,List list){
		    response.setContentType("application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename=" + fp.getName());
			WritableWorkbook book = null;
	        try {
	            // 打开文件
	        	fp.createNewFile();
	            book = Workbook.createWorkbook(fp);
	            // 生成名为"学生"的工作表，参数0表示这是第一页
	            WritableSheet sheet = book.createSheet(fp.getName().substring(0,fp.getName().indexOf(".")), 0);
	            WritableFont wfcNav =new WritableFont(WritableFont.ARIAL,13, WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,jxl.format.Colour.BLACK);
	            WritableCellFormat wcfN=new WritableCellFormat(wfcNav);
	            wcfN.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN,jxl.format.Colour.BLACK); //BorderLineStyle边框
	            wcfN.setAlignment(Alignment.CENTRE); //设置水平对齐
	            wcfN.setWrap(false); //设置自动换行
	            for (int i = 0; i < Title.length; i++) {  
	            	sheet.setColumnView(i, 20);//根据内容自动设置列宽
	                sheet.addCell(new Label(i, 0,Title[i],wcfN));  
	               } 
	            WritableFont wfcNavnew =new WritableFont(WritableFont.ARIAL,11, WritableFont.NO_BOLD,false,UnderlineStyle.NO_UNDERLINE);
	            WritableCellFormat wcfNnew=new WritableCellFormat(wfcNavnew);
	            if(!list.isEmpty()){
	                for(int i=1; i<list.size(); i++){
	                	sheet.setColumnView(i, 20);//根据内容自动设置列宽
	                	Object ob = list.get(i);
	    				Class cl = ob.getClass();
	    				Field[] fi = cl.getDeclaredFields();
	    				for(int j = 0;j<fi.length;j++){
	    					fi[j].setAccessible(true);
	    					Label la = new Label(j, i, String.valueOf(fi[j].get(ob)),wcfNnew);
	    					sheet.addCell(la);
	    				}
	                	
	                }
	            }
	            // 写入数据并关闭文件
	            book.write();
	        } catch (Exception e) {
	            System.out.println(e);
	        }finally{
	            if(book!=null){
	                try {
	                    book.close();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                } 
	            }
	        }
	   
	   
	   return fp;
	}
	   
	   public static Map<String, Object> getFileFromFastDfs(String downUrl, String fileName,
			HttpServletResponse response,HttpServletRequest request ) throws IOException{
	
		Map<String, Object> result = new HashMap<>();
		String[] szTrackerServers = new String[1];
		szTrackerServers[0] = new String("192.16.1.252:22122"); //开发地址
		DownloadFile downloadFile = DownloadFile.getInstance(szTrackerServers);
		DownloadFile.DownloadResponse downloadResponse = downloadFile.new DownloadResponse();
		downloadResponse = downloadFile.doDownloadFile(downUrl);
		
		if(downloadResponse.getDownloadResult() == DownloadFile.SUCCESS){
			result.put("success", true);
		}else{
			result.put("success", false);
			System.out.println(downloadResponse.getDownloadText());
		}
	
		OutputStream toClient = null;
		try {
			
			response.reset();
	        fileName = URLEncoder.encode(fileName,"UTF8");//其他浏览器
			// 设置response的Header
			response.setHeader("content-disposition", "attachment;filename=" +fileName);
			toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/x-msdownload;charset=utf-8");
				toClient.write(downloadResponse.getFileData());
				   
			} catch (IOException ex) {
				ex.printStackTrace();
			}finally {
				toClient.flush();
				toClient.close();
			}
			return result;
			
	   }
		    
	   
}
