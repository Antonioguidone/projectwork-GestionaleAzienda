package com.generation.italy.projectwork.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.italy.projectwork.dao.IDaoPersonale;
import com.generation.italy.projectwork.model.Personale;

@RestController
@RequestMapping("/personale")
public class ControllerPersonale {

	@Autowired
	private IDaoPersonale dao; 
	
	@GetMapping
	public List<Personale> get(){
		return dao.personale();
	}
	
	@GetMapping("/{id}") 
	public Personale get(@PathVariable int id) {
		return dao.personale(id);
	}
	
	@GetMapping("/modifica/{id}")
	public ResponseEntity<Personale> getMod(@PathVariable int id, Principal principal) {
		if(principal != null) {
		return new ResponseEntity(dao.personale(id), HttpStatus.OK);
		} 
			return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
		}
		
	@GetMapping("/dipendenti/{id}") 
	public List<Personale> getPersonale(@PathVariable int id) {
		return dao.personaleAzienda(id);
	}
	
	@PostMapping 
	public void add(@RequestBody Personale personale) {
		dao.add(personale);
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable int id) {
		dao.delete(id); 
	}

	@PutMapping 
	public void update(@RequestBody Personale personale) {
//		System.out.println(personale);
		dao.update(personale);
	}

}
