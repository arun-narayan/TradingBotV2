package com.getbux.app.v2.entities;

import com.getbux.app.v2.enums.SourceType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Source {

	private SourceType sourceType;
	private String sourceId;
}
