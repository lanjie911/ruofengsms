package com.sms.entity;

import java.io.Serializable;

/**
 * 
 * @Description 要加密的数据类型 
 * @author bqct_bya
 * @CreateTime 2017年1月3日 下午3:51:49
 */
public class AesString implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7271334136846304368L;

	/**
	 * 加密值
	 */
	private String cryptoValue; 
	
	/**
	 * 原始值
	 */
	private String originalValue;
	
	public AesString() {}
	
	public AesString(String cryptoValue, String originalValue) { 
		this.cryptoValue = cryptoValue;
		this.originalValue = originalValue;
	}

	/**
	 * 创建实例by原始值
	 * @param originalValue
	 * @return
	 */
	public static AesString getInstanceByOriginalValue(String originalValue){
		AesString desStr=new AesString();
		desStr.setOriginalValue(originalValue);
		return desStr;
	}
	
	/**
	 * 创建实例by加密值
	 * @param cryptoValue
	 * @return
	 */
	public static AesString getInstanceByCryptoValue(String cryptoValue){
		AesString desStr=new AesString();
		desStr.setCryptoValue(cryptoValue);
		return desStr;
	}

	public String getCryptoValue() {
		return cryptoValue;
	}

	public void setCryptoValue(String cryptoValue) {
		this.cryptoValue = cryptoValue;
	}

	public String getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(String originalValue) {
		this.originalValue = originalValue;
	}
	
	@Override
	public String toString() {
		return this.originalValue;
	}
	
}
