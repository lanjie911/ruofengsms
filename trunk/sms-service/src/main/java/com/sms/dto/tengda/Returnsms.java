package com.sms.dto.tengda;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.sms.dto.IReturnSMS;
import com.sms.dto.MsgBox;

@XmlRootElement
public class Returnsms implements Serializable, IReturnSMS {

	/**
	 * returnsms
	 */
	private static final long serialVersionUID = -4382154786864443639L;
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