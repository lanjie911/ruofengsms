package com.sms.criteria;

public abstract class AbstractCriteria {
	private boolean paging;
	private int limit;
	private int offset;
    private int totalCount;
    
	public boolean isPaging() {
		return paging;
	}
	public int getLimit() {
		return limit;
	}
	public int getOffset() {
		return offset;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setPaging(boolean paging) {
		this.paging = paging;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
    
}