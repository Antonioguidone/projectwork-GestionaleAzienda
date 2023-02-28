package com.generation.italy.projectwork.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.generation.italy.projectwork.auth.AuthService;



@RestController
@RequestMapping("/signup")
public class SignupController {
	@Autowired
	private AuthService authService;

	@PostMapping
	public void signup(@RequestParam String email, @RequestParam String username, @RequestParam String password, HttpServletResponse response) throws IOException {
		authService.signup(email, username, password);
		response.sendRedirect("/signups.html");
	}
}
