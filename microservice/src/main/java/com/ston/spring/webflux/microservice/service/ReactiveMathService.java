package com.ston.spring.webflux.microservice.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

		List<Response> response = IntStream.rangeClosed(1, 10).peek(i -> SleepUtil.sleepSeconds(1))
				.peek(i -> System.out.println("math-service processing: " + i)).mapToObj(i -> new Response(i * input))
				.collect(Collectors.toList());

		return Flux.fromIterable(response);

//		return Flux.range(1, 10).delayElements(Duration.ofSeconds(1))
//				.doOnNext(i -> SleepUtil.sleepSeconds(1))
//				.doOnNext(i -> System.out.println("react-math-service processing: " + i))
//				.map(i -> new Response(i * input));
	}
}
