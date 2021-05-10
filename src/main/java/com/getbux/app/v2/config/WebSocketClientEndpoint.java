package com.getbux.app.v2.config;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.websocket.ClientEndpoint;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.getbux.app.v2.entities.message.Message;
import com.getbux.app.v2.enums.MsgType;
import com.getbux.app.v2.processor.AbstractMessageHandler;
import com.getbux.app.v2.serializers.JsonSerializable;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@ClientEndpoint(configurator = WebSocketClientEndpoint.SocketConfig.class)
public class WebSocketClientEndpoint {
	
	@Autowired
	private BotConfig botConfig;
	
	@Autowired
	private AuthConfig authConfig;
	
	@Autowired
	private Map<MsgType, AbstractMessageHandler> handlers;
	
	private static String authToken;
	private static String connectLanguage;
	
	@Bean
	@Primary
	public Session establishSession() throws DeploymentException, IOException, URISyntaxException {
		log.debug("Websocket Connect URL {}", botConfig.getWsConnectUrl());
		authToken = authConfig.getToken();		
		connectLanguage = authConfig.getLanguage();
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		return container.connectToServer(this, new URI(botConfig.getWsConnectUrl()));
    }
	
	public void close(Session session) {
        log.debug("Closing web socket connection.");
        try {
			session.close();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
    }
    
    @OnClose
    public void onClose(Session session, CloseReason reason) {
		log.debug("Session closed with reason: {}", StringUtils.isNotBlank(reason.getReasonPhrase()) ? reason.getReasonPhrase()
						: reason.getCloseCode().toString());
        session = null;
    }
	
	@OnMessage
	public void onMessage(Session session, String message) {
		Message msg = JsonSerializable.fromJson(message, Message.class);
		AbstractMessageHandler handler = handlers.get(MsgType.parse(msg.getT()));
		if(null != handler) {
			handler.handle(session, message);
		}
	}
	
	@OnError
    public void onError(Session session, Throwable throwable) throws IOException {
        log.error("Error occurred during websocket connection. Socket is closing", throwable);
        session.close();
    }
	
	/**
     * Websocket configuration, adds headers to websocket requests
     */
	@Configuration
	public static class SocketConfig extends ClientEndpointConfig.Configurator {
		
		@Override
        public void beforeRequest(Map<String, List<String>> headers) {
            headers.put("Authorization", Collections.singletonList(authToken));
            headers.put("Accept-Language", Collections.singletonList(connectLanguage));
        }
    }

}
