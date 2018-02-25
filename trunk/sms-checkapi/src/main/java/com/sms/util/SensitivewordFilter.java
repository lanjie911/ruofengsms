package com.sms.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.sms.BaseServlet;
import com.sms.CheckServlet;

public class SensitivewordFilter extends BaseServlet{
	
	private static Logger logger = Logger.getLogger(SensitivewordFilter.class);
	
	private static final long serialVersionUID = -6702412750608119396L;
	private static SensitivewordFilter instance;  
	
	public static SensitivewordFilter getInstance() throws IOException {  
	    if (instance == null) {  
	        instance = new SensitivewordFilter();  
	    }  
	    return instance;  
	    }  
	
	public SensitivewordFilter() throws IOException{
		super();
	}
	
	public boolean isContaintSensitiveWord(String txt,int matchType){
		boolean flag = false;
		for(int i = 0 ; i < txt.length() ; i++){
			int matchFlag = this.CheckSensitiveWord(txt, i, matchType); 
			if(matchFlag > 0){   
				flag = true;
			}
		}
		return flag;
	}
	
	public Set<String> getSensitiveWord(String txt , int matchType){
		Set<String> sensitiveWordList = new HashSet<String>();
		
		for(int i = 0 ; i < txt.length() ; i++){
			int length = CheckSensitiveWord(txt, i, matchType);    
			if(length > 0){
				sensitiveWordList.add(txt.substring(i, i+length));
				i = i + length - 1;    
			}
		}
		
		return sensitiveWordList;
	}
	
	public String replaceSensitiveWord(String txt,int matchType,String replaceChar){
		String resultTxt = txt;
		Set<String> set = getSensitiveWord(txt, matchType);
		Iterator<String> iterator = set.iterator();
		String word = null;
		String replaceString = null;
		while (iterator.hasNext()) {
			word = iterator.next();
			replaceString = getReplaceChars(replaceChar, word.length());
			resultTxt = resultTxt.replaceAll(word, replaceString);
		}
		
		return resultTxt;
	}
	
	private String getReplaceChars(String replaceChar,int length){
		String resultReplace = replaceChar;
		for(int i = 1 ; i < length ; i++){
			resultReplace += replaceChar;
		}
		
		return resultReplace;
	}
	
	
	/** 
     * 检查文字中是否包含敏感字符，检查规则如下：<br> 
     * @param txt 
     * @param beginIndex 
     * @param matchType 
     * @return，如果存在，则返回敏感词字符的长度，不存在返回0 
     * @version 1.0 
     */  
	@SuppressWarnings({ "rawtypes"})
	public int CheckSensitiveWord(String txt,int beginIndex,int matchType){
		boolean  flag = false;
		int matchFlag = 0;
		char word = 0;
		Map nowMap = SensitivewordFilter.sensitiveWordMap;
		for(int i = beginIndex; i < txt.length() ; i++){
			word = txt.charAt(i);
			nowMap = (Map) nowMap.get(word);
			if(nowMap != null){
				matchFlag++; 
				if("1".equals(nowMap.get("isEnd"))){
					flag = true;
					if(SensitivewordFilter.minMatchTYpe == matchType){  
						break;
					}
				}
			}
			else{     //不存在，直接返回  
				break;
			}
		}
		if(matchFlag < 2 || !flag){       
			matchFlag = 0;
		}
		return matchFlag;
	}
	
	
	//模板匹配
	public int match(String source, String pattern) {
		int index = -1;
		boolean match = true;

		for (int i = 0, len = source.length() - pattern.length(); i <= len; i++) {
			match = true;

			for (int j = 0; j < pattern.length(); j++) {
				if (source.charAt(i + j) != pattern.charAt(j)) {
					match = false;
					break;
				}
			}

			if (match) {
				index = i;
				break;
			}
		}

		return index;
	}

	public boolean matchTemplate(String msgContent, String accountNo) {
		boolean isMatch = false;
		Map<Object,Object> map = (Map<Object,Object>)templateMap.get(accountNo);
		if(null == map){
			logger.error("该商户未关联模板"+accountNo);
			return false;
		}
		for(int i=msgContent.length(); i>0; i--){
			List<String> templateList =  (List<String>)map.get(i);
			if(null == templateList)
				continue;
			for(String template : templateList){
				isMatch = Pattern.matches(template, msgContent);
				if(isMatch)
					break;
			}
			if(isMatch)
				break;
		}
		return isMatch;
	}
//	public static void main(String[] args) {
//		String info = "您购买的T000291021定期宝产品，加息券加息收益21,000.00元已到账，请登录优普钱包查收，现在投资即享高额收益~";
//		String template = "^您购买的*[\\s|\\S]*定期宝产品，加息券加息收益*[\\s|\\S]*元已到账，请登录优普钱包查收，现在投资即享高额收益~$";
//		boolean isMatch = Pattern.matches(template, info);
//		System.out.println(isMatch);
////		String smsTemplate = "^您好，您";
////		String smsInfo = "您购买的\"T000291021\"定期宝产品，加息券加息收益21,000.00元已到账，请登录优普钱包查收，现在投资即享高额收益~";
//	}
	
}
