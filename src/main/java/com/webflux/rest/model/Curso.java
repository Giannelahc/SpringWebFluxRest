package com.webflux.rest.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cursos")
public class Curso {

	@Id
	private String id;
	
	private String nombre;
	
	private long creditos;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getCreditos() {
		return creditos;
	}

	public void setCreditos(long creditos) {
		this.creditos = creditos;
	}
	
	
}
