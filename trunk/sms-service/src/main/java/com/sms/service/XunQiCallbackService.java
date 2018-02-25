package com.sms.service;

import org.springframework.stereotype.Service;

import com.sms.dto.xunqi.Returnsms;

@Service("xunQiCallbackService")
public class XunQiCallbackService extends AbstractCallbackService {

	public XunQiCallbackService() {
		super();
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getEntityClass() {
		return Returnsms.class;
	}

}