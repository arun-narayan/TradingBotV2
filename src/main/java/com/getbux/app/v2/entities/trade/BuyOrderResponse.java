package com.getbux.app.v2.entities.trade;

import org.joda.time.DateTime;

import com.getbux.app.v2.entities.Amount;
import com.getbux.app.v2.entities.Product;
import com.getbux.app.v2.enums.DirectionType;
import com.getbux.app.v2.enums.PositionType;

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
public class BuyOrderResponse {

	private String id;
	private String positionId;
    private Product product;
    private Amount investingAmount;
    private Amount price;
    private Integer leverage;
    private DirectionType direction;
    private PositionType type;
    private DateTime dateCreated;
    
    @Override
    public String toString() {
		return "Position opened with id " + this.positionId + " for product " + this.product.getSecurityId()
				+ " at price = " + this.price.getAmount() + " " + this.price.getCurrency();
    }
    
    
}
