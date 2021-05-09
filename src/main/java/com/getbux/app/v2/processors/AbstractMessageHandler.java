package com.getbux.app.v2.processors;

import javax.websocket.Session;

import com.getbux.app.v2.enums.MsgType;

public abstract class AbstractMessageHandler {
	
	public abstract MsgType getMsgType();

	public abstract void handle(Session session, String message);
	
}
