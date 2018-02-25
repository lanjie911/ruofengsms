package com.sms.dao.primarydao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.criteria.WhiteCriteria;
import com.sms.entity.White;

public interface WhiteDao{
	
	List<White> query(WhiteCriteria criteria);
	
	Integer updte(White white);
	
	White getByMobile(@Param("mobile") Long mobile);
	
	Integer insert(White white);
	
	Integer update(White white);
	
	Integer delete(@Param("whiteId") Long whiteId);
	
	List<White> qryWhiteList();

}
