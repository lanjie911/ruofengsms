package com.sms.tradeservice.api.util;

public interface ResultCommon {
	
	interface ErrorCodeType {
		String SYSTEM_ERROR = "9999";//系统内部错误
		String SUCCESS = "0000";//交易成功
		String ACCEPT_SUCCESS = "0001";//受理成功
		String PARAM_WRONG = "0002";//请求参数不合法
		String LESS_PARAM = "0003";//缺少请求参数
		String JSON2BEAN_ERROR = "0004";//请求json转bean对象失败
		String REQ_AMOUNT_ERROR = "0005";//请求金额格式不正确
		String AMOUNT_ERROR = "0006";	//交易金额不合法
		String FAILURE_TO_QUERY = "0007";//查询结果不存在
		String GETWAY_FAILURE_TO_RECEIVE = "0008";//通道受理失败
		String NO_BORROW_APPLY = "0010";//借款记录不存在
		String NO_REPAY_BORROW_APPLY = "0011";//未找到代还款的借款记录
		String FAILURE_TO_FIND_RATE = "0012";//费率不存在
		String NO_LEND_BORROW_APPLY = "0013";//未找到代放款的借款记录
		String LESS_CREDIT = "0018";//可用额度不足
		String NO_FROZEN_LS = "0019";//未找到对应的冻结记录
		String PARAM_ERRO_APPLY_ID = "1001";//借款编号id为空
		String PARAM_ERRO_BORROWER_ID = "1002";//借款人编号为空
		String PARAM_ERRO_BORROWER_PLAN_STATUS = "1003";//还款计划状态为空
		String INSERT_FAILED = "1004";//数据库插入失败
		String NO_BACK_REPAY_APPLY = "1005";//未找到待处理的结款申请
		String PARAM_ERRO_NO_CREDIT_LEVEL = "1006";//信用等级不能为空
		String PARAM_ERRO_NO_LIMIT = "1007";//分页参数limit不能为空
		String PARAM_ERRO_NO_OFFSET = "1008";//分页参数offset不能为空
		String PARAM_ERRO_NO_DAYS = "1009";//借款天数不能为空
		String PARAM_ERRO_NO_ID_CARD_NO = "1010";//身份证号不能为空
		String PARAM_ERRO_NO_BANK_CARD_NO = "1011";//银行卡号不能为空
		String PARAM_ERRO_NO_BANK_MOBILE = "1012";//银行预留手机号不能为空
		String PARAM_ERRO_NO_BANK_CODE = "1013";//银行编码不能为空
		String PARAM_ERRO_NO_BANK_NAME = "1014";//银行名称不能为空
		String PARAM_ERRO_NO_BORROWER_MOBILE = "1015";//借款人手机号不能为空
		String PARAM_ERRO_NO_BORROWER_NAME = "1016";//借款人姓名不能为空
		String PARAM_ERRO_NO_REPAYMENT_TYPE = "1017";//还款方式不能为空
		String PARAM_ERRO_NO_BACK_REPAY_ID = "1018";//后台结款申请id不能为空
		String PARAM_ERRO_NO_BACK_REPAY_STATUS = "1019";//后台结款状态不能为空
		String PARAM_ERRO_NO_OPRETOR_ID = "1020";//后台结款操作员不能为空
		String REPAY_ERRO = "2001";//还款失败，逾期天数错误，请联系客服处理
	}
	
