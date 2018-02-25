package com.godai.trade.service.api;

import java.sql.Timestamp;

public abstract interface TimeUtilService {
	
	public abstract Timestamp getDate();
	public abstract long getDistanceClockSecond(String timeClock);
}
