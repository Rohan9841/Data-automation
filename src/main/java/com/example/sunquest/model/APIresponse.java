package com.example.sunquest.model;

import lombok.Data;

@Data
public class APIresponse {

	private int status;
	private String message;
	private Object result;
	
	public APIresponse(int status, String message, Object result) {
		this.status = status;
		this.message = message;
		this.result = result;
	}
}
