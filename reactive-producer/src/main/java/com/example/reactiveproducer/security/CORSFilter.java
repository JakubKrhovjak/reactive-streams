package com.example.reactiveproducer.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import static com.example.reactiveproducer.security.SecurityConfiguration.JWT_AUTH_TOKEN;


/**
 *
 * @author ard333
 */
@Configuration
@EnableWebFlux
public class CORSFilter implements WebFluxConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*").exposedHeaders(JWT_AUTH_TOKEN);
	}
}