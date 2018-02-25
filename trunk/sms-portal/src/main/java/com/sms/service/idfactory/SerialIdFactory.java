package com.sms.service.idfactory;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sms.util.IpUtil;



@Service
public class SerialIdFactory  {
	private static final Logger logger = LoggerFactory.getLogger(SerialIdFactory.class);
	private String suffix; //
	private int length = 7;
	private DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private int dateLength = 14;
	private long tempLong = 10000000L;
	private int currentId = 0;
	private boolean datePrefix;
	private Object semaphore = new Object();

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public void setDatePrefix(boolean b) {
		this.datePrefix = b;
	}

	public void setDateFormat(String format) {
		this.dateFormat = new SimpleDateFormat(format);
		this.dateLength = format.length();
	}

	public void setLength(int i) {
		this.length = i;
		this.tempLong = (long) Math.pow(10.0D, this.length);
	}

	protected String format(long l, String channelNo) {
		StringBuffer buf = new StringBuffer();
		if (this.datePrefix) {
			String dateStr = this.dateFormat.format(new Date());

			if (dateStr.length() != this.dateLength) {
				logger.error("unmatch date:" + dateStr);
				System.err.println("unmatch date:" + dateStr);
			}
			buf.append(dateStr);
		}
		String longStr = new Long(this.tempLong + l).toString();
		buf.append(longStr.substring(longStr.length() - this.length));
		buf.append(channelNo);
		if (this.suffix != null)
			buf.append(this.suffix);
		return buf.toString();
	}
	
	protected String format(long l) {
		StringBuffer buf = new StringBuffer();
		if (this.datePrefix) {
			String dateStr = this.dateFormat.format(new Date());
			
			if (dateStr.length() != this.dateLength) {
				logger.error("unmatch date:" + dateStr);
				System.err.println("unmatch date:" + dateStr);
			}
			buf.append(dateStr);
		}
		String longStr = new Long(this.tempLong + l).toString();
		buf.append(longStr.substring(longStr.length() - this.length));
		if (this.suffix != null)
			buf.append(this.suffix);
		return buf.toString();
	}

	/**
	 * 返回订单号24位yyyyMMddHHmmss+7位自增+ip
	 */
	public Object generate(String channelNo) {
		synchronized (this.semaphore) {
			this.currentId += 1;
			if (this.currentId >= this.tempLong) {
				this.currentId = 1;
			}
			long myId = this.currentId;
			Object object = format(myId,channelNo);
			return object;
		}
	}
	
	public String generate() {
		synchronized (this.semaphore) {
			this.currentId += 1;
			if (this.currentId >= this.tempLong) {
				this.currentId = 1;
			}
			long myId = this.currentId;
			return format(myId);
		}
	}

	@PostConstruct
	private void initParams() throws UnknownHostException,SocketException {
		try {
			String ip= IpUtil.getLocalIP().trim();
			String end = ip.substring(ip.lastIndexOf(".") + 1);
			this.setLength(7);
			this.setDatePrefix(true);// 拼接日期
			this.setSuffix(end); // 拼接ip最后的地址
			logger.info("SerialIdFactory-->serverInitIP:{},end:{}", ip, end);
		} catch (UnknownHostException e) {
			logger.error("SerialIdFactory-->UnknownHostException:" + e);
			throw e;
		}catch (SocketException e) {
			logger.error("SerialIdFactory-->UnknownHostException:" + e);
			throw e;
		}
	}

	public static void main(String[] args) throws UnknownHostException, SocketException {
		final SerialIdFactory id = new SerialIdFactory();
		id.setLength(0);
		id.setDatePrefix(true);// 拼接日期
		id.setSuffix(IpUtil.getLocalIP().trim().substring(IpUtil.getLocalIP().trim().lastIndexOf(".") + 1)); // 拼接ip
		Long orderNo = Long.valueOf(id.generate().toString());
		
		//20171114091842
		System.out.println(orderNo);
		
		/*for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					for (int j = 0; j < 1; j++) {
						System.out.println(id.generate("200").toString());
					}
				}
			}).start();
		}*/

	}

}