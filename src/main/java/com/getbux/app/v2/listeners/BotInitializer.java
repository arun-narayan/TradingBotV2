/**
 * Copyright 2017 (C) FixStream Networks, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.getbux.app.v2.listeners;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.websocket.DeploymentException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.getbux.app.v2.BotClientEndpoint;
import com.getbux.app.v2.config.BotConfig;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Order(0)
@Component
public class BotInitializer implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	BotConfig config;
	
	@Autowired
	BotClientEndpoint client;
	
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
//    	log.info("Initializing client endpoint...");
//    	try {
//    		BotClientEndpoint client = new BotClientEndpoint();
//    		client.establishSession(config.getWsConnectUrl(), config.getToken());
//    		client.establishSession();
//		} catch (DeploymentException | IOException | URISyntaxException e) {
//			log.error(e.getMessage(), e);
//		}
    }
}