	enum ErrorCodeTypeEnum {
		SUCCESS(ErrorCodeType.SUCCESS,"成功"),
		ACCEPT_SUCCESS(ErrorCodeType.ACCEPT_SUCCESS, "受理成功"),
		PARAM_WRONG(ErrorCodeType.PARAM_WRONG, "请求参数不合法"),
		LESS_PARAM(ErrorCodeType.LESS_PARAM, "缺少请求参数"),
		REQ_AMOUNT_ERROR(ErrorCodeType.REQ_AMOUNT_ERROR, "请求金额格式不正确"),
		JSON2BEAN_ERROR(ErrorCodeType.JSON2BEAN_ERROR, "请求json转bean对象失败"),
		SYSTEM_ERROR(ErrorCodeType.SYSTEM_ERROR, "系统内部错误"),
		AMOUNT_ERROR(ErrorCodeType.AMOUNT_ERROR, "金额不合法"),
		FAILURE_TO_QUERY(ErrorCodeType.FAILURE_TO_QUERY, "查询结果不存在"),
		NO_BORROW_APPLY(ErrorCodeType.NO_BORROW_APPLY, "借款记录不存在"),
		NO_REPAY_BORROW_APPLY(ErrorCodeType.NO_REPAY_BORROW_APPLY, "未找到待还款的借款记录"),
		NO_LEND_BORROW_APPLY(ErrorCodeType.NO_LEND_BORROW_APPLY, "未找到待放款的借款记录"),
		GETWAY_FAILURE_TO_RECEIVE(ErrorCodeType.GETWAY_FAILURE_TO_RECEIVE, "通道受理失败"),
		LESS_CREDIT(ErrorCodeType.LESS_CREDIT, "可用额度不足"),
		NO_FROZEN_LS(ErrorCodeType.NO_FROZEN_LS, "未找到对应的冻结记录"),
		PARAM_ERRO_APPLY_ID(ErrorCodeType.PARAM_ERRO_APPLY_ID, "借款订单编号不能为空"),
		PARAM_ERRO_BORROWER_ID(ErrorCodeType.PARAM_ERRO_BORROWER_ID, "借款人编号不能为空"),
		PARAM_ERRO_BORROWER_PLAN_STATUS(ErrorCodeType.PARAM_ERRO_BORROWER_PLAN_STATUS, "还款计划状态为空"),
		PARAM_ERRO_NO_CREDIT_LEVEL(ErrorCodeType.PARAM_ERRO_NO_CREDIT_LEVEL, "信用等级不能为空"),
		PARAM_ERRO_NO_LIMIT(ErrorCodeType.PARAM_ERRO_NO_LIMIT, "分页参数limit不能为空"),
		PARAM_ERRO_NO_OFFSET(ErrorCodeType.PARAM_ERRO_NO_OFFSET, "分页参数offset不能为空"),
		FAILURE_TO_FIND_RATE(ErrorCodeType.FAILURE_TO_FIND_RATE, "费率不存在"),
		NO_BACK_REPAY_APPLY(ErrorCodeType.NO_BACK_REPAY_APPLY, "未找到待处理的结款申请"),
		PARAM_ERRO_NO_BACK_REPAY_ID(ErrorCodeType.PARAM_ERRO_NO_BACK_REPAY_ID, "后台结款申请id不能为空"),
		PARAM_ERRO_NO_DAYS(ErrorCodeType.PARAM_ERRO_NO_DAYS, "借款天数不能为空"),
		PARAM_ERRO_NO_ID_CARD_NO(ErrorCodeType.PARAM_ERRO_NO_ID_CARD_NO, "身份证号不能为空"),
		PARAM_ERRO_NO_BANK_CARD_NO(ErrorCodeType.PARAM_ERRO_NO_BANK_CARD_NO, "银行卡号不能为空"),
		PARAM_ERRO_NO_BANK_MOBILE(ErrorCodeType.PARAM_ERRO_NO_BANK_MOBILE, "银行预留手机号不能为空"),
		PARAM_ERRO_NO_BANK_CODE(ErrorCodeType.PARAM_ERRO_NO_BANK_CODE, "银行编码不能为空"),
		PARAM_ERRO_NO_BANK_NAME(ErrorCodeType.PARAM_ERRO_NO_BANK_NAME, "银行名称不能为空"),
		PARAM_ERRO_NO_BORROWER_MOBILE(ErrorCodeType.PARAM_ERRO_NO_BORROWER_MOBILE, "借款人手机号不能为空"),
		PARAM_ERRO_NO_BORROWER_NAME(ErrorCodeType.PARAM_ERRO_NO_BORROWER_NAME, "借款人姓名不能为空"),
		PARAM_ERRO_NO_REPAYMENT_TYPE(ErrorCodeType.PARAM_ERRO_NO_REPAYMENT_TYPE, "还款方式不能为空"),
		PARAM_ERRO_NO_BACK_REPAY_STATUS(ErrorCodeType.PARAM_ERRO_NO_BACK_REPAY_STATUS, "后台结款状态不能为空"),
		PARAM_ERRO_NO_OPRETOR_ID(ErrorCodeType.PARAM_ERRO_NO_OPRETOR_ID, "后台结款操作员不能为空"),
		REPAY_ERRO(ErrorCodeType.REPAY_ERRO, "逾期天数错误，还款失败！请及时联系客服处理"),
		INSERT_FAILED(ErrorCodeType.INSERT_FAILED, "数据库插入失败");
		
		private String value;
		private String text;
		ErrorCodeTypeEnum(String value, String text) {
			this.value = value;
			this.text = text;
		}
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