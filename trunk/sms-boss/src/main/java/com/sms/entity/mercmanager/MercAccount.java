package com.sms.entity.mercmanager;

import java.io.Serializable;

public class MercAccount implements Serializable {

	private static final long serialVersionUID = -8404937874518642392L;
	private Long accountNo;  //商户账号
	private String accountNoPass;  //商户账号密码-密文
	private String accountPwdClearText;  //商户账号密码-明文
	private Integer passSalt;  //密码salt
	private Long merchantId ;  //商户编号
	private String merchantName;               //商户名称
	private String merchantNameAbbreviation;  //商户简称
	private Integer accountType ;  //账户类型
	private String accountTypeDes ;  //账户类型描述
	private Integer paymentMethods;   //付费方式
	private String paymentMethodsDes;   //付费方式描述
	private Integer extendNo;   //自定义扩展号码
	private Integer chargingMethods;  //扣费方式
	private Double unitPrice ;   //短信单价：元
	private Integer priorityLevel;  //优先级别
	private Double totalBalance;   //账户总额 单位：条
	private Double freeBalance ;   //账户可用额 单位：条
	private Double frozenBalance ;   //冻结金额 单位：条
	private Integer startBlacklistFlag;  //是否启用黑名单
	private Integer sendAuditFlag;  //是否发送审核
	private Integer failToReissueFlag;   //是否失败补发
	private String[] smsGroupId;  //短信通道组
	private String smsGroupDesc ;   //短信通道组描述
	private Integer validSignMethod;   //验签方式
	private String signatureContent;   //签名内容
	private Integer bindingIpFlag;   //是否绑定IP
	private String authorizationIp;  //授信请求IP
	private String remark;  //备注
	private Integer accountStatus ;   //账户状态
	private String accountStatusDes;   //账户状态描述
	private String createDatetime;   //创建时间
	private String operator ;  //操作员
	private String accountManager ;  //客户经理
	private Integer onetimeMinLimit;   //单次提交最小限制
	private Integer onetimeMaxLimit;   //单次提交最大限制
	private Double initialValidSmscount ;   //初始可用条数
	private String contactsName ;  //联系人姓名
	private String telephone ;  //联系人电话
	private Integer billingWordsize;   //计费个数
	private Integer senseWordFlag;  //是否进行敏感字过滤
	private Integer templateMatchFlag;  //是否进行模板匹配
	private Double costQuantity ;   //扣量比
	private Integer sendNumHours;   //计费个数
	private Integer sendNumDays;   	//计费个数
	private String[] supportOperators;   //计费个数
	private String[] priority;   //计费个数
	
	
	public Integer getBillingWordsize() {
		return billingWordsize;
	}
	public void setBillingWordsize(Integer billingWordsize) {
		this.billingWordsize = billingWordsize;
	}
	public String getPaymentMethodsDes() {
		return paymentMethodsDes;
	}
	public void setPaymentMethodsDes(String paymentMethodsDes) {
		this.paymentMethodsDes = paymentMethodsDes;
	}
	public Integer getValidSignMethod() {
		return validSignMethod;
	}
	public void setValidSignMethod(Integer validSignMethod) {
		this.validSignMethod = validSignMethod;
	}
	public Long getAccountNo() {
		return accountNo;
	}
	public Integer getOnetimeMinLimit() {
		return onetimeMinLimit;
	}
	public void setOnetimeMinLimit(Integer onetimeMinLimit) {
		this.onetimeMinLimit = onetimeMinLimit;
	}
	public Integer getOnetimeMaxLimit() {
		return onetimeMaxLimit;
	}
	public void setOnetimeMaxLimit(Integer onetimeMaxLimit) {
		this.onetimeMaxLimit = onetimeMaxLimit;
	}
	public Double getInitialValidSmscount() {
		return initialValidSmscount;
	}
	public void setInitialValidSmscount(Double initialValidSmscount) {
		this.initialValidSmscount = initialValidSmscount;
	}
	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getMerchantNameAbbreviation() {
		return merchantNameAbbreviation;
	}
	public void setMerchantNameAbbreviation(String merchantNameAbbreviation) {
		this.merchantNameAbbreviation = merchantNameAbbreviation;
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
	public Integer getPaymentMethods() {
		return paymentMethods;
	}
	public void setPaymentMethods(Integer paymentMethods) {
		this.paymentMethods = paymentMethods;
	}
	public Integer getExtendNo() {
		return extendNo;
	}
	public void setExtendNo(Integer extendNo) {
		this.extendNo = extendNo;
	}
	public Integer getChargingMethods() {
		return chargingMethods;
	}
	public void setChargingMethods(Integer chargingMethods) {
		this.chargingMethods = chargingMethods;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getPriorityLevel() {
		return priorityLevel;
	}
	public void setPriorityLevel(Integer priorityLevel) {
		this.priorityLevel = priorityLevel;
	}
	public Double getTotalBalance() {
		return totalBalance;
	}
	public void setTotalBalance(Double totalBalance) {
		this.totalBalance = totalBalance;
	}
	public Double getFreeBalance() {
		return freeBalance;
	}
	public void setFreeBalance(Double freeBalance) {
		this.freeBalance = freeBalance;
	}
	public Double getFrozenBalance() {
		return frozenBalance;
	}
	public void setFrozenBalance(Double frozenBalance) {
		this.frozenBalance = frozenBalance;
	}
	public Integer getStartBlacklistFlag() {
		return startBlacklistFlag;
	}
	public void setStartBlacklistFlag(Integer startBlacklistFlag) {
		this.startBlacklistFlag = startBlacklistFlag;
	}
	public Integer getSendAuditFlag() {
		return sendAuditFlag;
	}
	public void setSendAuditFlag(Integer sendAuditFlag) {
		this.sendAuditFlag = sendAuditFlag;
	}
	public Integer getFailToReissueFlag() {
		return failToReissueFlag;
	}
	public void setFailToReissueFlag(Integer failToReissueFlag) {
		this.failToReissueFlag = failToReissueFlag;
	}
	public String getSmsGroupDesc() {
		return smsGroupDesc;
	}
	public void setSmsGroupDesc(String smsGroupDesc) {
		this.smsGroupDesc = smsGroupDesc;
	}
	public String getSignatureContent() {
		return signatureContent;
	}
	public void setSignatureContent(String signatureContent) {
		this.signatureContent = signatureContent;
	}
	public Integer getBindingIpFlag() {
		return bindingIpFlag;
	}
	public void setBindingIpFlag(Integer bindingIpFlag) {
		this.bindingIpFlag = bindingIpFlag;
	}
	public String getAuthorizationIp() {
		return authorizationIp;
	}
	public void setAuthorizationIp(String authorizationIp) {
		this.authorizationIp = authorizationIp;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getAccountStatusDes() {
		return accountStatusDes;
	}
	public void setAccountStatusDes(String accountStatusDes) {
		this.accountStatusDes = accountStatusDes;
	}
	public String getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getAccountManager() {
		return accountManager;
	}
	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}
	public String getContactsName() {
		return contactsName;
	}
	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Integer getSenseWordFlag() {
		return senseWordFlag;
	}
	public void setSenseWordFlag(Integer senseWordFlag) {
		this.senseWordFlag = senseWordFlag;
	}
	public Integer getTemplateMatchFlag() {
		return templateMatchFlag;
	}
	public void setTemplateMatchFlag(Integer templateMatchFlag) {
		this.templateMatchFlag = templateMatchFlag;
	}
	public Double getCostQuantity() {
		return costQuantity;
	}
	public void setCostQuantity(Double costQuantity) {
		this.costQuantity = costQuantity;
	}
	public Integer getSendNumHours() {
		return sendNumHours;
	}
	public void setSendNumHours(Integer sendNumHours) {
		this.sendNumHours = sendNumHours;
	}
	public Integer getSendNumDays() {
		return sendNumDays;
	}
	public void setSendNumDays(Integer sendNumDays) {
		this.sendNumDays = sendNumDays;
	}
	public String getAccountNoPass() {
		return accountNoPass;
	}
	public void setAccountNoPass(String accountNoPass) {
		this.accountNoPass = accountNoPass;
	}
	public Integer getPassSalt() {
		return passSalt;
	}
	public void setPassSalt(Integer passSalt) {
		this.passSalt = passSalt;
	}
	public String[] getSupportOperators() {
		return supportOperators;
	}
	public void setSupportOperators(String[] supportOperators) {
		this.supportOperators = supportOperators;
	}
	public String[] getSmsGroupId() {
		return smsGroupId;
	}
	public void setSmsGroupId(String[] smsGroupId) {
		this.smsGroupId = smsGroupId;
	}
	public String[] getPriority() {
		return priority;
	}
	public void setPriority(String[] priority) {
		this.priority = priority;
	}
	public String getAccountPwdClearText() {
		return accountPwdClearText;
	}
	public void setAccountPwdClearText(String accountPwdClearText) {
		this.accountPwdClearText = accountPwdClearText;
	}
}