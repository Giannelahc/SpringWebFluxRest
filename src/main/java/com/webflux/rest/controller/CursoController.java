package com.webflux.rest.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.webflux.rest.model.Curso;
import com.webflux.rest.service.CursoService;

import reactor.core.publisher.Mono;

@Configuration
public class CursoController {

	private static final Logger LOG = LoggerFactory.getLogger(CursoController.class);
	
	@Autowired
	private CursoService cursoService;
	
	@Bean
	public RouterFunction<?> routers(){
		return route(GET("/subjects/show"),this::show)
				.andRoute(POST("/subjects/add"), this::add)
				.andRoute(PUT("/subjects/add"), this::add)
				.andRoute(DELETE("/subjects/show/{id}"), this::delete)
				.andRoute(GET("/subjects/show/{id}"), this::showId)
				.andRoute(GET("/subjects/sho/{name}"), this::showName);
	}
	
	public Mono<ServerResponse> show(ServerRequest request){
		return ServerResponse.ok()
				.body(cursoService.findAll(),Curso.class);
	}
	
	public Mono<ServerResponse> add(ServerRequest request){
		Mono<Curso> curso = request.bodyToMono(Curso.class);
		
		return cursoService.save(curso).flatMap(f-> {
			if(f.getId() == null)
				return ServerResponse.badRequest().build();
			else
				return ServerResponse.ok()
						.body(f,Curso.class);
		});
		/*Mono<Curso> monoGet = curso.flatMap(f -> {
			return cursoService.findByName(f.getNombre()).defaultIfEmpty(new Curso());
		});
		return monoGet.flatMap(m ->{
			
			if(m.getId()==null) {
				LOG.info(m.getId());
				return ServerResponse.ok()
						.body(cursoService.save(curso),Curso.class);
			}
			else {
				return ServerResponse.badRequest().build();
			}
		});*/
		
		/*Mono<Curso> monoGet = curso.flatMap(f -> {
			return cursoService.findByName(f.getNombre());
			});

		return monoGet.flatMap(m -> ServerResponse.notFound().build())
			.switchIfEmpty(ServerResponse.ok().bodyValue("guarda"));*/
		
		/*return ServerResponse.ok()
				.body(cursoService.save(curso),Curso.class);*/
		
	}
	
	public Mono<ServerResponse> showId(ServerRequest request){
		String id =  request.pathVariable ("id");
		Mono<Curso> monoGet = cursoService.findById(id);//.defaultIfEmpty(new Curso());
		
		return monoGet.flatMap(m ->
			ServerResponse.ok().bodyValue(m))
				.switchIfEmpty(ServerResponse.notFound().build());
		/*{
			if(m.getId()==null)
				return ServerResponse.notFound().build();
			else {
				return ServerResponse.ok()
						.body(monoGet,Curso.class);
			}
		}*/
		
	}
	
	public Mono<ServerResponse> showName(ServerRequest request){
		String name =  request.pathVariable ("name");
		Mono<Curso> monoGet = cursoService.findByName(name).defaultIfEmpty(new Curso());
		
		return monoGet.flatMap(c ->{
			if(c.getId() == null) {
				return ServerResponse.notFound().build();
			}else
				return ServerResponse.ok()
						.body(monoGet, Curso.class);
		});
	}
	
	public Mono<ServerResponse> delete(ServerRequest request){
		String id =  request.pathVariable ("id");
		Mono<Curso> c = cursoService.findById(id).defaultIfEmpty(new Curso());
		
		return	c.flatMap(curso -> {
					if(curso.getId() == null) {
						return ServerResponse.notFound().build();
					}
					else {
						cursoService.delete(id).subscribe();
						return ServerResponse.ok().build();
					}
				});
	}
}
//******************************CONTROLLER********
/*@RestController
@RequestMapping("/cursos")
public class CursoController {

	private static final Logger LOG = LoggerFactory.getLogger(CursoController.class);
	
	@Autowired
	private CursoService cursoService;
	
	@GetMapping("/listar")
	public Flux<Curso> listar(){
		return cursoService.findAll().doOnNext(c ->{
			LOG.info("listando: "+c.getNombre());
		});
	}
	
	@GetMapping("/listar/{id}")
	public Mono<Curso> ver(@PathVariable("id") String id){
		
		return cursoService.findById(id)
			.defaultIfEmpty(new Curso())
			.flatMap(c -> {
				if(c.getId() == null)
					return Mono.error(new InterruptedException("No extiste el producto"));
				else {
					c.setNombre(c.getNombre().toUpperCase());
					
					return Mono.just(c);
				}
				
			}).doOnNext(c -> LOG.info("listando: "+c.getId()+" "+c.getNombre()));
	}
	
	@PostMapping("/registrar")
	public Mono<Curso> grabar(@RequestBody Mono<Curso> curso){
		return cursoService.save(curso);
	}
}*/
