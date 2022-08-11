package com.ston.spring.webflux.microservice.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Response {
	private Date date = new Date();
	private int output;

	public Response(int output) {
		this.output = output;
	}
}
