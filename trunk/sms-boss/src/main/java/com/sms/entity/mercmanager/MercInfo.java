package com.sms.entity.mercmanager;

import java.io.Serializable;

public class MercInfo implements Serializable{

	private static final long serialVersionUID = -8404937874518642392L;
	private Long merchantId;                   //商户
	private String merchantName;               //商户名称
	private String merchantNameAbbreviation;   //商户简称
	private String operatingAddress;           //经营地址
	private String createDatetime;             //创建时间
	private String updateDatetime;             //更新时间
	private String remark;                     //备注信息
	private String operator;                   //操作员
	private Integer merchantStatus;            //商户状态
	private String merchantStatusDes;          //商户状态描述
	private Integer merchantNature;            //商户性质
	private String merchantNatureDes;          //商户性质描述
	private String legalRepresentative;          //法人代表
	private MercContacts mercContacts;      //商户联系人相关信息
	private String province;        //注册地省份
	private String city;        //注册地省份
	private String area;        //注册地区县
	private String cusManager;        //客户经理
	private String cusManagerName;        //客户经理名称
	private String cusManagerMobile;        //客户经理手机号
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public MercContacts getMercContacts() {
		return mercContacts;
	}
	public void setMercContacts(MercContacts mercContacts) {
		this.mercContacts = mercContacts;
	}
	public String getLegalRepresentative() {
		return legalRepresentative;
	}
	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}
	public Integer getMerchantNature() {
		return merchantNature;
	}
	public void setMerchantNature(Integer merchantNature) {
		this.merchantNature = merchantNature;
	}
	public String getMerchantNatureDes() {
		return merchantNatureDes;
	}
	public void setMerchantNatureDes(String merchantNatureDes) {
		this.merchantNatureDes = merchantNatureDes;
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
	public String getOperatingAddress() {
		return operatingAddress;
	}
	public void setOperatingAddress(String operatingAddress) {
		this.operatingAddress = operatingAddress;
	}
	public String getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}
	public String getUpdateDatetime() {
		return updateDatetime;
	}
	public void setUpdateDatetime(String updateDatetime) {
		this.updateDatetime = updateDatetime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Integer getMerchantStatus() {
		return merchantStatus;
	}
	public void setMerchantStatus(Integer merchantStatus) {
		this.merchantStatus = merchantStatus;
	}
	public String getMerchantStatusDes() {
		return merchantStatusDes;
	}
	public void setMerchantStatusDes(String merchantStatusDes) {
		this.merchantStatusDes = merchantStatusDes;
	}
	public String getCusManager() {
		return cusManager;
	}
	public void setCusManager(String cusManager) {
		this.cusManager = cusManager;
	}
	public String getCusManagerName() {
		return cusManagerName;
	}
	public void setCusManagerName(String cusManagerName) {
		this.cusManagerName = cusManagerName;
	}
	public String getCusManagerMobile() {
		return cusManagerMobile;
	}
	public void setCusManagerMobile(String cusManagerMobile) {
		this.cusManagerMobile = cusManagerMobile;
	}
}
