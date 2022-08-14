package com.ston.spring.webflux.microservice.service;

import org.springframework.stereotype.Service;

import com.ston.spring.webflux.microservice.dto.MultiplyRequestDTO;
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

//		return Flux.fromIterable(IntStream.rangeClosed(1, 10).peek(i -> SleepUtil.sleepSeconds(1))
//				.peek(i -> System.out.println("reactive-math-service processing: " + i))
//				.mapToObj(i -> new Response(i * input)).collect(Collectors.toList()));

		// .delayElements(Duration.ofSeconds(1))
		return Flux.range(1, 10).doOnNext(i -> SleepUtil.sleepSeconds(1))
				.doOnNext(i -> System.out.println("react-math-service processing: " + i))
				.map(i -> new Response(i * input));
	}

	public Mono<Response> multiply(Mono<MultiplyRequestDTO> request) {
		return request.map(dto -> dto.getFirst() * dto.getSecond()).map(Response::new);
	}
}
