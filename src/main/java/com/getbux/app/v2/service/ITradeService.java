package com.getbux.app.v2.service;

import com.getbux.app.v2.entities.BotTradingResponse;
import com.getbux.app.v2.entities.trade.BuyOrderRequest;

public interface ITradeService {

	/**
	 * 
	 * @param request
	 * @return {@link BotTradingResponse}
	 * 
	 * @throws Exception
	 */
	BotTradingResponse<?> buyOrder(BuyOrderRequest request) throws Exception;

	/**
	 * 
	 * @param positionId
	 * @return {@link BotTradingResponse}
	 * 
	 * @throws Exception
	 */
	BotTradingResponse<?> sellOrder(String positionId) throws Exception;

}
