package com.getbux.app.v2.service.impl;

import org.glassfish.grizzly.http.util.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.getbux.app.v2.commons.Constants;
import com.getbux.app.v2.config.AuthConfig;
import com.getbux.app.v2.config.BotConfig;
import com.getbux.app.v2.entities.BotTradingResponse;
import com.getbux.app.v2.entities.ErrorResponse;
import com.getbux.app.v2.entities.trade.BuyOrderRequest;
import com.getbux.app.v2.entities.trade.BuyOrderResponse;
import com.getbux.app.v2.entities.trade.SellOrderResponse;
import com.getbux.app.v2.serializers.JsonSerializable;
import com.getbux.app.v2.serializers.JsonSerializers;
import com.getbux.app.v2.service.ITradeService;

import lombok.extern.log4j.Log4j2;

/**
 * TradeService performs the Buy and Sell order transactions
 * for a given product id
 * 
 * @author arun
 *
 */
@Log4j2
@Service
public class TradeService implements ITradeService {

	@Autowired
	private BotConfig botConfig;
	
	@Autowired
	private AuthConfig authConfig;
	
	private final RestTemplate template = new RestTemplate();

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, authConfig.getToken());
		headers.add(HttpHeaders.ACCEPT_LANGUAGE, authConfig.getLanguage());
		headers.add(HttpHeaders.CONTENT_TYPE, authConfig.getContentType());
		headers.add(HttpHeaders.ACCEPT, authConfig.getContentType());
		return headers;
	}
	
	@Override
	public BotTradingResponse<?> buyOrder(BuyOrderRequest request) {
		log.info("Opening position for product {}", request.getProductId());
		return parseResponse(botConfig.getBuyOrderUrl(), request, HttpMethod.POST, BuyOrderResponse.class);
	}
	
	@Override
	public BotTradingResponse<?> sellOrder(String positionId) {
		log.info("Closing position for positionId {}", positionId);
		return parseResponse(botConfig.sellOrderUrl(positionId), null, HttpMethod.DELETE, SellOrderResponse.class);
	}
	
	/**
	 * 
	 * @param <T>
	 * @param url
	 * @param request
	 * @param httpMethod
	 * @param classType
	 * @return {@link BotTradingResponse}
	 */
	private <T> BotTradingResponse<?> parseResponse(String url, T request, HttpMethod httpMethod, Class<?> classType) {
		log.debug("Making HTTP {} {}", httpMethod.name(), url);
		
		ErrorResponse error = new ErrorResponse(null, null, Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR_500.getStatusCode()));
		
		HttpEntity<?> httpEntity = new HttpEntity<>(request, getHeaders());
		
		try {
			
			ResponseEntity<String> entity = template.exchange(url, httpMethod, httpEntity, String.class);
			
			JsonNode root = JsonSerializers.DESERIALIZER.readTree(entity.getBody());
			if (null == root.get(Constants.ERR_CODE_STRING)) {
				// Success response
				return BotTradingResponse.success(JsonSerializable.fromJson(entity.getBody(), classType));
			} else {
				// Error response
				return BotTradingResponse.failure(JsonSerializable.fromJson(entity.getBody(), ErrorResponse.class));
			}
			
		} catch (RestClientException e) {
			log.error("RestClientException: " + e.getMessage());
			error.setDeveloperMessage(e.getMessage());
			if (e.getMessage().contains(Constants.ERR_CODE_STRING)) {
				error = JsonSerializable.fromJsonList(e.getMessage().split(" : ")[1], ErrorResponse.class).get(0);
			}
		}  catch (Exception e) {
			log.error("Exception: " + e.getMessage());
			error.setDeveloperMessage(e.getMessage());
		} 
		
		return BotTradingResponse.failure(error);
	}
}
