package com.generation.italy.projectwork.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.italy.projectwork.dao.IDaoAziende;
import com.generation.italy.projectwork.model.Azienda;

@RestController
@RequestMapping("/aziende")
public class ControllerAziende {

	@Autowired
	private IDaoAziende dao; 
	
	@GetMapping 
	public List<Azienda> get(){
		return dao.aziende(); 
	}
	
	
	@GetMapping("/{id}")
	public Azienda get(@PathVariable int id) {
		return dao.azienda(id); 
	} 
	
	
	
	@GetMapping("/modifica/{id}")
	public ResponseEntity<Azienda> getMod(@PathVariable int id, Principal principal) {
		if(principal != null) {
		return new ResponseEntity(dao.azienda(id), HttpStatus.OK);
		} 
			return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
		}
	
	@PostMapping
	public void add(@RequestBody Azienda azienda) {
		dao.add(azienda);}

	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable int id) {
		dao.delete(id);
	}
	
	@PutMapping
	public void update(@RequestBody Azienda azienda) {
		dao.update(azienda);
	}


}
