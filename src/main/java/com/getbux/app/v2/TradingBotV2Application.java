package com.getbux.app.v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableConfigurationProperties
@PropertySource({ "classpath:bot.properties" })
@ComponentScan({ "com.getbux" })
public class TradingBotV2Application {

	public static void main(String[] args) {
		SpringApplication.run(TradingBotV2Application.class, args);
	}

}
