package com.tutorial.fleetapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {

	@GetMapping("/")
	public String goHome() {
		
		return "user/index";
	}

	@GetMapping("/login")
	public String login() {
		return "/actions/login";
	}

	@GetMapping("/logout")
	public String logout() {
		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "/actions/register";
	}
}
