package com.sms.dao;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.sms.BaseServlet;
import com.sms.entity.Template;
import com.sms.util.SensitiveWordInit;

public class SensitiveWordDao extends BaseServlet{
	
	public SensitiveWordDao() throws IOException {
		super();
	}
	
	public String getStatementNamespace(){
		return this.getClass().getName();
	}
	
	public Template getById(Map<String,Object> param){
		SqlSession session = null;
		try {
			session = factory.openSession();
			return session.selectOne(getStatementNamespace()+".getById", param);
		}finally {
			if(session != null) session.close();
		}
	}
	
	public List<String> queryAll(){
		SqlSession session = null;
		try {
			session = factory.openSession();
			return session.selectList(getStatementNamespace()+".queryAll");
		}finally {
			if(session != null) session.close();
		}
	}

}
