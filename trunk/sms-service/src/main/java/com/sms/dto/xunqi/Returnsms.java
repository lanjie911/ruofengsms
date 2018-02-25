package com.sms.dto.xunqi;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.sms.dto.IReturnSMS;
import com.sms.dto.MsgBox;

@XmlRootElement
public class Returnsms implements Serializable, IReturnSMS {

	/**
	 * returnsms 标签实体
	 */
	private static final long serialVersionUID = 8486417100852634025L;
	private List<MsgBox> Statusbox;

	public List<MsgBox> getStatusbox() {
		return Statusbox;
	}

	public void setStatusbox(List<MsgBox> statusbox) {
		Statusbox = statusbox;
	}

	@Override
	public String toString() {
		return "Returnsms [Statusbox=" + Statusbox + "]";
	}

}