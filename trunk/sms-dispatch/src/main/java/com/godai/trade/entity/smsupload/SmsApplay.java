package com.godai.trade.entity.smsupload;

import java.io.Serializable;
import java.sql.Timestamp;

public class SmsApplay implements Serializable {

	private static final long serialVersionUID = -8825708245170790089L;
	private Integer smsApplayId;		// 主键ID
	private Long batchNo;				// 批次号
	private Integer batchType;			// 短信发送类型	100-普通批量发送	200-高级批量发送	300-接口发送
	private Long accountNo;				// 商户号
	private Integer accountType;		// 账户类型	100-行业账户	200-营销账户 300-全类型商户
	private String accountName;			// 商户名称
	private String smsContent;			// 短信内容
	private int mobileCount;			// 手机号总数
	private int outlierCount;			// 手机号异常总数
	private int repeatCount;			// 手机号重复数量
	private int succCount;				// 需处理手机号数量
	private Integer orderFlag;			// 是否预约	100-是	200-否
	private Timestamp appointmentTime;	// 预约发送时间
	private Integer applayStatus;		// 批次状态100-已上传待处理	101-上传成功待审核 200-审核通过 300-审核拒绝 400-数据处理超时
	private Timestamp createDatetime;	// 创建时间
	private String operator;			// 上传操作员
	private String signTip;				// 签名
	
	private String textAreaVal;			// 页面展示使用textarea
	private String headStr;
	private String hiddenVal;
	private String mobileColumn;
	
	public Integer getSmsApplayId() {
		return smsApplayId;
	}
	public Long getBatchNo() {
		return batchNo;
	}
	public Integer getBatchType() {
		return batchType;
	}
	public Long getAccountNo() {
		return accountNo;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public String getAccountName() {
		return accountName;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public int getMobileCount() {
		return mobileCount;
	}
	public int getOutlierCount() {
		return outlierCount;
	}
	public int getRepeatCount() {
		return repeatCount;
	}
	public int getSuccCount() {
		return succCount;
	}
	public Integer getOrderFlag() {
		return orderFlag;
	}
	public Timestamp getAppointmentTime() {
		return appointmentTime;
	}
	public Integer getApplayStatus() {
		return applayStatus;
	}
	public Timestamp getCreateDatetime() {
		return createDatetime;
	}
	public String getOperator() {
		return operator;
	}
	public String getSignTip() {
		return signTip;
	}
	public String getTextAreaVal() {
		return textAreaVal;
	}
	public String getHeadStr() {
		return headStr;
	}
	public String getHiddenVal() {
		return hiddenVal;
	}
	public String getMobileColumn() {
		return mobileColumn;
	}
	public void setSmsApplayId(Integer smsApplayId) {
		this.smsApplayId = smsApplayId;
	}
	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}
	public void setBatchType(Integer batchType) {
		this.batchType = batchType;
	}
	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	public void setMobileCount(int mobileCount) {
		this.mobileCount = mobileCount;
	}
	public void setOutlierCount(int outlierCount) {
		this.outlierCount = outlierCount;
	}
	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}
	public void setSuccCount(int succCount) {
		this.succCount = succCount;
	}
	public void setOrderFlag(Integer orderFlag) {
		this.orderFlag = orderFlag;
	}
	public void setAppointmentTime(Timestamp appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	public void setApplayStatus(Integer applayStatus) {
		this.applayStatus = applayStatus;
	}
	public void setCreateDatetime(Timestamp createDatetime) {
		this.createDatetime = createDatetime;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public void setSignTip(String signTip) {
		this.signTip = signTip;
	}
	public void setTextAreaVal(String textAreaVal) {
		this.textAreaVal = textAreaVal;
	}
	public void setHeadStr(String headStr) {
		this.headStr = headStr;
	}
	public void setHiddenVal(String hiddenVal) {
		this.hiddenVal = hiddenVal;
	}
	public void setMobileColumn(String mobileColumn) {
		this.mobileColumn = mobileColumn;
	}
	@Override
	public String toString() {
		return "SmsApplay [smsApplayId=" + smsApplayId + ", batchNo=" + batchNo + ", batchType=" + batchType
				+ ", accountNo=" + accountNo + ", accountType=" + accountType + ", accountName=" + accountName
				+ ", smsContent=" + smsContent + ", mobileCount=" + mobileCount + ", outlierCount=" + outlierCount
				+ ", repeatCount=" + repeatCount + ", succCount=" + succCount + ", orderFlag=" + orderFlag
				+ ", appointmentTime=" + appointmentTime + ", applayStatus=" + applayStatus + ", createDatetime="
				+ createDatetime + ", operator=" + operator + ", signTip=" + signTip + ", textAreaVal=" + textAreaVal
				+ ", headStr=" + headStr + ", hiddenVal=" + hiddenVal + ", mobileColumn=" + mobileColumn + "]";
	}
}