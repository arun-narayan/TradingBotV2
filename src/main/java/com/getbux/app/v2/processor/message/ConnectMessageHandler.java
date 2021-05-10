package com.getbux.app.v2.processor.message;

import javax.websocket.Session;

import org.springframework.stereotype.Service;

import com.getbux.app.v2.entities.message.ConnectSuccessBody;
import com.getbux.app.v2.enums.MsgType;
import com.getbux.app.v2.processor.AbstractMessageHandler;
import com.getbux.app.v2.processor.ResourceProcessor;
import com.getbux.app.v2.serializer.JsonSerializable;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@ResourceProcessor
public class ConnectMessageHandler extends AbstractMessageHandler {

	private static boolean connected;
	
	@Override
	public void handle(Session session, String message) {
		ConnectSuccessBody msg = JsonSerializable.fromJson(message, ConnectSuccessBody.class);
		log.info("Websocket connection established with session id {}", msg.getBody().getSessionId());
		connected = true;
	}
	
	public static boolean isConnected() {
		return connected;
	}
	
	@Override
	public MsgType getMsgType() {
		return MsgType.CONNECT_CONNECTED;
	}

}
