package com.example.demo.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.entity.Admin;
import com.example.demo.repository.AdminRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // セキュリティ設定
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/signup", "/admin/signup/**").permitAll()
                .requestMatchers("/admin/login", "/logout").permitAll()
                .requestMatchers("/admin/manage/create").hasAuthority("管理者") // 「管理者」ロールのみ作成可能
                .requestMatchers("/admin/manage/**").authenticated()
                .anyRequest().permitAll()
            )
            .formLogin(login -> login
                .loginPage("/admin/login")
                .loginProcessingUrl("/admin/login")
                .defaultSuccessUrl("/main", true)
                .failureUrl("/admin/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/admin/login?logout=true")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    // パスワードのエンコーダー（BCrypt方式）
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 認証用のUserDetailsService（AdminServiceを使わずに直接定義）
    @Bean
    UserDetailsService userDetailsService(AdminRepository adminRepository) {
        return email -> {
            Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

            // 「admin.getRole().getName()」を権限として設定（例：「管理者」「一般」）
            List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(admin.getRole().getName())
            );

            return new User(admin.getEmail(), admin.getPassword(), authorities);
        };
    }
}