package com.getbux.app.v2.service.impl;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getbux.app.v2.entities.message.Subscription;
import com.getbux.app.v2.serializers.JsonSerializable;
import com.getbux.app.v2.service.ISubscriptionService;

import lombok.extern.log4j.Log4j2;

/**
 * SubscriptionService performs subscription and unsubscription transactions
 * for a given product id
 * 
 * @author arun
 *
 */
@Log4j2
@Service
public class SubscriptionService implements ISubscriptionService {

	@Autowired
	Session session;
	
	@Override
	public void subscribeTo(String productId) {
		log.debug("Subscribing to product {}", productId);
		Subscription subscription = Subscription.subscribeTo(productId);
		session.getAsyncRemote().sendText(JsonSerializable.toJson(subscription));
	}
	
	@Override
	public void unsubscribeFrom(String productId) {
		log.debug("Unsubscribing from product {}", productId);
		Subscription subscription = Subscription.unsubscribeFrom(productId);
		session.getAsyncRemote().sendText(JsonSerializable.toJson(subscription));
	}
}
