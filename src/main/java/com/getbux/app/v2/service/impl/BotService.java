package com.getbux.app.v2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getbux.app.v2.entities.BotTradingRequest;
import com.getbux.app.v2.repositories.ProductRepository;
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
		try {
			// Save the incoming trading request for the given product id
			repo.save(tradingRequest);

			// Subscribe to product
			subscriptionService.subscribeTo(tradingRequest.getProductId());
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
