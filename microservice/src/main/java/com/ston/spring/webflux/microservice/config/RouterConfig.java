package com.ston.spring.webflux.microservice.config;

import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.ston.spring.webflux.microservice.dto.InputFailedValidationResponse;
import com.ston.spring.webflux.microservice.exception.InputValidationException;
import com.ston.spring.webflux.microservice.service.RequestHandler;

import reactor.core.publisher.Mono;

@Configuration
public class RouterConfig {

	@Autowired
	private RequestHandler requestHandler;

	@Bean
	public RouterFunction<ServerResponse> pathRouterFunction() {
		return RouterFunctions.route().path("router", this::serverResponseRouterFunction).build();
	}

	private RouterFunction<ServerResponse> serverResponseRouterFunction() {
		return RouterFunctions.route()
				.GET("router/square/{input}", RequestPredicates.path("*/1?").or(RequestPredicates.path("*/20")),
						requestHandler::squareHandler)
				.GET("square/{input}", request -> ServerResponse.badRequest().bodyValue("Only 10-19 values allowed"))
				.GET("table/{input}", requestHandler::tableHandler)
				.GET("table/{input}/stream", requestHandler::tableStreamHandler)
				.POST("multiply", requestHandler::multiplyHandler)
				.GET("square/{input}/validation", requestHandler::squareHandlerWithValidation)
				.onError(InputValidationException.class, exceptionHandler()).build();
	}

	private BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> exceptionHandler() {
		return (error, request) -> {
			InputValidationException exception = (InputValidationException) error;
			InputFailedValidationResponse response = new InputFailedValidationResponse();
			response.setInput(exception.getInput());
			response.setMessage(exception.getMessage());
			response.setErrorCode(exception.getERROR_CODE());

			return ServerResponse.badRequest().bodyValue(response);
		};
	}
}
