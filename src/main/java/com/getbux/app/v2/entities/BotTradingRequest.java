package com.getbux.app.v2.entities;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.grizzly.http.util.HttpStatus;

import com.getbux.app.v2.commons.Constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BotTradingRequest {
	
	private String productId;
	private BigDecimal buyPrice;
	private BigDecimal lowerLimit;
	private BigDecimal upperLimit;
	private boolean bought;
	private String positionId;
	
	@Override
    public String toString() {
		return "TradingBotRequest [productId=" + productId + ", buyPrice=" + buyPrice + ", lowerLimit=" + lowerLimit
				+ ", upperLimit=" + upperLimit + "]";
    }
	
	public BotTradingResponse<?> validateTradeSettings() {

		if (StringUtils.isBlank(this.productId)) {
			return BotTradingResponse.failure(Constants.PRODUCT_ID_INVALID, HttpStatus.PRECONDITION_FAILED_412);
		}

		if (null == this.buyPrice) {
			log.info(Constants.BUY_PRICE_NOT_SET, this.productId);

		} else {
			log.debug("Got buying price as {}", this.buyPrice);

			if (null == this.lowerLimit && null == this.upperLimit) {
				log.info(Constants.LIMITS_NOT_SET);
			} else {
				if (null != this.lowerLimit && 0 > this.lowerLimit.compareTo(this.buyPrice)) {
					log.debug(Constants.GOT_LOWER_SELL_PRICE, this.lowerLimit);
				} else {
					return BotTradingResponse.failure(Constants.LOWER_SELL_PRICE_INVALID, HttpStatus.PRECONDITION_FAILED_412);
				}

				if (null != this.upperLimit && 0 < this.upperLimit.compareTo(this.buyPrice)) {
					log.debug(Constants.GOT_UPPER_SELL_PRICE, this.upperLimit);
				} else {
					return BotTradingResponse.failure(Constants.UPPER_SELL_PRICE_INVALID, HttpStatus.PRECONDITION_FAILED_412);
				}
			}

		}
		
		return BotTradingResponse.success();
	}
	
	/**
	 * Configures trading request input values based on the currentPrice if not already set
	 * 
	 * @param currentPrice
	 * @param stopLoss
	 * @param profitTarget
	 */
	public void configureTradingRequest(BigDecimal currentPrice, BigDecimal stopLoss, BigDecimal profitTarget) {

		if (null == this.buyPrice) {
			// Set buy price to the current price
			this.buyPrice = currentPrice;
		}

		if (null == this.lowerLimit) {
			// Set lower selling price
			this.lowerLimit = this.buyPrice.subtract(stopLoss);
		}

		if (null == this.upperLimit) {
			// Set upper selling price
			this.upperLimit = this.buyPrice.add(profitTarget);
		}
	}
}
