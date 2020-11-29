package com.webflux.rest.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.webflux.rest.model.Curso;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CursoRepository extends ReactiveMongoRepository<Curso, String>{

	Mono<Curso> findByNombre(String name);
	Mono<Boolean> existsByNombre(String name);
}
