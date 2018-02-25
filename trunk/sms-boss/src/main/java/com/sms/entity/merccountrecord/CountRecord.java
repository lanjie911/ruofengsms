package com.sms.entity.merccountrecord;

import java.io.Serializable;
import java.util.List;

public class CountRecord implements Serializable{

	private static final long serialVersionUID = -8404937874518642392L;
	private Long recordId ;  
	private String refId ;
	private Long sumCount ;
	private Long sucCount ;
	private Long failCount ;
	private String columnType ;
	private String columnName; 
	private String countTime;
	
	private List<Long> columnValueGroup;
	private List<String> categories;
	
	public Long getRecordId() {
		return recordId;
	}
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	public String getRefId() {
		return refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}
	public Long getSumCount() {
		return sumCount;
	}
	public void setSumCount(Long sumCount) {
		this.sumCount = sumCount;
	}
	public List<Long> getColumnValueGroup() {
		return columnValueGroup;
	}
	public void setColumnValueGroup(List<Long> columnValueGroup) {
		this.columnValueGroup = columnValueGroup;
	}
	public Long getSucCount() {
		return sucCount;
	}
	public void setSucCount(Long sucCount) {
		this.sucCount = sucCount;
	}
	public Long getFailCount() {
		return failCount;
	}
	public void setFailCount(Long failCount) {
		this.failCount = failCount;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getCountTime() {
		return countTime;
	}
	public void setCountTime(String countTime) {
		this.countTime = countTime;
	}
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	
}
