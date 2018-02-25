package com.sms.entity.bizdict;

import java.io.Serializable;

public class Bizdict implements Serializable{

	private static final long serialVersionUID = -419892904500588864L;
	
	private Long dirId; 					//主键ID 用户在合作商户唯一标识
	private String dirCode;				//参数编码由基金公司分配 
	private String dirCodeDesc;  			//参数值
	private String dirType;					//备注
	public Long getDirId() {
		return dirId;
	}
	public void setDirId(Long dirId) {
		this.dirId = dirId;
	}
	public String getDirCode() {
		return dirCode;
	}
	public void setDirCode(String dirCode) {
		this.dirCode = dirCode;
	}
	public String getDirCodeDesc() {
		return dirCodeDesc;
	}
	public void setDirCodeDesc(String dirCodeDesc) {
		this.dirCodeDesc = dirCodeDesc;
	}
	public String getDirType() {
		return dirType;
	}
	public void setDirType(String dirType) {
		this.dirType = dirType;
	}
}