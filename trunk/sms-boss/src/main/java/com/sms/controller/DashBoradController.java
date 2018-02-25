package com.sms.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sms.criteria.dashboard.TransCountCritreia;
import com.sms.entity.dashboard.TransCount;
import com.sms.service.dashboard.DashBoradService;

@Controller
@RequestMapping("/dashBorad")
public class DashBoradController {

	@Autowired
	private DashBoradService dashBoradService;

	@ResponseBody
	@RequestMapping("/queryTransCount.ajax")
	public Map<String, Object> queryTransCount(TransCountCritreia criteria, HttpSession session) {

		List<TransCount> records = dashBoradService.queryTransCount(criteria);

		TransCount sc = dashBoradService.countLoadData(criteria);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", criteria.getTotalCount());
		result.put("rows", records);
		result.put("transCount", sc);
		return result;
	}

	@ResponseBody
	@RequestMapping("/expDataTransCount.ajax")
	public Map<String, Object> expDataTransCount(TransCountCritreia criteria, HttpSession session,
			HttpServletResponse response, HttpServletRequest request) throws IOException {
		Map<String, Object> result = new HashMap<String, Object>();
		HSSFWorkbook hssf = new HSSFWorkbook();
		try {
			List<TransCount> records = dashBoradService.queryTransCount(criteria);

			// 创建一个空的xls文件
			// 创建一个标签页
			HSSFSheet sheet = hssf.createSheet("分区数据");
			// 创建标题行
			HSSFRow titleRow = sheet.createRow(0);
			titleRow.createCell(0).setCellValue("时间");
			titleRow.createCell(1).setCellValue("商户编号");
			titleRow.createCell(2).setCellValue("商户简称");
			titleRow.createCell(3).setCellValue("商户账号");
			titleRow.createCell(4).setCellValue("发送数");
			titleRow.createCell(5).setCellValue("成功数");
			titleRow.createCell(6).setCellValue("失败数");
			titleRow.createCell(7).setCellValue("未知数");
			titleRow.createCell(8).setCellValue("成功率");
			// 遍历list，填充xls数据
			for (TransCount transCount : records) {
				HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
				dataRow.createCell(0).setCellValue(transCount.getStatisticalTime());
				dataRow.createCell(1).setCellValue(transCount.getMerchantId());
				dataRow.createCell(2).setCellValue(transCount.getMerchantNameAbbreviation());
				dataRow.createCell(3).setCellValue(transCount.getAccountNo());
				dataRow.createCell(4).setCellValue(transCount.getSendNum());
				dataRow.createCell(5).setCellValue(transCount.getSuccessNum());
				dataRow.createCell(6).setCellValue(transCount.getFailureNum());
				dataRow.createCell(7).setCellValue(transCount.getUnknownNum());
				dataRow.createCell(8).setCellValue(transCount.getMissionSuccessRateDes());
			}
			// 创建输出流【下载文件：一个流：输出流。两个头：一个头指定文件mime类型，一个头指定文件名称。】
			ServletOutputStream out = response.getOutputStream();
			// 设置文件mime类型
			response.setContentType("application/vnd.ms-excel");
			// 文件中文名
			String filename = "每日发送数量统计.xls";
			// 获取当前浏览器的类型和版本
			String agent = request.getHeader("User-Agent");
			filename = URLEncoder.encode(filename, "UTF-8");
			// 告诉客户端下载文件
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > -1) {
				response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + filename);
				System.out.println("firefox");
			} else {
				response.setHeader("content-disposition", "attachment; filename=" + filename);
			}
			hssf.write(out);
			result.put("success", true);
			result.put("message", "导出成功!");
			return result;
		} catch (IOException e) {
			result.put("success", false);
			result.put("message", "导出失败!");
			return result;
		} finally {
			hssf.close();
		}
	}

}