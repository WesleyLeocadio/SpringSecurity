package br.com.wesleyDev.autenticacao.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class MySecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		  http
	        .csrf(csrf -> csrf.disable()) // Desabilita o CSRF
	        .authorizeHttpRequests(authorize -> authorize
	            .requestMatchers(HttpMethod.GET, "/free").permitAll() // Define o endpoint "/free" como acessível
	            .anyRequest().authenticated() // Exige autenticação para qualquer outra requisição
	        )
	        .cors(); // Habilita CORS
		// Adiciona o filtro personalizado MyFilter antes do filtro de autenticação UsernamePasswordAuthenticationFilter
		// Isso permite que MyFilter intercepte as requisições antes do processo de autenticação padrão
		http.addFilterBefore(new MyFilter(), UsernamePasswordAuthenticationFilter.class);

		// Finaliza a configuração do HttpSecurity e retorna a instância configurada
		// O método build() aplica todas as configurações feitas anteriormente, preparando o HttpSecurity para uso na aplicação
		return http.build();
		
	}
}
