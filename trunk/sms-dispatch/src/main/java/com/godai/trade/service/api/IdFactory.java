package com.godai.trade.service.api;

public abstract interface IdFactory {
	public abstract Object generate();
	
	/**
	 * 获取用户系统接口的请求流水号
	 * @return
	 */
	public abstract Object generateTradeSerialNo();
}