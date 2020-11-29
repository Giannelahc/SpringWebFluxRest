package com.webflux.rest.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webflux.rest.controller.CursoController;
import com.webflux.rest.model.Curso;
import com.webflux.rest.repository.CursoRepository;
import com.webflux.rest.service.CursoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CursoServiceImpl implements CursoService{

	private static final Logger LOG = LoggerFactory.getLogger(CursoServiceImpl.class);
	@Autowired
	CursoRepository cursoRepo;
	
	@Override
	public Mono<Curso> save(Mono<Curso> curso) {
		
		Mono<Boolean> exist = curso.flatMap(m -> {return cursoRepo.existsByNombre(m.getNombre());});
		
		return exist.flatMap(f ->{LOG.info(f.booleanValue()+"");
			if(f.booleanValue() == false)
				return cursoRepo.saveAll(curso).next();
			else
				return Mono.just(new Curso());
		});
		
		//return cursoRepo.saveAll(curso).next();
			
	}

	@Override
	public Mono<Curso> findById(String id) {
		return cursoRepo.findById(id);
	}

	@Override
	public Flux<Curso> findAll() {
		return cursoRepo.findAll();
	}

	@Override
	public Mono<Void> delete(String id) {
			return cursoRepo.deleteById(id);
	}

	@Override
	public Mono<Curso> findByName(String name) {
		return cursoRepo.findByNombre(name);
	}

	@Override
	public Mono<Boolean> existsByName(String name) {
		return cursoRepo.existsByNombre(name);
	}

}
