package com.getbux.app.v2.entities.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConnectFailedBody extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8952530089458806570L;
	
	private Body body;
    
    @Getter
    @NoArgsConstructor
    public class Body {

    	private String developerMessage;
        private String errorCode;
    }
    
    @Override
    public String toString() {
        return "ConnectFailedBody [developerMessage=" + body.developerMessage + ", errorCode=" + body.errorCode + "]";
    }
}
