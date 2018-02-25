package com.sms.service;


import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sms.util.IpUtil;
@Service
public class IdWorker {
    
    public static String ip;
    
    static{
    	String str;
		try {
			str = IpUtil.getLocalIP();
			ip = str.substring(str.lastIndexOf(".")+1);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
    }

    public List<Long> nextId(String accountNo,Integer size) {
    	List<Long> strlist = new ArrayList<>();
    	for(int i=0;i<size;i++){
    		long time = System.currentTimeMillis();
    		int rd = (int)Math.ceil(Math.random()*(double)99999999);
       	 	DecimalFormat df = new DecimalFormat("00000000");
       	 	String recordId = ip + time +accountNo + df.format(rd);
       	 	Long hashId = Long.valueOf(Math.abs(recordId.hashCode()));
       	 	strlist.add(hashId);
    	}
    	return strlist;
    }

	public Long nextIdOne(String accountNo) {
		long time = System.currentTimeMillis();
		int rd = (int)Math.ceil(Math.random()*(double)99999999);
   	 	DecimalFormat df = new DecimalFormat("00000000");
   	 	String recordId = ip + time +accountNo + df.format(rd);
   	 	Long hashId = Long.valueOf(Math.abs(recordId.hashCode()));
   	 	return hashId;
	}
    
    public static void main(String[] args) {
//    	List<Long> s = nextId("100000123",40);
//    	Long s = nextIdOne("123");
//    	System.out.println(s);
	}
}

