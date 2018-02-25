package com.godai.trade.dao;

import java.util.List;
import java.util.Map;

import com.godai.trade.entity.Auditing;

public interface AuditingDao {

	public List<Auditing> getNeedToSend(Map<String, Object> parmas);
}