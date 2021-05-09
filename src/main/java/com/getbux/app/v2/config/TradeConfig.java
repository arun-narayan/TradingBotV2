/**
 * Copyright 2017 (C) FixStream Networks, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.getbux.app.v2.config;

import java.math.BigDecimal;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Primary
@Configuration
@ConfigurationProperties(prefix = "bot.trade")
public class TradeConfig {

    private Float riskPerShare;
    private String profitLossRatio;
    
    public BigDecimal profitTarget() {
    	int rewardFactor = Integer.parseInt(profitLossRatio.split(":")[0]);
    	return new BigDecimal(riskPerShare * rewardFactor);
    }
    
    public BigDecimal stopLoss() {
    	int riskFactor = Integer.parseInt(profitLossRatio.split(":")[1]);
    	return new BigDecimal(riskPerShare * riskFactor);
    }
}
