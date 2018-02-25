package com.godai.trade.service;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.godai.trade.service.api.IdFactory;
import com.godai.trade.util.IpUtil;


/**
 * 
 * @Description 订单编号简单实现
 * @author bqct_bya
 * @CreateTime 2016年11月30日 上午10:54:57
 */
@Service(value="simpleIdFactory")
public class SimpleIdFactory implements IdFactory {
	private static final Logger logger = LoggerFactory.getLogger(SimpleIdFactory.class);
	private String prefix;
	private int length = 7;
	private boolean datePrefix;
	private String datesuffix;
	private String serviceIp;

	private DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

	private int dateLength = 14;

	private long tempLong = 10000000L;

	private int currentId = 0;

	private Object semaphore = new Object();

	public void setPrefix(String pre) {
		this.prefix = pre;
	}

	public void setDatePrefix(boolean b) {
		this.datePrefix = b;
	}
	
	public void setDatesuffix(String datesuffix) {
		this.datesuffix = datesuffix;
	}

	public void setDateFormat(String format) {
		this.dateFormat = new SimpleDateFormat(format);
		this.dateLength = format.length();
	}

	public void setLength(int i) {
		this.length = i;
		this.tempLong = (long) Math.pow(10.0D, this.length);
	}

	public void setServiceIp(String serviceIp) {
		this.serviceIp = serviceIp;
	}
	
	protected String format(long l) {
		StringBuffer buf = new StringBuffer();
		if (this.prefix != null)
			buf.append(this.prefix);
		if (this.datePrefix) {
			String dateStr = this.dateFormat.format(new Date());

			if (dateStr.length() != this.dateLength) {
				logger.error("unmatch date:" + dateStr);
				System.err.println("unmatch date:" + dateStr);
			}
			buf.append(dateStr);
		}
		if(this.datesuffix != null){
			buf.append(this.datesuffix);
		}
		String longStr = new Long(this.tempLong + l).toString();
		buf.append(longStr.substring(longStr.length() - this.length));
		return buf.toString();
	}

	/**
	 * 返回订单号yyyyMMddHHmmss+7位循环自增
	 */
	@Override
	public Object generate() {
		synchronized (this.semaphore) {
			this.currentId += 1;
			if (this.currentId >= this.tempLong) {
				this.currentId = 1;
			}
			long myId = this.currentId;
			Object object = format(myId);
			return object;
		}
	}
	
	@Override
	public Object generateTradeSerialNo() {
		StringBuffer sb=new StringBuffer();
		sb.append(generate());
		sb.append("100");
		sb.append(this.serviceIp);
		return sb.toString();
	}

	@PostConstruct
	private void initParams() throws UnknownHostException, SocketException {
		this.setDatePrefix(true);// 拼接日期
		// 初始化ip
		String ip=IpUtil.getLocalIP();
		String end = ip.substring(ip.lastIndexOf(".")+1);
		this.setServiceIp(StringUtils.leftPad(end, 3, "0"));
		logger.info("SimpleIdFactory-->serverInitIP:{},end:{}", ip, end);
	}
	

	public static void main(String[] args) throws UnknownHostException {
		  final SimpleIdFactory id=new SimpleIdFactory();
		  id.setDatePrefix(true);
		  for (int i = 0; i < 10; i++) {
				new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 5; j++) {
						System.out.println(id.generateTradeSerialNo());	
					}				
				}
			}).start();
		}
	  }

}