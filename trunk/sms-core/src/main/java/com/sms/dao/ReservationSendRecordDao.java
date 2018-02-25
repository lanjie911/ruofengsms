package com.sms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sms.criteria.ReservationSendRecordCriteria;
import com.sms.entity.ReservationSendRecord;

public interface ReservationSendRecordDao extends IGenericDao<ReservationSendRecord, Long>{
	public Integer insertBatch(@Param("strs")String[] strs, @Param("reservationSendRecord")ReservationSendRecord reservationSendRecord);

	public List<ReservationSendRecord> loadAppointmentSms();

	public List<Map<String, Object>> queryResult(ReservationSendRecordCriteria reservationSendRecordCriteria);

	public Integer updateInitStatusToNew(@Param("recordId")Integer recordId, @Param("init")Integer init,@Param("status")Integer status);
}
