package com.sms.util; 

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 * Excel组件
 *
 * @author Snowolf
 * @version 1.0
 * @since 1.0
 */
public class ExcelHelper {

    /**
     * Excel 2003
     */
    private final static String XLS = ".xls";
    /**
     * Excel 2007
     */
    private final static String XLSX = ".xlsx";

    /**
     * 由Excel文件
     *
     * @param file
     * @param sheetNum
     * @return
     */
    public static Sheet getSheetFromFile(File file, int sheetNum){
        Workbook workbook = null;
        Sheet sheet = null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            if (file.getName().toLowerCase().endsWith(XLS)) {
                workbook = new HSSFWorkbook(is);
            } else if (file.getName().toLowerCase().endsWith(XLSX)) {
                workbook = new XSSFWorkbook(is);
            }
            sheet = workbook.getSheetAt(sheetNum);
        }catch (Exception e) {
        	if(is!=null){
                try {
                    is.close();
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        }
        return sheet;
    }

}
