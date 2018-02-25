package com.sms.criteria.mercmanager;

import com.sms.criteria.AbstractCriteria;

public class SmsTemplateCriteria extends AbstractCriteria {
	private Long templateId;                   //模板编号
	private String templateContent;               //模板内容
	private String dateBegin;              //创建日期开始
	private String dateEnd;            //创建日期结束
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public String getTemplateContent() {
		return templateContent;
	}
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}
	public String getDateBegin() {
		return dateBegin;
	}
	public void setDateBegin(String dateBegin) {
		this.dateBegin = dateBegin;
	}
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	
	
}