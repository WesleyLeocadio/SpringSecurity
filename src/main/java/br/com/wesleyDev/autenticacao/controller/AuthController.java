package br.com.wesleyDev.autenticacao.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

	
	@GetMapping("/free")
	public String sayFreeHello() {
		return "Este e um endpront liberado pela nossa API";
	}
	
	@GetMapping("/auth")
	public String sayAuthHello() {
		return "Este e um endpront  precisa de autenticacao";
	}
}
