package com.sms.entity.smsmanager;

import java.io.Serializable;
/**
 * @author Cao
 * 通道
 */
public class SenstiveWord implements Serializable{

	private static final long serialVersionUID = -1L;
	
	private Long wordId;
	private String wordContent;
	private Integer wordStatus;
	private String wordStatusDes;
	public Long getWordId() {
		return wordId;
	}
	public void setWordId(Long wordId) {
		this.wordId = wordId;
	}
	public String getWordContent() {
		return wordContent;
	}
	public void setWordContent(String wordContent) {
		this.wordContent = wordContent;
	}
	public Integer getWordStatus() {
		return wordStatus;
	}
	public void setWordStatus(Integer wordStatus) {
		this.wordStatus = wordStatus;
	}
	public String getWordStatusDes() {
		return wordStatusDes;
	}
	public void setWordStatusDes(String wordStatusDes) {
		this.wordStatusDes = wordStatusDes;
	}
}
