package com.sms.dto.maiyuan;

import java.io.Serializable;
import java.util.Arrays;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "returnsms")
public class MaiYuantDto implements Serializable {

	/**
	 * 腾达报告
	 */
	private static final long serialVersionUID = -7753360482016742486L;
	
	private String returnstatus;
	private String message;
	private String remainpoint;
	private String taskID;
	private String successCounts;
	public String getReturnstatus() {
		return returnstatus;
	}
	public void setReturnstatus(String returnstatus) {
		this.returnstatus = returnstatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRemainpoint() {
		return remainpoint;
	}
	public void setRemainpoint(String remainpoint) {
		this.remainpoint = remainpoint;
	}
	public String getTaskID() {
		return taskID;
	}
	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}
	public String getSuccessCounts() {
		return successCounts;
	}
	public void setSuccessCounts(String successCounts) {
		this.successCounts = successCounts;
	}
	@Override
	public String toString() {
		return "MaiYuantDto [returnstatus=" + returnstatus + ", message=" + message + ", remainpoint=" + remainpoint
				+ ", taskID=" + taskID + ", successCounts=" + successCounts + "]";
	}
	
	
}