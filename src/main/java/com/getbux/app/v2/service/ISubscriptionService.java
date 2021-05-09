package com.getbux.app.v2.service;

public interface ISubscriptionService {

	/**
	 * Provides a mechanism to subscribe to a given product id
	 * 
	 * @param productId
	 * 
	 */
	void subscribeTo(String productId);

	/**
	 * Provides a mechanism to unsubscribe from a given product id
	 * 
	 * @param productId
	 */
	void unsubscribeFrom(String productId);

}
