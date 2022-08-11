package com.ston.spring.webflux.microservice.service;

import org.springframework.stereotype.Service;

import com.ston.spring.webflux.microservice.dto.Response;
import com.ston.spring.webflux.microservice.util.SleepUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ReactiveMathService {
	public Mono<Response> findSquare(int input) {
		return Mono.fromSupplier(() -> input * input).map(Response::new);
	}

	public Flux<Response> multiplicationTable(int input) {
		return Flux.range(1, 10).doOnNext(i -> SleepUtil.sleepSeconds(1))
				.doOnNext(i -> System.out.println("react-math-service processing: " + i))
				.map(i -> new Response(i * input));
	}
}
