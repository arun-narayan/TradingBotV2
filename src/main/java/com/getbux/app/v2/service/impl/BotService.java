package com.getbux.app.v2.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getbux.app.v2.entities.BotTradingRequest;
import com.getbux.app.v2.entities.BotTradingResponse;
import com.getbux.app.v2.repository.ProductRepository;
import com.getbux.app.v2.service.IBotService;
import com.getbux.app.v2.service.ISubscriptionService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class BotService implements IBotService {
	
	@Autowired
	ISubscriptionService subscriptionService;
	
	@Autowired
	ProductRepository repo;
	
	@Override
	public void start(BotTradingRequest tradingRequest) {
		log.debug("Starting trading bot...");
		
		try {
			// Save the incoming trading request for the given product id
			repo.save(tradingRequest);

			// Subscribe to product
			subscriptionService.subscribeTo(tradingRequest.getProductId());
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Override
	public BotTradingResponse<?> getAllActiveTrades() {
		log.debug("Getting all currently active trades...");
		
		List<BotTradingRequest> activeTrades = repo.findAll();
		
		return CollectionUtils.isEmpty(activeTrades) ? BotTradingResponse.success("No active trades found.")
													 : BotTradingResponse.success(repo.findAll());
	}

}
