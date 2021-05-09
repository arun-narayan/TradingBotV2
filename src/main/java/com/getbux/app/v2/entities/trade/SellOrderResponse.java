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
public class SellOrderResponse {

	private String id;
	private String positionId;
	private Amount profitAndLoss;
    private Product product;
    private Amount investingAmount;
    private Amount price;
    private Integer leverage;
    private DirectionType direction;
    private PositionType type;
    private DateTime dateCreated;
    
	@Override
	public String toString() {
		return "Position (" + this.positionId + ") closed for product " + this.product.getSecurityId() + "" + getProfitAndLossString();
	}
    
	public String getProfitAndLossString() {
		String msg = ((0 < Float.parseFloat(this.profitAndLoss.getAmount())) ? " with a profit of "
				: (0 > Float.parseFloat(this.profitAndLoss.getAmount())) ? " with a loss of "
						: " with no profit/no loss of ");
		return msg + this.profitAndLoss.getAmount() + " " + this.profitAndLoss.getCurrency();
	}
}
