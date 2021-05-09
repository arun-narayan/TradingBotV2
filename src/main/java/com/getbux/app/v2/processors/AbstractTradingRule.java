package com.getbux.app.v2.processors;

import java.math.BigDecimal;

import com.getbux.app.v2.entities.BotTradingRequest;

public abstract class AbstractTradingRule {
	
	/**
	 * 
	 * @param tradingRequest
	 * @param currentPrice
	 * 
	 * @return true is the rule can be applied on this trading request based on the current pricce of the product
	 */
	public abstract boolean shouldApply(BotTradingRequest tradingRequest, BigDecimal currentPrice);

	/**
	 * 
	 * @param tradingRequest
	 * 
	 * @return true if product can be unsubscribed else false 
	 */
	public abstract boolean apply(BotTradingRequest tradingRequest);
	
}
