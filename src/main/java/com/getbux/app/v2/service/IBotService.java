package com.getbux.app.v2.service;

import com.getbux.app.v2.entities.BotTradingRequest;

public interface IBotService {

	/**
	 * 
	 * @param tradingRequest
	 */
	void start(BotTradingRequest tradingRequest);

}
