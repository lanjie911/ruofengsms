package com.sms.tradeservice.api.service;

/**
 * 交易系统-dispatch服务接口
 * @author caochengfan
 */
public interface TradeForDispatchService {
	
	/**
	 * 交易系统-审核后发送营销短信
	 * @param request jsonString
	 * @return json
	 */
	public String doSendMarketMsgAfterAudit(String request);
	
	/**
	 * 交易系统-审核后发送黑名单，退订等
	 * @param request jsonString
	 * @return json
	 */
	public String doSendAfterAudit(String request);
	
	/**
	 * 交易系统-发送预约短信
	 * @param request jsonString
	 * @return json
	 */
	public String doSendReservationMsg(String request);
	
	/**
	 * 交易系统-失败短信冲正金额
	 * @param request jsonString
	 * @return json
	 */
	public String doCrectAfterFail(String request);
	
	/**
	 * 交易系统-统计通道数据
	 * @param request jsonString
	 * @return json
	 */
	public String countChannelData(String request);
	
	/**
	 * 交易系统--统计平台数据
	 * @param request jsonString
	 * @return json
	 */
	public String countPlatformData(String request);
	
	/**
	 * 普通短信批量发送
	 * @param request
	 */
	public void sendSmsBatchNormal(String request);
		
}