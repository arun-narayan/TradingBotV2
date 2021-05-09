package com.getbux.app.v2.service;

import com.getbux.app.v2.entities.BotTradingRequest;
import com.getbux.app.v2.entities.BotTradingResponse;

public interface IBotService {

	/**
	 * 
	 * @param tradingRequest
	 */
	void start(BotTradingRequest tradingRequest);

	/**
	 * 
	 * @return {@link BotTradingResponse}
	 */
	BotTradingResponse<?> getAllActiveTrades();

}
