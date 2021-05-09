/**
 * Copyright 2017 (C) FixStream Networks, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.getbux.app.v2.controllers;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.getbux.app.v2.entities.BotTradingRequest;
import com.getbux.app.v2.entities.BotTradingResponse;
import com.getbux.app.v2.processors.message.ConnectMessageHandler;
import com.getbux.app.v2.service.IBotService;

import lombok.extern.log4j.Log4j2;


@Log4j2
@RestController
@RequestMapping(value = { "/api/getbux" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class BotController {

	@Autowired
	IBotService botService;
	
	@PostMapping(value = { "/trade" })
	@ResponseStatus(HttpStatus.OK)
	public BotTradingResponse<?> startTrade(HttpServletRequest request, HttpServletResponse response,
			@RequestBody(required = true) BotTradingRequest tradingRequest) throws Exception {
		
		if (!ConnectMessageHandler.isConnected()) {
			return BotTradingResponse.failure("Could not establish connection with the trading system. Please try again later..");
		}
		
		log.debug("Got Trading Request: {}", tradingRequest.toString());
		BotTradingResponse<?> botResponse = tradingRequest.validateTradeSettings();
		
		if (botResponse.isOk()) {
			// open a separate thread to process the incoming trading request and return submission status
			Callable<BotTradingResponse<?>> callable = () -> {
				log.debug("Starting Bot service...");
				botService.start(tradingRequest);
				return null;
			};
			
			Executors.newFixedThreadPool(10).submit(callable);
		}
		
		return botResponse;
	}
	
	@GetMapping(value = { "/active/trades" })
	@ResponseStatus(HttpStatus.OK)
	public BotTradingResponse<?> getActiveTrades(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return botService.getAllActiveTrades();
	}
}
