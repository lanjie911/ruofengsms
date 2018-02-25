package com.sms.dto.tengda;

import java.io.Serializable;

import com.sms.dto.MsgBox;

public class Statusbox extends MsgBox implements Serializable {

	private static final long serialVersionUID = 4947967993235561466L;

	public Statusbox(String mobile, String taskid, String status, String receivetime, String errorcode) {
		super(mobile, taskid, status, receivetime, errorcode);
	}

}