package com.getbux.app.v2.entities;

import org.glassfish.grizzly.http.util.HttpStatus;

import com.getbux.app.v2.commons.Constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

	private String message;
    private String developerMessage;
    private String errorCode;
    
    public static ErrorResponse prepare(String errorMsg, HttpStatus httpStatus) {
		return new ErrorResponse(httpStatus.getReasonPhrase(), errorMsg, Constants.ERR_PREFIX + Integer.toString(httpStatus.getStatusCode()));
	}
    
    @Override
    public String toString() {
		return "Failed to process transaction with reason: " + this.developerMessage + " and error code: "
				+ this.errorCode;
    }
}
