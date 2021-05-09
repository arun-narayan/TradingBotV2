package com.getbux.app.v2.entities.trade;

import com.getbux.app.v2.entities.Amount;
import com.getbux.app.v2.entities.Source;
import com.getbux.app.v2.enums.CurrencyType;
import com.getbux.app.v2.enums.DirectionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuyOrderRequest {

	private String productId;
    private Amount investingAmount;
    private Integer leverage;
    private DirectionType direction;
    private Source source;
    private String riskWarningConfirmation;
    
    /**
     * Returns the default amount
     * 
     * @return {@link Amount}
     */
	public static Amount defaultAmount() {
		return Amount.builder().amount("100").currency(CurrencyType.BUX).decimals(2).build();
	}
    
}
