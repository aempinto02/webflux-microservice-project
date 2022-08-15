package com.ston.spring.webflux.microservice.dto;

import lombok.Data;

@Data
public class InputFailedValidationResponse {

	private int errorCode;
	private int input;
	private String message;
}
