package com.ston.spring.webflux.microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ston.spring.webflux.microservice.dto.Response;
import com.ston.spring.webflux.microservice.exception.InputValidationException;
import com.ston.spring.webflux.microservice.service.ReactiveMathService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive-math-validation")
public class ReactiveMathValidationController {

	@Autowired
	private ReactiveMathService service;

	@GetMapping("/square/{input}/throw")
	public Mono<Response> findSquare(@PathVariable int input) {
		if (input < 10 || input > 20) {
			throw new InputValidationException(input);
		}
		return this.service.findSquare(input);
	}

	@GetMapping("/square/{input}/mono-error")
	public Mono<Response> monoError(@PathVariable int input) {
		return Mono.just(input).handle((integer, sink) -> {
			if (integer >= 10 && integer <= 20) {
				sink.next(integer);
			} else {
				sink.error(new InputValidationException(integer));
			}
		}).cast(Integer.class).flatMap(i -> this.service.findSquare(i));
	}

	@GetMapping("/square/{input}/assignment")
	public Mono<ResponseEntity<Response>> assignment(@PathVariable int input) {
		return Mono.just(input).filter(i -> i >= 10 && i <= 20).flatMap(i -> this.service.findSquare(i))
				.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.badRequest().body(new Response(input)));
	}
}
