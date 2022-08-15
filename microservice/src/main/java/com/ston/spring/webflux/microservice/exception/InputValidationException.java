package com.ston.spring.webflux.microservice.exception;

import lombok.Getter;

public class InputValidationException extends RuntimeException {

	private static final long serialVersionUID = 1437053779207014183L;

	private static final String MESSAGE = "Allowed rage is 10 - 20";

	@Getter
	private static final int ERROR_CODE = 100;
	@Getter
	private final int input;

	public InputValidationException(int input) {
		super(MESSAGE);
		this.input = input;
	}
}
