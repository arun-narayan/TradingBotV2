package com.getbux.app.v2.processors.rules;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import com.getbux.app.v2.entities.Amount;
import com.getbux.app.v2.entities.BotTradingRequest;
import com.getbux.app.v2.entities.BotTradingResponse;
import com.getbux.app.v2.entities.ErrorResponse;
import com.getbux.app.v2.entities.trade.BuyOrderRequest;
import com.getbux.app.v2.entities.trade.BuyOrderResponse;
import com.getbux.app.v2.enums.DirectionType;
import com.getbux.app.v2.processors.AbstractTradingRule;
import com.getbux.app.v2.processors.ResourceProcessor;
import com.getbux.app.v2.repositories.ProductRepository;
import com.getbux.app.v2.serializers.JsonSerializable;
import com.getbux.app.v2.service.ITradeService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Primary
@Order(0)
@ResourceProcessor
public class BuyTradingRule extends AbstractTradingRule {

	@Autowired
	ITradeService tradeService;
	
	@Autowired
	ProductRepository repo;

	@Override
	public boolean shouldApply(BotTradingRequest tradingRequest, BigDecimal currentPrice) {
		return !tradingRequest.isBought() && 0 == tradingRequest.getBuyPrice().compareTo(currentPrice);
	}

	@Override
	public boolean apply(BotTradingRequest tradingRequest) {
		log.debug("Applying BuyTradingRule...");
		boolean unsubscribe = false;
		
		if (!tradingRequest.isBought()) {
			// Open position

			// Get default investing amount
			Amount amount = BuyOrderRequest.defaultAmount();
			BuyOrderRequest request =  BuyOrderRequest.builder()
									  .productId(tradingRequest.getProductId())
									  .direction(DirectionType.BUY)
									  .leverage(1)
									  .investingAmount(amount)
									  .build();

			try {
				BotTradingResponse<?> response = tradeService.buyOrder(request);

				if (response.isOk()) {
					BuyOrderResponse buyOrderResponse = JsonSerializable.fromJsonObject(response.getResult(),
							BuyOrderResponse.class);
					// Set the position id
					tradingRequest.setBought(true);
					tradingRequest.setPositionId(buyOrderResponse.getPositionId());
					log.info(buyOrderResponse.toString());
					
					// Upsert the existing trading request with updated position id
					repo.save(tradingRequest);
					
				} else {
					ErrorResponse errorResponse = JsonSerializable.fromJsonObject(response.getResult(),
							ErrorResponse.class);
					log.info(errorResponse.toString());
					unsubscribe = true;

				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		return unsubscribe;
	}

}
