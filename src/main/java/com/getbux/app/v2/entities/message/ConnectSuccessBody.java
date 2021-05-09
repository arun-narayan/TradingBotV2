package com.getbux.app.v2.entities.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConnectSuccessBody extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6020919718567434888L;
	
	private Body body;
	
	@Getter
    @NoArgsConstructor
    public class Body {

		private String sessionId;
		private String time;
		private Pop pop;
		private String clientVersion;
		
		@Getter
		@NoArgsConstructor
		class Pop {
			
			private String clientId;
			private String sessionId;
		}
    }
	
}
