package com.webflux.rest.service;

import com.webflux.rest.model.Curso;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CursoService {

	Mono<Curso> save(Mono<Curso> curso);
	Mono<Curso> findById(String id);
	Flux<Curso> findAll();
	Mono<Void> delete(String id);
	Mono<Curso> findByName(String name);
	Mono<Boolean> existsByName(String name);
}
