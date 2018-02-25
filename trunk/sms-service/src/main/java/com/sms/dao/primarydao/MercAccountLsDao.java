package com.sms.dao.primarydao;

import org.apache.ibatis.annotations.Param;

import com.sms.dao.IGenericDao;
import com.sms.entity.MercAccountLs;

public interface MercAccountLsDao extends IGenericDao<MercAccountLs, Long>{

	public Integer insertBatch(@Param("mercAccountLs")MercAccountLs mercAccountLs, @Param("strs")String[] strs);

}
