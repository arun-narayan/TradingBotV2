package com.getbux.app.v2.entities.message;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TradingQuoteMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4588469310172358356L;
	
	private Body body;
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Body {

    	private String securityId;
        private String currentPrice;
    }
    
    @Override
    public String toString() {
        return "TradingQuoteBody [securityId=" + body.securityId + ", currentPrice=" + body.currentPrice + "]";
    }
    
    public String productId() {
    	return this.body.securityId;
    }
    
    public BigDecimal currentPrice() {
    	return new BigDecimal(this.body.currentPrice);
    }
}
