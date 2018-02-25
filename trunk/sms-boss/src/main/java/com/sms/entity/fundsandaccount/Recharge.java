package com.sms.entity.fundsandaccount;

import java.io.Serializable;
/**
 * @author Cao
 * 通道
 */
public class Recharge implements Serializable{

	private static final long serialVersionUID = -1L;
	
	private Long depositId;  //充值记录id
	private Long accountNo;  //商户账号
	private String merchantName; //商户名称
	private String merchantNameAbbreviation; //商户名称
	private Integer accountType; //账号类型
	private String accountTypeDes;  //账号类型描述
	private Double amount;  //充值金额
	private Double unitPrice; //单价
	private Integer depositNum; //充值数量
	private String operator; //操作员
	private Integer depositStatus; //充值状态
	private String depositStatusSes; //充值状态描述
	private String createDatetime; //创建时间
	private Integer rechargeType; //充值类型
	private String rechargeTypeDes; //充值类型描述
	private String outRechargeNo; //外部订单号
	private String remark;  //备注
	
	
	public Long getDepositId() {
		return depositId;
	}
	public void setDepositId(Long depositId) {
		this.depositId = depositId;
	}
	public String getMerchantNameAbbreviation() {
		return merchantNameAbbreviation;
	}
	public void setMerchantNameAbbreviation(String merchantNameAbbreviation) {
		this.merchantNameAbbreviation = merchantNameAbbreviation;
	}
	public Integer getRechargeType() {
		return rechargeType;
	}
	public void setRechargeType(Integer rechargeType) {
		this.rechargeType = rechargeType;
	}
	public String getRechargeTypeDes() {
		return rechargeTypeDes;
	}
	public void setRechargeTypeDes(String rechargeTypeDes) {
		this.rechargeTypeDes = rechargeTypeDes;
	}
	public String getOutRechargeNo() {
		return outRechargeNo;
	}
	public void setOutRechargeNo(String outRechargeNo) {
		this.outRechargeNo = outRechargeNo;
	}
	public Long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public String getAccountTypeDes() {
		return accountTypeDes;
	}
	public void setAccountTypeDes(String accountTypeDes) {
		this.accountTypeDes = accountTypeDes;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getDepositNum() {
		return depositNum;
	}
	public void setDepositNum(Integer depositNum) {
		this.depositNum = depositNum;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Integer getDepositStatus() {
		return depositStatus;
	}
	public void setDepositStatus(Integer depositStatus) {
		this.depositStatus = depositStatus;
	}
	public String getDepositStatusSes() {
		return depositStatusSes;
	}
	public void setDepositStatusSes(String depositStatusSes) {
		this.depositStatusSes = depositStatusSes;
	}
	public String getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
