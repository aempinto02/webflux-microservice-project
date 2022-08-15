package com.ston.spring.webflux.microservice.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ston.spring.webflux.microservice.dto.InputFailedValidationResponse;
import com.ston.spring.webflux.microservice.exception.InputValidationException;

@ControllerAdvice
public class InputValidationHandler {

	@ExceptionHandler(InputValidationException.class)
	public ResponseEntity<InputFailedValidationResponse> handleException(InputValidationException exception) {
		InputFailedValidationResponse response = new InputFailedValidationResponse();
		response.setErrorCode(InputValidationException.getERROR_CODE());
		response.setInput(exception.getInput());
		response.setMessage(exception.getMessage());

		return ResponseEntity.badRequest().body(response);
	}
}
