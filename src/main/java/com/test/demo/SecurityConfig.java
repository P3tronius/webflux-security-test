package com.test.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    private AppProperties properties;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(this::setPathMatchers)
                .authenticationManager(reactiveAuthenticationManager())
            .buiasld();
    }

    // https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/authentication/ReactiveAuthenticationManagerAdapter.html
    // https://stackoverflow.com/questions/54406998/how-to-disable-session-cookie-in-apache-httpasyncclientbuilder/54418330#54418330
    // must implement org.springframework.security.authentication.AuthenticationProvider
    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        AuthenticationManager am = new ProviderManager(Collections.singletonList(new TestingAuthenticationProvider()));
        return new ReactiveAuthenticationManagerAdapter(am);
    }


    private void setPathMatchers(ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchangeSpec) {
        properties.getRoles().forEach((pathAndRoles -> {
            authorizeExchangeSpec.pathMatchers(pathAndRoles.getPath()).hasRole(pathAndRoles.getRole());
        }));

        Arrays.stream(properties.getPublicPaths()).forEach(s ->
                authorizeExchangeSpec.pathMatchers(s).permitAll()
        );
        authorizeExchangeSpec
                .anyExchange().authenticated();
    }
}
