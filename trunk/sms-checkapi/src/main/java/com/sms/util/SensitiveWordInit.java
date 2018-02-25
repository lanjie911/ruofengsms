package com.sms.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sms.dao.SensitiveWordDao;
import com.sms.dao.TemplateDao;

public class SensitiveWordInit {
	private String ENCODING = "GBK";
	@SuppressWarnings("rawtypes")
	public HashMap sensitiveWordMap;
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> templateMap;
	
	public SensitiveWordInit(){
		super();
	}
	
	@SuppressWarnings("rawtypes")
	public Map initKeyWord(){
		try {
			SensitiveWordDao sensitiveWordDao = new SensitiveWordDao();
			List<String> list = sensitiveWordDao.queryAll();
			checkMsg(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sensitiveWordMap;
	}
	
	@SuppressWarnings("rawtypes")
	private void checkMsg(List<String> list) {
		sensitiveWordMap = new HashMap(list.size());
		String key = null;
		Map nowMap = null;
		Map<String, String> newWorMap = null;
	
		Iterator<String> iterator = list.iterator();
		while(iterator.hasNext()){
			key = iterator.next();  
			nowMap = sensitiveWordMap;
			for(int i = 0 ; i < key.length() ; i++){
				char keyChar = key.charAt(i);       
				Object wordMap = nowMap.get(keyChar);      
				
				if(wordMap != null){
					nowMap = (Map) wordMap;
				}
				else{
					newWorMap = new HashMap<String,String>();
					newWorMap.put("isEnd", "0");   
					nowMap.put(keyChar, newWorMap);
					nowMap = newWorMap;
				}
				
				if(i == key.length() - 1){
					nowMap.put("isEnd", "1");  
				}
			}
		}
	}

	public Map<String,Object> initTemplate(){
		Map<String,Object> accountLength = new HashMap<>();
		
		try {
			TemplateDao templateDao = new TemplateDao();
			List<String> accList = templateDao.queryAllAccount();
			for(String accountNo : accList){
				List<Integer> lengthList = templateDao.queryAllLength(accountNo);
				Map<Object,Object> lengthContent = new HashMap<>();
				for(Integer templateLength : lengthList){
					List<String> contentList = templateDao.queryAllTemplate(accountNo, templateLength);
					lengthContent.put(templateLength, contentList);
				}
				accountLength.put(accountNo, lengthContent);
			}
			templateMap = accountLength;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return templateMap;
	}
}
