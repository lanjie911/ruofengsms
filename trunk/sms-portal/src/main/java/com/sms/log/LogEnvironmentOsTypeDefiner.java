package com.sms.log;



import org.apache.commons.lang3.SystemUtils;

import ch.qos.logback.core.Context;
import ch.qos.logback.core.spi.PropertyDefiner;
import ch.qos.logback.core.status.Status;

/**
 * 为logback获取系统类型环境变量
 * @Description 
 * @author bqct_bya
 * @CreateTime 2017年4月13日 下午12:46:13
 */
public class LogEnvironmentOsTypeDefiner implements PropertyDefiner{

	@Override
	public void addError(String arg0) {}

	@Override
	public void addError(String arg0, Throwable arg1) {}

	@Override
	public void addInfo(String arg0) {}

	@Override
	public void addInfo(String arg0, Throwable arg1) {}

	@Override
	public void addStatus(Status arg0) {}

	@Override
	public void addWarn(String arg0) {}

	@Override
	public void addWarn(String arg0, Throwable arg1) {}

	@Override
	public Context getContext() {
		return null;
	}

	@Override
	public void setContext(Context arg0) {}

	@Override
	public String getPropertyValue() {
		if(SystemUtils.IS_OS_WINDOWS){
			System.out.println("Operating System is =========>windows");
			return "windows";
		}else if(SystemUtils.IS_OS_LINUX){
			System.out.println("Operating System is =========>linux");
			return "linux";
		}else if(SystemUtils.IS_OS_UNIX){
			System.out.println("Operating System is =========>unix");
			return "unix";
		}else{
			// 无法判断出来的情况，默认返回linux.
			System.out.println("Operating System is =========>unknown");
			return "linux";
		}
	}

}
