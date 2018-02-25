package com.sms.tradeservice.api.service;

/**
 * 交易系统-交易服务接口
 * @author caochengfan
 */
public interface TradeExcuteService {
	
	/**
	 * 交易系统-行业短信
	 * @param request jsonString
	 * @return json
	 */
	public String sendMsg(String request);
	
	/**
	 * 交易系统-发个性短信（批量不同内容）
	 * @param request jsonString
	 * @return json
	 */
	public String sendDifferentMsg(String request);
	
	/**
	 * 交易系统-营销短息
	 * @param request jsonString
	 * @return json
	 */
	public String sendMsgMarket(String request);
	
	/**
	 * 交易系统-查询余额
	 * @param request jsonString
	 * @return json
	 */
	public String balanceQuery(String request);
	
	/**
	 * 交易系统-查询发送
	 * @param request jsonString
	 * @return json
	 */
	public String resultQuery(String request);
	
}