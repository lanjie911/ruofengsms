package com.sms.entity.mercmanager;

import java.io.Serializable;

public class MercContacts implements Serializable{

	private static final long serialVersionUID = -1763128937543842823L;
	private Long mercContactsId;
	private Long mercInfoId;
	private String contactsName;
	private String contactAddress;
	private String zipCode;
	private String telephone;
	private String contactQq;
	private String fax;
	private String companyWebsite;
	private String contactMobile;
	private String email;
	private String accountManager;
	private String accountManagerMobile;
	
	public Long getMercContactsId() {
		return mercContactsId;
	}
	public void setMercContactsId(Long mercContactsId) {
		this.mercContactsId = mercContactsId;
	}
	public Long getMercInfoId() {
		return mercInfoId;
	}
	public void setMercInfoId(Long mercInfoId) {
		this.mercInfoId = mercInfoId;
	}
	public String getContactsName() {
		return contactsName;
	}
	public void setContactsName(String contactsName) {
		this.contactsName = contactsName;
	}
	public String getContactAddress() {
		return contactAddress;
	}
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getContactQq() {
		return contactQq;
	}
	public void setContactQq(String contactQq) {
		this.contactQq = contactQq;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getCompanyWebsite() {
		return companyWebsite;
	}
	public void setCompanyWebsite(String companyWebsite) {
		this.companyWebsite = companyWebsite;
	}
	public String getContactMobile() {
		return contactMobile;
	}
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAccountManager() {
		return accountManager;
	}
	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}
	public String getAccountManagerMobile() {
		return accountManagerMobile;
	}
	public void setAccountManagerMobile(String accountManagerMobile) {
		this.accountManagerMobile = accountManagerMobile;
	}
	@Override
	public String toString() {
		return "MercContacts [mercContactsId=" + mercContactsId + ", mercInfoId=" + mercInfoId + ", contactsName="
				+ contactsName + ", contactAddress=" + contactAddress + ", zipCode=" + zipCode + ", telephone="
				+ telephone + ", contactQq=" + contactQq + ", fax=" + fax + ", companyWebsite=" + companyWebsite
				+ ", contactMobile=" + contactMobile + ", email=" + email + ", accountManager=" + accountManager
				+ ", accountManagerMobile=" + accountManagerMobile + "]";
	}
}
