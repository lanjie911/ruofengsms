package com.sms.dto;

public class MsgBox {

	private static final long serialVersionUID = 8486417100852634025L;

	private String mobile;
	private String taskid;
	private String status;
	private String receivetime;
	private String errorcode;

	public MsgBox() {
	}

	public MsgBox(String mobile, String taskid, String status, String receivetime, String errorcode) {
		this.mobile = mobile;
		this.taskid = taskid;
		this.status = status;
		this.receivetime = receivetime;
		this.errorcode = errorcode;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReceivetime() {
		return receivetime;
	}

	public void setReceivetime(String receivetime) {
		this.receivetime = receivetime;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	@Override
	public String toString() {
		return "MsgBox [mobile=" + mobile + ", taskid=" + taskid + ", status=" + status + ", receivetime=" + receivetime
				+ ", errorcode=" + errorcode + "]";
	}

}
