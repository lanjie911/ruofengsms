package com.sms.dao;

import java.sql.Date;
import java.sql.Timestamp;


public interface UtilDao {
	public Timestamp getCurrentTimestamp();
	public Date getCurrentDate();
	public Date getBeforeDate();
	public Date getAfterDate();
}
