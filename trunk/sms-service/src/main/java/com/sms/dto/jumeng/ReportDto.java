package com.sms.dto.jumeng;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="report")
public class ReportDto implements Serializable {
	
	private static final long serialVersionUID = -7909624637439957548L;

	private String mobile;
	private String taskid;
	private String status;
	private String errcode;
	private String receivetime;
	
	@XmlElement(name="mobile")
	public String getMobile() {
		return mobile;
	}
	
	@XmlElement(name="taskid")
	public String getTaskid() {
		return taskid;
	}
	
	@XmlElement(name="status")
	public String getStatus() {
		return status;
	}
	
	@XmlElement(name="errcode")
	public String getErrcode() {
		return errcode;
	}
	
	@XmlElement(name="receivetime")
	public String getReceivetime() {
		return receivetime;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public void setReceivetime(String receivetime) {
		this.receivetime = receivetime;
	}
	@Override
	public String toString() {
		return "ReportDto [mobile=" + mobile + ", taskid=" + taskid + ", status=" + status + ", errcode=" + errcode
				+ ", receivetime=" + receivetime + "]";
	}
}