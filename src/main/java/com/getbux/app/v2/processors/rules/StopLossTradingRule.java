package com.getbux.app.v2.processors.rules;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getbux.app.v2.entities.BotTradingRequest;
import com.getbux.app.v2.entities.BotTradingResponse;
import com.getbux.app.v2.entities.ErrorResponse;
import com.getbux.app.v2.entities.trade.SellOrderResponse;
import com.getbux.app.v2.processors.AbstractTradingRule;
import com.getbux.app.v2.processors.ResourceProcessor;
import com.getbux.app.v2.repositories.ProductRepository;
import com.getbux.app.v2.serializers.JsonSerializable;
import com.getbux.app.v2.service.ITradeService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@ResourceProcessor
public class StopLossTradingRule extends AbstractTradingRule {

	@Autowired
	ITradeService tradeService;
	
	@Autowired
	ProductRepository repo;

	@Override
	public boolean shouldApply(BotTradingRequest tradingRequest, BigDecimal currentPrice) {
		return tradingRequest.isBought() && 0 >= currentPrice.compareTo(tradingRequest.getLowerLimit());
	}

	@Override
	public boolean apply(BotTradingRequest tradingRequest) {
		
		boolean unsubscribe = true;
		
		if (tradingRequest.isBought()) {
			try {
				BotTradingResponse<?> response = tradeService.sellOrder(tradingRequest.getPositionId());
				
				if (response.isOk()) {
					SellOrderResponse sellOrderResponse = JsonSerializable.fromJsonObject(response.getResult(),
							SellOrderResponse.class);
					log.debug(sellOrderResponse.toString());
				} else {
					ErrorResponse errorResponse = JsonSerializable.fromJsonObject(response.getResult(),
							ErrorResponse.class);
					log.debug(errorResponse.toString());
				}
				
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				unsubscribe = false;
			} finally {
				// Reset the position id
				tradingRequest.setBought(false);
				tradingRequest.setPositionId(null);
				
				repo.save(tradingRequest);
			}

		}
		
		return unsubscribe;
	}

}
