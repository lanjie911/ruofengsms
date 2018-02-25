package com.sms.dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.sms.BaseServlet;
import com.sms.entity.Template;

public class TemplateDao extends BaseServlet{
	
	public TemplateDao() throws IOException {
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
	
	public List<Map> queryAll(){
		SqlSession session = null;
		try {
			session = factory.openSession();
			return session.selectList(getStatementNamespace()+".queryAll");
		}finally {
			if(session != null) session.close();
		}
	}	
	
	public List<String> queryAllAccount(){
		SqlSession session = null;
		try {
			session = factory.openSession();
			return session.selectList(getStatementNamespace()+".queryAllAccount");
		}finally {
			if(session != null) session.close();
		}
	}
	
	public List<Integer> queryAllLength(String accountNo){
		SqlSession session = null;
		try {
			Map<String,Object> param = new HashMap<>();
			param.put("accountNo", accountNo);
			session = factory.openSession();
			return session.selectList(getStatementNamespace()+".queryAllLength",param);
		}finally {
			if(session != null) session.close();
		}
	}
	
	public List<String> queryAllTemplate(String accountNo,Integer templateLength){
		SqlSession session = null;
		try {
			Map<String,Object> param = new HashMap<>();
			param.put("accountNo", accountNo);
			param.put("templateLength", templateLength);
			session = factory.openSession();
			return session.selectList(getStatementNamespace()+".queryAllTemplate",param);
		}finally {
			if(session != null) session.close();
		}
	}
	
}
