package com.getbux.app.v2.entities;

import org.glassfish.grizzly.http.util.HttpStatus;

import com.getbux.app.v2.commons.Constants;
import com.getbux.app.v2.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BotTradingResponse<T> {

	private T result;
	private String status;
	private boolean ok;
	private String productId;
	
	public BotTradingResponse(T result, String status, boolean ok) {
		this.result = result;
		this.status = status;
		this.ok = ok;
	}
	
	public static BotTradingResponse<String> success() {
		return success(Constants.TASK_SUBMITTED);
	}
	
	public static <T> BotTradingResponse<T> success(T data) {
		return new BotTradingResponse<T>(data, Status.SUCCESS.getDisplayName(), true);
	}
	
	/**
	 * 
	 * @param errorMsg
	 * @param httpStatus {@link HttpStatus}
	 * @return {@link BotTradingResponse}
	 */
	public static BotTradingResponse<?> failure(String errorMsg, HttpStatus httpStatus) {
		return failure(ErrorResponse.prepare(errorMsg, httpStatus));
	}

	/**
	 * 
	 * @param <T>
	 * @param error
	 * @return @return {@link BotTradingResponse}
	 */
	public static <T> BotTradingResponse<T> failure(T error) {
		return new BotTradingResponse<T>(error, Status.FAILURE.getDisplayName(), false);
	}
	
	
}
