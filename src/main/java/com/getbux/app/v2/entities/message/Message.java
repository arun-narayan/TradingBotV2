package com.getbux.app.v2.entities.message;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6563233857886766014L;
	
	private String t;
	private String id;
    private Integer v;

}
