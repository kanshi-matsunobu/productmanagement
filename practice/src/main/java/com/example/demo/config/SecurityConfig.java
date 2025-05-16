package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/signup", "/admin/signup/**").permitAll()  // 管理者登録は誰でも可能
                .requestMatchers("/admin/login", "/logout").permitAll()  // ログイン画面も誰でも可能
                .anyRequest().authenticated()  // その他は認証必須
            )
            .formLogin(login -> login
                .loginPage("/admin/login")  // 正しいログインページの指定
                .defaultSuccessUrl("/main", true)  // ログイン成功時に遷移
                .failureUrl("/admin/login?error=true")  // ログイン失敗時
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")  // ログアウトURL
                .logoutSuccessUrl("/admin/login?logout=true")  // ログアウト後の遷移
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}