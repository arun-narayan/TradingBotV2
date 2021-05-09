package com.getbux.app.v2.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum MsgType {

	CONNECT_CONNECTED("connect.connected"),
    CONNECT_FAILED("connect.failed"),
    TRADING_QUOTE("trading.quote"),
    UNKNOWN("");
	
	private String name;
	
	public static MsgType parse(String name) {
        for (MsgType eventType : MsgType.values()) {
            if (eventType.name.equals(name)) {
                return eventType;
            }
        }
        return UNKNOWN;
    }

}
