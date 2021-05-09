package com.getbux.app.v2.entities;

import com.getbux.app.v2.enums.CurrencyType;

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
public class Amount {

	private CurrencyType currency;
	private Integer decimals;
	private String amount;
	
}
