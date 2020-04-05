package com.example.reactiveproducer.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 *
 * @author ard333
 */
public class SecurityContextRepository implements ServerSecurityContextRepository {
	
	@Autowired
	private ReactiveAuthenticationManager jwtAuthenticationManager;

	@Autowired
	private AuthUtils authUtils;

	@Override
	public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Mono<SecurityContext> load(ServerWebExchange exchange) {
		return Mono.justOrEmpty(authUtils.getAuthPayload(exchange))
			.map(auth -> authUtils.getToken(auth, AuthUtils.AuthType.BEARER))
			.flatMap(this::getSecurityContext)
			.switchIfEmpty(Mono.empty());
	}

	private Mono<SecurityContext> getSecurityContext(String token) {
		return jwtAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(token, token))
			.map(SecurityContextImpl::new);
	}
	
}
