package com.sms.entity.bizDict;

public interface EumCommon {
	/**
	 * 中行交易信息表状态
	 */
	interface BocTransStatus {
		// 100 - 待处理
		Integer PENDING = 100;
		// 200 - 交易成功
		Integer SUCCESS = 200;
		// 300 - 系统处理中
		Integer INHAND = 300;
		// 400 - 银行处理中
		Integer BANKING = 400;
		// 500 - 交易失败
		Integer FAILURE = 500;
		// 600 - 内部系统校验失败
		Integer VERIFICATIONFAILED = 600;
	}

	enum BocTransStatusEnum implements ConstantEnum {
		PENDING(BocTransStatus.PENDING, "待处理"), SUCCESS(BocTransStatus.SUCCESS, "交易成功"), INHAND(BocTransStatus.INHAND, "系统处理中"),
		BANKING(BocTransStatus.BANKING, "银行处理中"), FAILURE(BocTransStatus.FAILURE, "交易失败"), 
		VERIFICATIONFAILED(BocTransStatus.VERIFICATIONFAILED, "内部系统校验失败");
		
		private Integer value;
		private String text;
		
		BocTransStatusEnum(Integer value, String text) {
			this.value = value;
			this.text = text;
		}

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}
	
	interface NotifyFlag{
		// 1 - 未通知
		Integer UNANNOUNCED = 1;
		// 2 - 已通知
		Integer HASBEENNOTIFIED = 2;
	}
	
	enum NotifyFlagEnum implements ConstantEnum{
		UNANNOUNCED(NotifyFlag.UNANNOUNCED, "未通知"), HASBEENNOTIFIED(NotifyFlag.HASBEENNOTIFIED, "已通知");
		
		NotifyFlagEnum(Integer value, String text) {
			this.value = value;
			this.text = text;
		}
		
		private Integer value;
		private String text;
		
		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}
	
	interface ErrorCodeType {
		String SUCCESS = "0000";//交易成功
		String ACCEPT_SUCCESS = "0001";//受理成功
		String PARAM_WRONG = "0002";//请求参数不合法
		String LESS_PARAM = "0010";//缺少请求参数
		String REQ_MESSAGE_ID_ERROR = "0011";//请求流水号格式有误 格式:yyyymmddhhmmss+6为随机数
		String REQ_MESSAGE_ID_REPEAT = "0012";//请求流水号提交重复
		String REQ_VERSION_ERROR = "0013";//请求版本号有误
		String REQ_TRANST_YPE_ERROR="0014";//请求交易类型不正确
		String REQ_AMOUNT_ERROR = "0015";//请求金额格式不正确
		String REQ_INNER_ID_REPEAT = "0016";//请求内部订单号重复
		String SIGN_ERROR = "0020";//签名验签失败
		String JSON2BEAN_ERROR = "0030";//请求json转bean对象失败
		String PRIVATE_DECODE_ERROR = "0101";//私钥解密失败
		String PUBLIC_ENCODE_ERROR = "1002";//公钥加密失败
		String PRIVATE_ENCODE_ERROR = "1003";//私钥加密失败
		String PUBLIC_DECODE_ERROR = "1004";//公钥解密失败
		String DES_ENCODE_ERROR = "1010";//DES加密失败
		String DES_DECODE_ERROR = "1011";//DES解密失败
		String DB_ERROR = "9998";//操作数据库失败
		String SYSTEM_ERROR = "9999";//系统内部错误
	}
	
	enum ErrorCodeTypeEnum {
		SUCCESS(ErrorCodeType.SUCCESS,"成功"),
		ACCEPT_SUCCESS(ErrorCodeType.ACCEPT_SUCCESS,"受理成功"),
		PARAM_WRONG(ErrorCodeType.PARAM_WRONG,"请求参数不合法"),
		LESS_PARAM(ErrorCodeType.LESS_PARAM,"缺少请求参数"),
		REQ_MESSAGE_ID_ERROR(ErrorCodeType.REQ_MESSAGE_ID_REPEAT,"请求流水号格式有误 格式:yyyymmddhhmmss+6为随机数"),
		REQ_MESSAGE_ID_REPEAT(ErrorCodeType.REQ_MESSAGE_ID_REPEAT,"请求流水号提交重复"),
		REQ_VERSION_ERROR(ErrorCodeType.REQ_VERSION_ERROR,"请求版本号有误"),
		REQ_TRANST_YPE_ERROR(ErrorCodeType.REQ_TRANST_YPE_ERROR,"请求交易类型不正确"),
		REQ_AMOUNT_ERROR(ErrorCodeType.REQ_AMOUNT_ERROR,"请求金额格式不正确"),
		REQ_INNER_ID_REPEAT(ErrorCodeType.REQ_INNER_ID_REPEAT,"请求内部订单号重复"),
		SIGN_ERROR(ErrorCodeType.SIGN_ERROR,"签名验签失败"),
		JSON2BEAN_ERROR(ErrorCodeType.JSON2BEAN_ERROR,"请求json转bean对象失败"),
		PRIVATE_DECODE_ERROR(ErrorCodeType.PRIVATE_DECODE_ERROR,"私钥解密失败"),
		PUBLIC_ENCODE_ERROR(ErrorCodeType.PUBLIC_ENCODE_ERROR,"公钥加密失败"),
		PRIVATE_ENCODE_ERROR(ErrorCodeType.PRIVATE_ENCODE_ERROR,"私钥加密失败"),
		PUBLIC_DECODE_ERROR(ErrorCodeType.PUBLIC_DECODE_ERROR,"公钥解密失败"),
		DES_ENCODE_ERROR(ErrorCodeType.DES_ENCODE_ERROR,"DES加密失败"),
		DES_DECODE_ERROR(ErrorCodeType.DES_DECODE_ERROR,"DES解密失败"),
		DB_ERROR(ErrorCodeType.DB_ERROR,"操作数据库失败"),
		SYSTEM_ERROR(ErrorCodeType.SYSTEM_ERROR,"系统内部错误");
		
		ErrorCodeTypeEnum(String value, String text) {
			this.value = value;
			this.text = text;
		}
		
		private String value;
		private String text;
		
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		
	}
	
}