/**
 * Copyright 2017 (C) FixStream Networks, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.getbux.app.v2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Primary
@Configuration
@ConfigurationProperties(prefix = "bot.auth")
public class AuthConfig {

    private String token;
	private String language;
	private String contentType;
    
}
