package com.sms.dao.primarydao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sms.criteria.ReservationSendRecordCriteria;
import com.sms.dao.IGenericDao;
import com.sms.entity.ReservationSendRecord;

public interface ReservationSendRecordDao extends IGenericDao<ReservationSendRecord, Long>{
	public Integer insertBatch(@Param("strs")String[] strs, @Param("reservationSendRecord")ReservationSendRecord reservationSendRecord);

	public List<ReservationSendRecord> loadAppointmentSms();
	
	public List<Map<String, Object>> queryResult(ReservationSendRecordCriteria reservationSendRecordCriteria);
}
