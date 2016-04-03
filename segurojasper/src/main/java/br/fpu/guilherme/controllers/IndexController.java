package br.fpu.guilherme.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping("/")
	public String goIndex(){
		return "home";
	}
	
	@RequestMapping("/home")
	public String goHome(){
		return "home";
	}
	
	@RequestMapping("/admin")
	public String goAdmin(){
		return "admin";
	}
	@RequestMapping("/login")
	public String goLogin() {
		return "login";
	}
}
