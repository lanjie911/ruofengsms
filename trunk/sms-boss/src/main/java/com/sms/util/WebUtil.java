package com.sms.util;

/**
 * WEB工具类
 * 
 */
public final class WebUtil {
	private WebUtil() {
	}

	/**
	 * 封装后台往前台打印消息
	 * <p>
	 * 返回AJXS的JSON串
	 * 
	 * @return
	 */
	public static String getMessage(boolean flag, String message, String dataJosn) {
		String f = flag ? "true" : "false";
		if (message == null || message.trim().length() == 0)
			message = "";
		if (dataJosn == null || dataJosn.trim().length() == 0)
			dataJosn = "";
		return "{\"success\":" + f + ", \"message\":\"" + message + "\",\"data\":[" + dataJosn + "]}";
	}

	/**
	 * 封装后台往前台操作成功的打印消息
	 * <p>
	 * 返回AJXS的JSON串
	 * 
	 * @return
	 */
	public static String getSuccuseMessage(String message) {
		return getMessage(true, message, null);
	}

	/**
	 * 封装后台往前台操作成功的打印消息
	 * <p>
	 * 返回AJXS的JSON串
	 * 
	 * @return
	 */
	public static String getSuccuseDataMessage(String dataJosn) {
		return getMessage(true, "", dataJosn);
	}

	/**
	 * 封装后台往前台操作失败的打印消息
	 * <p>
	 * 返回AJXS的JSON串
	 * 
	 * @return
	 */
	public static String getFailureMessage(String message) {
		return getMessage(false, message, null);
	}
}
