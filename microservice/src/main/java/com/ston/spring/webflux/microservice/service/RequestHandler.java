package com.ston.spring.webflux.microservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.ston.spring.webflux.microservice.dto.MultiplyRequestDTO;
import com.ston.spring.webflux.microservice.dto.Response;
import com.ston.spring.webflux.microservice.exception.InputValidationException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RequestHandler {

	@Autowired
	private ReactiveMathService mathService;

	public Mono<ServerResponse> squareHandler(ServerRequest serverRequest) {
		int input = Integer.parseInt(serverRequest.pathVariable("input"));
		Mono<Response> monoResponse = this.mathService.findSquare(input);
		return ServerResponse.ok().body(monoResponse, Response.class);
	}

	public Mono<ServerResponse> tableHandler(ServerRequest serverRequest) {
		int input = Integer.parseInt(serverRequest.pathVariable("input"));
		Flux<Response> fluxResponse = this.mathService.multiplicationTable(input);
		return ServerResponse.ok().body(fluxResponse, Response.class);
	}

	public Mono<ServerResponse> tableStreamHandler(ServerRequest serverRequest) {
		int input = Integer.parseInt(serverRequest.pathVariable("input"));
		Flux<Response> fluxResponse = this.mathService.multiplicationTable(input);
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(fluxResponse, Response.class);
	}

	public Mono<ServerResponse> multiplyHandler(ServerRequest serverRequest) {
		Mono<MultiplyRequestDTO> requestDTOMono = serverRequest.bodyToMono(MultiplyRequestDTO.class);
		Mono<Response> monoResponse = this.mathService.multiply(requestDTOMono);
		return ServerResponse.ok().body(monoResponse, Response.class);
	}

	public Mono<ServerResponse> squareHandlerWithValidation(ServerRequest serverRequest) {
		int input = Integer.parseInt(serverRequest.pathVariable("input"));
		if (input < 10 || input > 20) {
			return Mono.error(new InputValidationException(input));
		}
		Mono<Response> monoResponse = this.mathService.findSquare(input);
		return ServerResponse.ok().body(monoResponse, Response.class);
	}
}
