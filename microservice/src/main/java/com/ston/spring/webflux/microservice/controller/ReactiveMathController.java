package com.ston.spring.webflux.microservice.controller;

import java.time.Duration;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ston.spring.webflux.microservice.dto.MultiplyRequestDTO;
import com.ston.spring.webflux.microservice.dto.Response;
import com.ston.spring.webflux.microservice.service.ReactiveMathService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive-math")
public class ReactiveMathController {

	@Autowired
	private ReactiveMathService mathService;

	@GetMapping("/square/{input}")
	public Mono<Response> findSquare(@PathVariable int input) {
		return this.mathService.findSquare(input);
	}

	@GetMapping("/table/{input}")
	public Flux<Response> multiplicationTable(@PathVariable int input) {
		return this.mathService.multiplicationTable(input);
	}

	@GetMapping(value = "/table/{input}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Response> multiplicationTableStream(@PathVariable int input) {
		return this.mathService.multiplicationTable(input);
	}

	@GetMapping(value = "/stream-test", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> streamFlux() {
		return Flux.interval(Duration.ofSeconds(2)).map(sequence -> "Flux - " + LocalTime.now().toString());
	}

	@PostMapping("/multiply")
	public Mono<Response> multiply(@RequestBody Mono<MultiplyRequestDTO> request) {
		return this.mathService.multiply(request);
	}

}
