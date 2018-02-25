package com.sms.dto.xunqi;

import java.io.Serializable;

import com.sms.dto.MsgBox;

public class Statusbox extends MsgBox implements Serializable {

	private static final long serialVersionUID = 6479771158414993915L;
	private String exno;

	public Statusbox(String mobile, String taskid, String status, String receivetime, String errorcode, String exno) {
		super(mobile, taskid, status, receivetime, errorcode);
		this.exno = exno;
	}

	public String getExno() {
		return exno;
	}

	public void setExno(String exno) {
		this.exno = exno;
	}

	@Override
	public String toString() {
		return "Statusbox [exno=" + exno + "]";
	}

}