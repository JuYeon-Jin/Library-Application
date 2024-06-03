package com.group.libraryapp.project.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {

        // CSRF (Cross-Site Request Forgery, 사이트 간 요청 위조) 설정 가능
        http.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers("/", "/login/**").permitAll()
                        .requestMatchers("/posts/**", "/api/v1/posts/**").permitAll()
                        .requestMatchers("/admins/**", "/api/v1/admins/**").permitAll()
                );

        return http.build();
    }

}
