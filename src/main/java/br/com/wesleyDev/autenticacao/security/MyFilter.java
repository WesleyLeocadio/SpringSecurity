package br.com.wesleyDev.autenticacao.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyFilter extends OncePerRequestFilter {
	// Este filtro será acionado para toda requisição, devido à extensão de
	// OncePerRequestFilter

	/**
	 * O método doFilterInternal é chamado para cada requisição recebida pela
	 * aplicação. Aqui podemos adicionar qualquer lógica de filtro que precise ser
	 * executada uma vez por requisição.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("DEBUG - Requisicao passou pelo filter");

		if (request.getHeader("Authorization") != null) {
			// recupero o cabecalho
			Authentication auth = TokenUtil.decodeToken(request);
			// cabecalho de autorizacao existe, preciso ver se eh valido
			if (auth != null) {
				// se o meu token for valido, eu passo a requisicao pra frente, indicando que
				// ela esta
				SecurityContextHolder.getContext().setAuthentication(auth);
			} else {
				// token existe, mas nao eh valido - preciso customizar a minha mensagem de erro
				System.err.println("Erro de token");
				ErroDTO erro = new ErroDTO(401, "Usuario nao autorizado para este sistema.");
				response.setStatus(erro.getStatus());
				response.setContentType("application/json");
				ObjectMapper mapper = new ObjectMapper();
				response.getWriter().print(mapper.writeValueAsString(erro));
				return;
			}
		}
		// Passa a requisição e a resposta para o próximo filtro na cadeia (ou para o
		// destino final)
		filterChain.doFilter(request, response);

	}

}
