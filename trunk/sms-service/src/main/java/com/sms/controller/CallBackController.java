package com.sms.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sms.dao.primarydao.PlainSendRecordDao;
import com.sms.entity.MercAccount;
import com.sms.entity.PlainSendRecord;
import com.sms.service.PrepareParamService;
import com.sms.service.SendSmsService;
import com.sms.service.send.MercAccountService;
import com.sms.service.send.ValidatePramService;
import com.sms.util.DatetimeUtil;
import com.sms.util.TradeException;

@Controller
@RequestMapping("/callback")
public class CallBackController {
	
	private static Logger logger = LoggerFactory.getLogger(CallBackController.class);
	
	@Autowired
	private ValidatePramService validatePramService;
	
	@Autowired
	private SendSmsService sendSmsService;
	
	@Autowired
	private PrepareParamService prepareParamService;
	
	@Autowired
	private PlainSendRecordDao plainSendRecordDao;
	
	@Autowired
	private MercAccountService mercAccountService;
	
	/**
	 * 接收请求
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/receive")
	public Callable<String> receive(HttpServletRequest request, HttpServletResponse response){
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				
				BufferedReader reader = null;
				String jsonStr = null;
				StringBuilder result = new StringBuilder();
                String resultStr = "{\"msg\":\"SUCCESS\",\"code\":\"0000\"}";
				try {
					String reqIp = getIpAddr(request);
					logger.info("sendMsg synchro start request :{}", reqIp);	//获取请求IP
					if(null != prepareParamService.getConvinceIp(reqIp))
						throw new TradeException("9988","{\"msg\":\"非法IP\",\"code\":\"9988\"}");
					reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
	    	    	while ((jsonStr = reader.readLine()) != null)
	    	    		result.append(jsonStr);
	    	    	reader.close();// 关闭输入流
	    	    	
		        	JSONObject jsonObject = JSONObject.parseObject(result.toString()); // 取一个json转换为对象
		        	validatePramService.validateSignReceive(jsonObject,reqIp);
		        	sendSmsService.tdToUnsubscribe(jsonObject);
		        	
		        	response.setCharacterEncoding("UTF-8");
            		response.setContentType("application/json; charset=utf-8");
            		logger.info("接口处理成功："+resultStr);
                } catch (TradeException e) {
                	resultStr = e.getErrorMsg();
                	logger.error("接口处理失败："+e.getErrorMsg());
                } catch (Exception e) {
                	resultStr = "{\"msg\":\"系统内部错误\",\"code\":\"9999\"}";
                	logger.error("接口处理异常："+e.getMessage());
                } finally{
                	if(reader != null)
                		reader.close();
                }
				return resultStr;
			}
		};
	}
	
	/**
	 * 接收美联推送
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/meilianReport")
	public Callable<String> meilianReport(HttpServletRequest request, HttpServletResponse response){
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				
				BufferedReader reader = null;
				String jsonStr = null;
				StringBuilder result = new StringBuilder();
                String resultStr = "success";
				try {
					String reqIp = getIpAddr(request);
					logger.info("sendMsg synchro start request :{}", reqIp);	//获取请求IP
					if(null != prepareParamService.getConvinceIp(reqIp))
						throw new TradeException("9988","{\"msg\":\"非法IP\",\"code\":\"9988\"}");
					reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
	    	    	while ((jsonStr = reader.readLine()) != null)
	    	    		result.append(jsonStr);
	    	    	reader.close();// 关闭输入流
	    	    	String text = URLDecoder.decode(result.toString(), "utf-8");
	    	    	logger.info("美联回调内容："+text);
	    	    	String[] reports = text.substring(5).split(";");
	    	    	
		        	for(int i=0;i<reports.length;i++){
		        		try {
		        			String[] reportOne = reports[i].split(",");
		        			String reqid = reportOne[0];
			        		PlainSendRecord plainSendRecord = plainSendRecordDao.getByreqMsgId(reqid);
			        		if(null == plainSendRecord)
			        			continue;
			        		MercAccount mercAccount = prepareParamService.getMercAccount(plainSendRecord.getAccountNo());
			        		if(null == mercAccount)
			        			continue;
			        		int count = 0;
			        		if("DELIVRD".equals(reportOne[2])){
			        			count = plainSendRecordDao.updateStatusByReport(reportOne[0], "500", "发送成功", DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
			        			if(1 == count)
			        				mercAccountService.unFrozenBalance(plainSendRecord.getAccountNo(), 1, reportOne[0]);
			        		}else{
			        			count = plainSendRecordDao.updateStatusByReport(reportOne[0], "400", reportOne[2], DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
			        			if(1 == count && 100 == mercAccount.getChargingMethods()){
			        				mercAccountService.unFrozenBalance(plainSendRecord.getAccountNo(), 1, reportOne[0]);
			        			}else if(1 == count && 200 == mercAccount.getChargingMethods()){
			        				mercAccountService.doCorect(plainSendRecord.getAccountNo(), 1, reportOne[0]);
			        			}
			        		}
						} catch (Exception e) {
							continue;
						}
		        	}
		        	response.setCharacterEncoding("UTF-8");
            		response.setContentType("application/json; charset=utf-8");
            		logger.info("接口处理成功："+resultStr);
                } catch (TradeException e) {
                	resultStr = "error"+e.getErrorMsg();
                	logger.error("接口处理失败："+e.getErrorMsg());
                } catch (Exception e) {
                	resultStr = "{\"msg\":\"系统内部错误\",\"code\":\"9999\"}";
                	logger.error("接口处理异常："+e.getMessage());
                } finally{
                	if(reader != null)
                		reader.close();
                }
				return resultStr;
			}
		};
	}
	
	/**
	 * 接收推送
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/juxinReport")
	public Callable<String> juxinReport(HttpServletRequest request, HttpServletResponse response){
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				
                String resultStr = "{\"status\":1}";
				try {
					String reqIp = getIpAddr(request);
					logger.info("sendMsg synchro start request :{}", reqIp);	//获取请求IP
					BufferedReader reader = null;
					String jsonStr = null;
					StringBuilder result = new StringBuilder();
					reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
					while ((jsonStr = reader.readLine()) != null)
			    		result.append(jsonStr);
			    	reader.close();// 关闭输入流
					
					if(null != prepareParamService.getConvinceIp(reqIp))
						throw new TradeException("9988","{\"status\":\"0\"}");
	    	    	
					String data = URLDecoder.decode(result.toString(), "UTF-8");
			    	logger.info("聚信发送结果回调："+data);
	    	    	
	    	    	String[] reports = data.substring(6, data.length()-1).split("','");
		        	for(int i=0;i<reports.length;i++){
		        		try {
		        			String[] reportOne = reports[i].split(",");
		        			String reqid = reportOne[0];
			        		PlainSendRecord plainSendRecord = plainSendRecordDao.getByreqMsgId(reqid);
			        		if(null == plainSendRecord)
			        			continue;
			        		MercAccount mercAccount = prepareParamService.getMercAccount(plainSendRecord.getAccountNo());
			        		if(null == mercAccount)
			        			continue;
			        		int count = 0;
			        		if("DELIVRD".equals(reportOne[2])){
			        			count = plainSendRecordDao.updateStatusByReport(reportOne[0], "500", "发送成功", DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
			        			if(1 == count)
			        				mercAccountService.unFrozenBalance(plainSendRecord.getAccountNo(), 1, reportOne[0]);
			        		}else{
			        			count = plainSendRecordDao.updateStatusByReport(reportOne[0], "400", reportOne[2], DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
			        			if(1 == count && 100 == mercAccount.getChargingMethods()){
			        				mercAccountService.unFrozenBalance(plainSendRecord.getAccountNo(), 1, reportOne[0]);
			        			}else if(1 == count && 200 == mercAccount.getChargingMethods()){
			        				mercAccountService.doCorect(plainSendRecord.getAccountNo(), 1, reportOne[0]);
			        			}
			        		}
						} catch (Exception e) {
							continue;
						}
		        	}
		        	response.setCharacterEncoding("UTF-8");
            		response.setContentType("application/json; charset=utf-8");
            		logger.info("接口处理成功："+resultStr);
                } catch (TradeException e) {
                	resultStr = "{\"status\":0}";
                	logger.error("接口处理失败："+e.getErrorMsg());
                } catch (Exception e) {
                	e.printStackTrace();
                	resultStr = "{\"status\":0}";
                	logger.error("接口处理异常："+e.getMessage());
                }
				return resultStr;
			}
		};
	}
	
	/**
	 * 接收烽火万家状态推送
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/fenghuoReport")
	public Callable<String> fenghuoReport(HttpServletRequest request, HttpServletResponse response){
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				String reqIp = getIpAddr(request);
				logger.info("sendMsg synchro start request :{}", reqIp);	//获取请求IP
				BufferedReader reader = null;
				String jsonStr = null;
				StringBuilder result = new StringBuilder();
                String resultStr = "success";
				try {
					if(null != prepareParamService.getConvinceIp(reqIp))
						throw new TradeException("9988","{\"msg\":\"非法IP\",\"code\":\"9988\"}");
					reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
	    	    	while ((jsonStr = reader.readLine()) != null)
	    	    		result.append(jsonStr);
	    	    	reader.close();// 关闭输入流
	    	    	
	    	    	String name = request.getParameter("name");
	    	    	String report = request.getParameter("report");
	    	    	logger.info("烽火短信发送结果回执："+name+"-"+report);
	    	    	String[] reports = report.split(";");
		        	for(int i=0;i<reports.length;i++){
		        		try {
		        			String[] reportOne = reports[i].split(",");
		        			String reqid = reportOne[0];
			        		PlainSendRecord plainSendRecord = plainSendRecordDao.getByreqMsgId(reqid);
			        		if(null == plainSendRecord)
			        			continue;
			        		MercAccount mercAccount = prepareParamService.getMercAccount(plainSendRecord.getAccountNo());
			        		if(null == mercAccount)
			        			continue;
			        		int count = 0;
			        		if("DELIVRD".equals(reportOne[2])){
			        			count = plainSendRecordDao.updateStatusByReport(reportOne[0], "500", "发送成功", DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
			        			if(1 == count)
			        				mercAccountService.unFrozenBalance(plainSendRecord.getAccountNo(), 1, reportOne[0]);
			        		}else{
			        			count = plainSendRecordDao.updateStatusByReport(reportOne[0], "400", reportOne[2], DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
			        			if(1 == count && 100 == mercAccount.getChargingMethods()){
			        				mercAccountService.unFrozenBalance(plainSendRecord.getAccountNo(), 1, reportOne[0]);
			        			}else if(1 == count && 200 == mercAccount.getChargingMethods()){
			        				mercAccountService.doCorect(plainSendRecord.getAccountNo(), 1, reportOne[0]);
			        			}
			        		}
						} catch (Exception e) {
							continue;
						}
		        	}
		        	response.setCharacterEncoding("UTF-8");
            		response.setContentType("application/json; charset=utf-8");
            		logger.info("接口处理成功："+resultStr);
                } catch (TradeException e) {
                	resultStr = "error"+e.getErrorMsg();
                	logger.error("接口处理失败："+e.getErrorMsg());
                } catch (Exception e) {
                	resultStr = "{\"msg\":\"系统内部错误\",\"code\":\"9999\"}";
                	logger.error("接口处理异常："+e.getMessage());
                } finally{
                	if(reader != null)
                		reader.close();
                }
				return resultStr;
			}
		};
	}
	
	/**
	 * 接收乐信推送
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/lexinReport")
	public Callable<String> lexinReport(HttpServletRequest request, HttpServletResponse response){
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				String reqIp = getIpAddr(request);
				logger.info("sendMsg synchro start request :{}", reqIp);	//获取请求IP
				BufferedReader reader = null;
				String jsonStr = null;
				StringBuilder result = new StringBuilder();
                String resultStr = "success";
				try {
					if(null != prepareParamService.getConvinceIp(reqIp))
						throw new TradeException("9988","{\"msg\":\"非法IP\",\"code\":\"9988\"}");
					reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
	    	    	while ((jsonStr = reader.readLine()) != null)
	    	    		result.append(jsonStr);
	    	    	reader.close();// 关闭输入流
	    	    	
	    	    	String msgID = request.getParameter("MsgID");
	    	    	String sendState = request.getParameter("SendState");
	    	    	String sendResultInfo = request.getParameter("SendResultInfo");
	    	    	PlainSendRecord plainSendRecord = plainSendRecordDao.getById(Long.valueOf(msgID));
	        		MercAccount mercAccount = prepareParamService.getMercAccount(plainSendRecord.getAccountNo());
	    	    	logger.info("乐信短信发送结果回执："+msgID+"-"+sendState+"-"+sendResultInfo);
	        		int count = 0;
	    	    	if("DELIVRD".equals(sendResultInfo)){
	        			count = plainSendRecordDao.updateStatusByReport(msgID, "500", "发送成功", DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
	        			if(1 == count)
	        				mercAccountService.unFrozenBalance(plainSendRecord.getAccountNo(), 1, msgID);
	    	    	}else{
	        			count = plainSendRecordDao.updateStatusByReport(msgID, "400", sendResultInfo, DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
	        			if(1 == count && 100 == mercAccount.getChargingMethods()){
	        				mercAccountService.unFrozenBalance(plainSendRecord.getAccountNo(), 1, msgID);
	        			}else if(1 == count && 200 == mercAccount.getChargingMethods()){
	        				mercAccountService.doCorect(plainSendRecord.getAccountNo(), 1, msgID);
	        			}
	    	    	}
		        	response.setCharacterEncoding("UTF-8");
            		response.setContentType("application/json; charset=utf-8");
            		logger.info("接口处理成功："+resultStr);
                } catch (TradeException e) {
                	resultStr = "error"+e.getErrorMsg();
                	logger.error("接口处理失败："+e.getErrorMsg());
                } catch (Exception e) {
                	resultStr = "{\"msg\":\"系统内部错误\",\"code\":\"9999\"}";
                	logger.error("接口处理异常："+e.getMessage());
                } finally{
                	if(reader != null)
                		reader.close();
                }
				return resultStr;
			}
		};
	}
	
	/**
	 * 接收创锐推送
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/chuangruiReport")
	public Callable<String> chuangruiReport(HttpServletRequest request, HttpServletResponse response){
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				String reqIp = getIpAddr(request);
				logger.info("sendMsg synchro start request :{}", reqIp);	//获取请求IP
				BufferedReader reader = null;
				String jsonStr = null;
				StringBuilder result = new StringBuilder();
                String resultStr = "success";
				try {
					if(null != prepareParamService.getConvinceIp(reqIp))
						throw new TradeException("9988","{\"msg\":\"非法IP\",\"code\":\"9988\"}");
					reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
	    	    	while ((jsonStr = reader.readLine()) != null)
	    	    		result.append(jsonStr);
	    	    	reader.close();// 关闭输入流
	    	    	
	    	    	String name = request.getParameter("name");
	    	    	String sendid = request.getParameter("sendid");
	    	    	String time = request.getParameter("time");
	    	    	String state = request.getParameter("state");
	    	    	logger.info("创锐短信发送结果回执："+name+"-"+sendid+"-"+state+"-"+time);
	        		if("DELIVRD".equals(state)){
	        			plainSendRecordDao.updateStatusByReport(sendid, "500", "发送成功", DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
	        		}else{
	        			plainSendRecordDao.updateStatusByReport(sendid, "400", state, DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
	        		}
		        	response.setCharacterEncoding("UTF-8");
            		response.setContentType("application/json; charset=utf-8");
            		logger.info("接口处理成功："+resultStr);
                } catch (TradeException e) {
                	resultStr = "error"+e.getErrorMsg();
                	logger.error("接口处理失败："+e.getErrorMsg());
                } catch (Exception e) {
                	resultStr = "{\"msg\":\"系统内部错误\",\"code\":\"9999\"}";
                	logger.error("接口处理异常："+e.getMessage());
                } finally{
                	if(reader != null)
                		reader.close();
                }
				return resultStr;
			}
		};
	}
	
	/**
	 * 创锐3.0推送
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/chuangRuiReportV3")
	public Callable<String> chuangRuiReportV3(HttpServletRequest request, HttpServletResponse response){
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				
				BufferedReader reader = null;
				String jsonStr = null;
				StringBuilder result = new StringBuilder();
				String resultStr = "success";
				try {
					String reqIp = getIpAddr(request);
					logger.info("CallBackController.chuangRuiReportV3-Request IP：{}", reqIp);	//获取请求IP
					reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
					while ((jsonStr = reader.readLine()) != null)
						result.append(jsonStr);
					reader.close();// 关闭输入流
					String requestStr = URLDecoder.decode(result.toString(), "UTF-8");
					logger.info("CallBackController.chuangRuiReportV3-RequestData{}", requestStr);
					/*String name = request.getParameter("name");
					String sendid = request.getParameter("sendid");
					String time = request.getParameter("time");
					String state = request.getParameter("state");
					logger.info("创锐短信发送结果回执："+name+"-"+sendid+"-"+state+"-"+time);
					if("DELIVRD".equals(state)){
						plainSendRecordDao.updateStatusByReport(sendid, "500", "发送成功", DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
					}else{
						plainSendRecordDao.updateStatusByReport(sendid, "400", state, DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
					}*/
					response.setCharacterEncoding("UTF-8");
					response.setContentType("application/json; charset=utf-8");
					logger.info("CallBackController.chuangRuiReportV3-Reponse：{}",resultStr);
				} catch (TradeException e) {
					resultStr = "error"+e.getErrorMsg();
					logger.info("CallBackController.chuangRuiReportV3-TradeException：{}", e);
				} catch (Exception e) {
					resultStr = "{\"msg\":\"系统内部错误\",\"code\":\"9999\"}";
					logger.info("CallBackController.chuangRuiReportV3-Exception：{}", e);
				} finally{
					if(reader != null)
						reader.close();
				}
				return resultStr;
			}
		};
	}
	
	/**
	 * 263返回报告推送
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/net263Report")
	public Callable<String> net263Report(HttpServletRequest request, HttpServletResponse response){
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				String reqIp = getIpAddr(request);
				logger.info("sendMsg synchro start request :{}", reqIp);	//获取请求IP
				BufferedReader reader = null;
				String jsonStr = null;
				StringBuilder result = new StringBuilder();
                String resultStr = "success";
				try {
					if(null != prepareParamService.getConvinceIp(reqIp))
						throw new TradeException("9988","{\"msg\":\"非法IP\",\"code\":\"9988\"}");
					reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
	    	    	while ((jsonStr = reader.readLine()) != null)
	    	    		result.append(jsonStr);
	    	    	reader.close();// 关闭输入流
	    	    	
	    	    	String msgid = request.getParameter("msgid");
	    	    	String reportTime = request.getParameter("reportTime");
	    	    	String status = request.getParameter("status");
	    	    	logger.info("net263短信发送结果回执："+msgid+"-"+reportTime+"-"+status);
	        		if("DELIVRD".equals(status)){
	        			plainSendRecordDao.updateStatusByReport(msgid, "500", "发送成功", DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
	        		}else{
	        			plainSendRecordDao.updateStatusByReport(msgid, "400", status, DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
	        		}
		        	response.setCharacterEncoding("UTF-8");
            		response.setContentType("application/json; charset=utf-8");
            		logger.info("接口处理成功："+resultStr);
                } catch (TradeException e) {
                	resultStr = "error"+e.getErrorMsg();
                	logger.error("接口处理失败："+e.getErrorMsg());
                } catch (Exception e) {
                	resultStr = "{\"msg\":\"系统内部错误\",\"code\":\"9999\"}";
                	logger.error("接口处理异常："+e.getMessage());
                } finally{
                	if(reader != null)
                		reader.close();
                }
				return resultStr;
			}
		};
	}
	
	@ResponseBody
	@RequestMapping("/jumengReport")
	public Callable<String> jumengReport(HttpServletRequest request, HttpServletResponse response){
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				Map<String, Object> resultMap = prepareResponseMap();
				BufferedReader reader = null;
				String jsonStr = null;
				StringBuilder result = new StringBuilder();
				try {
					String requestIp = getIpAddr(request);
					logger.info("CallBackController.jumengReport synchro start requestIP :{}", requestIp);	//获取请求IP
					reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
					while ((jsonStr = reader.readLine()) != null)
						result.append(jsonStr);
					reader.close();// 关闭输入流
					logger.info("聚梦回调内容：{}", result.toString());
					
					/*String msgid = request.getParameter("msgid");
					String reportTime = request.getParameter("reportTime");
					String status = request.getParameter("status");
					logger.info("net263短信发送结果回执："+msgid+"-"+reportTime+"-"+status);
					if("DELIVRD".equals(status)){
						plainSendRecordDao.updateStatusByReport(msgid, "500", "发送成功", DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
					}else{
						plainSendRecordDao.updateStatusByReport(msgid, "400", status, DatetimeUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
					}
					response.setCharacterEncoding("UTF-8");
					response.setContentType("application/json; charset=utf-8")*/;
				} catch (TradeException e) {
					logger.error("CallBackController.jumengReport-TradeException：{}", e);
					resultMap.put("code", e.getErrorCode());
					resultMap.put("msg", e.getErrorMsg());
				} catch (Exception e) {
					logger.error("CallBackController.jumengReport-Exception：{}", e);
					resultMap.put("code", "9999");
					resultMap.put("msg", "系统内部错误");
				} finally{
					if(reader != null)
						reader.close();
				}
				return resultMap.toString();
			}
		};
	}
	
	private String getIpAddr(HttpServletRequest request)throws Exception {
    	String ip = request.getHeader("X-Real-IP");		//先从nginx自定义配置获取
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
	        ip = request.getRemoteAddr();  
	    if(null != prepareParamService.getConvinceIp(ip))
			throw new TradeException("9988","{\"msg\":\"非法IP\",\"code\":\"9988\"}");
	    return ip;  
	}
	
	private Map<String, Object> prepareResponseMap() {
		Map<String, Object> reponseMap = new HashMap<String, Object>();
		reponseMap.put("code", "0000");
		reponseMap.put("msg", "成功");
		return reponseMap;
	}
	
	public static void main(String[] args)throws Exception {
		String rspStr = "data=88568517378019,13305148812,DELIVRD,1512626287;36244879757985,18218063381,DELIVRD,1512626893;46496293133124,18353635985,DELIVRD,1512626905";
		String[] reports = rspStr.substring(5).split(";");
		for(int i=0;i<reports.length;i++){
			String[] reportOne = reports[i].split(",");
			String reqid = reportOne[0];
			System.out.println(reqid);
		}
	}
}