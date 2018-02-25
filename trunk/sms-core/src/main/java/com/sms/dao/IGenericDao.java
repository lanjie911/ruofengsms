package com.sms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sms.criteria.AbstractCriteria;

public interface IGenericDao<T, PK> {
	public T getById(@Param("id")PK id);
	public int queryCount(AbstractCriteria criteria);
	public List<T> query(AbstractCriteria criteria);
	public Integer insert(T t);
	public Integer update(T t);
	public Integer delete(PK id);
	public Integer deleteByCriteria(AbstractCriteria criteria);
}