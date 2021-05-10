package com.getbux.app.v2.processor.message;

import java.io.IOException;

import javax.websocket.Session;

import org.springframework.stereotype.Service;

import com.getbux.app.v2.enums.MsgType;
import com.getbux.app.v2.processor.AbstractMessageHandler;
import com.getbux.app.v2.processor.ResourceProcessor;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@ResourceProcessor
public class ConnectFailedMessageHandler extends AbstractMessageHandler {

	@Override
	public void handle(Session session, String message) {
		log.debug("Websocket connection failed {}. Closing session.", message);
		try {
			session.close();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Override
	public MsgType getMsgType() {
		return MsgType.CONNECT_FAILED;
	}

}
