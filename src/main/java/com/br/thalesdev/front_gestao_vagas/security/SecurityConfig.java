package com.br.thalesdev.front_gestao_vagas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(
                                                                "/home/",
                                                                "/candidate/login",
                                                                "/candidate/create",
                                                                "/candidate/signIn",
                                                                "/company/login",
                                                                "/company/create",
                                                                "/company/signIn")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/home/"));

                return http.build();
        }
}
