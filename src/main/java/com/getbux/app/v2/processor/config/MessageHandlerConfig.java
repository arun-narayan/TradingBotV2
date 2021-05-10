package com.getbux.app.v2.processor.config;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.getbux.app.v2.enums.MsgType;
import com.getbux.app.v2.processor.AbstractMessageHandler;
import com.getbux.app.v2.processor.ResourceProcessor;

@Configuration
public class MessageHandlerConfig {

	@Bean
    public Map<MsgType, AbstractMessageHandler>
           messageHandlerMapping(@Autowired(required = false) @ResourceProcessor List<AbstractMessageHandler> handlers) {
        if (CollectionUtils.isNotEmpty(handlers)) {
            return handlers.stream().collect(Collectors.toMap(AbstractMessageHandler::getMsgType, Function.identity()));
        }
        return Collections.emptyMap();
    }
}
