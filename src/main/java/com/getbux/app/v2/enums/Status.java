package com.getbux.app.v2.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum Status {

	SUCCESS("Success"), 
	FAILURE("Failure");
	
	private String displayName;
}
