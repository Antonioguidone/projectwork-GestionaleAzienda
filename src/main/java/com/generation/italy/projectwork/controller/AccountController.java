package com.generation.italy.projectwork.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.generation.italy.projectwork.auth.Utente;
import com.generation.italy.projectwork.auth.UtenteRepository;

@RestController
@RequestMapping("/accountmanager")
public class AccountController {
	
	@Autowired
	private UtenteRepository utenterep;
	
	@GetMapping
	public Iterable<Utente> getAll() {
		return utenterep.findAll();
	}

	@PutMapping("/{id}")
	public void updateRole(@PathVariable int id, @RequestParam String ruolo) {
		Optional<? extends Utente> user = utenterep.findById(id);
		
		if (user.isPresent()) {
			Utente u = user.get();
			u.setRuolo(ruolo);
			utenterep.save(u);
		}
	}
	

	
}

