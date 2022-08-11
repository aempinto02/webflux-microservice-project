package com.ston.spring.webflux.microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
