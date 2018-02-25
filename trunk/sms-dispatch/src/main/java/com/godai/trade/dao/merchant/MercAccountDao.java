package com.godai.trade.dao.merchant;

import java.util.List;

import com.godai.trade.entity.merchant.MercAccount;

public interface MercAccountDao { 
	public List<MercAccount> loadMerAccount();
}