package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/admin/signup", "/admin/signup/**").permitAll() // サインアップURLを許可
                                .anyRequest().permitAll() // すべてのリクエストを許可（認証不要）
                )
                .csrf(csrf -> csrf.disable()); // CSRF対策を無効化（必要に応じて）
        return http.build();
    }
}