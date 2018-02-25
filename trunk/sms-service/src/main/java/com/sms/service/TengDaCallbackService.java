package com.sms.service;

import org.springframework.stereotype.Service;

import com.sms.dto.tengda.Returnsms;

@Service("tengDaCallbackService")
public class TengDaCallbackService extends AbstractCallbackService {

	public TengDaCallbackService() {
		super();
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getEntityClass() {
		return Returnsms.class;
	}

}