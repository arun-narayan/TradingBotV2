package com.getbux.app.v2.processors.message;

import java.util.List;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getbux.app.v2.config.TradeConfig;
import com.getbux.app.v2.entities.BotTradingRequest;
import com.getbux.app.v2.entities.message.TradingQuoteMessage;
import com.getbux.app.v2.enums.MsgType;
import com.getbux.app.v2.processors.AbstractMessageHandler;
import com.getbux.app.v2.processors.AbstractTradingRule;
import com.getbux.app.v2.processors.ResourceProcessor;
import com.getbux.app.v2.repositories.ProductRepository;
import com.getbux.app.v2.serializers.JsonSerializable;
import com.getbux.app.v2.service.ISubscriptionService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@ResourceProcessor
public class TradingQuoteMessageHandler extends AbstractMessageHandler {

	@Autowired
	ProductRepository repo;
	
	@Autowired
	TradeConfig tradeConfig;
	
	@Autowired
	List<AbstractTradingRule> tradingRules;
	
	@Autowired
	ISubscriptionService subscriptionService;
	
	@Override
	public void handle(Session session, String message) {
		log.debug("Got trading quote message: {} ", message);
		processMessage(message);
	}
	
	private void processMessage(String message) {

		TradingQuoteMessage quote = JsonSerializable.fromJson(message, TradingQuoteMessage.class);
		String currentProductId = quote.getBody().getSecurityId();

		synchronized (currentProductId) {

			try {
				// Find the current product id in in-memory repo
				BotTradingRequest tradingRequest = repo.findById(currentProductId);
				log.debug("Got trading request {} for product {} from the in-memory repo", JsonSerializable.toJson(tradingRequest), currentProductId);

				if (!currentProductId.equals(tradingRequest.getProductId())) {
					log.warn("Received quote for other product with id = {}, Skipping quote update.", currentProductId);
					return;
				}

				tradingRequest.configureTradingRequest(quote.currentPrice(), tradeConfig.stopLoss(),
						tradeConfig.profitTarget());

				log.debug("Applying trading rules...");
				tradingRules.stream().filter(rule -> rule.shouldApply(tradingRequest, quote.currentPrice()))
						.filter(rule -> rule.apply(tradingRequest))
						.forEach(rule -> {
							// Remove the product from the in-memory repo
							repo.deleteById(tradingRequest.getProductId());
							subscriptionService.unsubscribeFrom(tradingRequest.getProductId());
						});

			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

	}
	
	@Override
	public MsgType getMsgType() {
		return MsgType.TRADING_QUOTE;
	}

}
