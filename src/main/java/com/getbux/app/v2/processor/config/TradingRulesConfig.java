package com.getbux.app.v2.processor.config;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.getbux.app.v2.processor.AbstractTradingRule;
import com.getbux.app.v2.processor.ResourceProcessor;

@Configuration
public class TradingRulesConfig {

	@Bean
	public List<AbstractTradingRule> tradingRulesMapping(
			@Autowired(required = false) @ResourceProcessor List<AbstractTradingRule> processors) {
		if (CollectionUtils.isNotEmpty(processors)) {
			return processors.stream().collect(Collectors.toList());
		}
		return Collections.emptyList();
	}
}
