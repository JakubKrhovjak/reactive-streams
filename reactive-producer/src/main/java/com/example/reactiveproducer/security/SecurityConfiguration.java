package com.example.reactiveproducer.security;

import javax.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * @author Jakub Krhovjak
 */

@EnableWebFluxSecurity
//@EnableReactiveMethodSecurity
public class SecurityConfiguration  {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter testFilter() {
        return new TestFilter();
    }

    @Bean
    public Filter logFilter() {
        return new LogFilter();
    }


    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User
            .withUsername("user")
            .password(passwordEncoder().encode("user"))
//            .password("user")
            .roles("USER")
            .build();
        return new MapReactiveUserDetailsService(user);
    }
    //


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
        ServerHttpSecurity http) {

        http.cors().and().csrf().disable();
        return http.authorizeExchange()
            .pathMatchers("/free").permitAll()
            .pathMatchers("/basic").authenticated()
            .anyExchange()
            .authenticated()


//            .pathMatchers("/basic").authenticated()
            .and()
            .build();
    }


//
//    @Override
//    public void configure(AuthenticationManagerBuilder authentication) throws Exception {
//        authentication.inMemoryAuthentication()
//            .withUser("user")
//            .password(passwordEncoder().encode("user"))
//            .roles("USER");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors().and().csrf().disable();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.authorizeRequests()
//            .antMatchers("/basic/**").hasAnyRole("USER")
//            .and().httpBasic();
//    }

}
