package com.godai.trade.dao.trans;

import org.apache.ibatis.annotations.Param;

import com.godai.trade.entity.trans.PlainSendRecord;

public interface PlainSendRecordDao {

	public PlainSendRecord countTranNumByAccountNo(@Param("accountNo") Long accountNo);

	public Integer countTransYesterday(@Param("accountNo") Long accountNo,
			@Param("yesterdayStart") String yesterdayStart, @Param("yesterdayEnd") String yesterdayEnd);

	public Integer queryTotalNum(@Param("accountNo") Long accountNo, @Param("yesterdayStart") String yesterdayStart,
			@Param("yesterdayEnd") String yesterdayEnd);

	public Integer querySuccNum(@Param("accountNo") Long accountNo, @Param("yesterdayStart") String yesterdayStart,
			@Param("yesterdayEnd") String yesterdayEnd);

	public Integer queryFailNum(@Param("accountNo") Long accountNo, @Param("yesterdayStart") String yesterdayStart,
			@Param("yesterdayEnd") String yesterdayEnd);

	public Integer queryUnknowNum(@Param("accountNo") Long accountNo, @Param("yesterdayStart") String yesterdayStart,
			@Param("yesterdayEnd") String yesterdayEnd);

}