package com.getbux.app.v2.commons;

public class Constants {
	
	public static final String ERR_CODE_STRING = "errorCode";
	public static final String ERR_PREFIX = "CORE_";
	public static final String ERR_CONNECT_FAIL = "Could not establish connection with the trading system. Please try again later..";

	public static final String SUBSCRIPTION_MSG_PREFIX = "trading.product.";
	
	public static final String TASK_SUBMITTED = "Auto trading request submitted successfully.";
	public static final String BUY_ORDER_FAILURE = "Buy order request could not be completed";
	public static final String SELL_ORDER_FAILURE = "Sell order request could not be completed";
	
	// Precheck Validation Messages 
	public static final String BUY_PRICE_NOT_SET = "Buying price for the product {} is not set. Bot will automatically start a trade at the first available price. Lower and Upper selling price will also be automatically set with a pre-defined profit and loss margin.";
	public static final String LIMITS_NOT_SET = "Lower and Upper selling prices are not set. Bot will sell the product at a pre-defined margin.";
	public static final String UPPER_SELL_PRICE_INVALID = "Upper selling price cannot be less than the buying price";
	public static final String LOWER_SELL_PRICE_INVALID = "Lower selling price cannot be more than the buying price";
	public static final String GOT_LOWER_SELL_PRICE = "Got lower selling price as {}";
	public static final String GOT_UPPER_SELL_PRICE = "Got upper selling price as {}";
	public static final String PRODUCT_ID_INVALID = "Could not retrieve product id";
	
	public static final String REACHED_LOWER_SELL_PRICE = "Current price {} has reached the lower selling limit {}. Close position now.";
	public static final String REACHED_UPPER_SELL_PRICE = "Current price {} has reached the upper selling limit {}. Close position now.";
	public static final String REACHED_BUY_PRICE = "Current price {} has reached the buying price {}. Opening position now.";
	public static final String NOT_REACHED_BUY_PRICE = "Current price {} has not reached the buying price {} yet.";

}
