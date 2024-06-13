package com.group.libraryapp.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {

/*
        http
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/img/**", "/js/**").permitAll()
                .requestMatchers("/", "/login", "/join").permitAll()
                .requestMatchers("/authBranch").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/user/**").hasRole("USER")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            );
        */
        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        http
                .formLogin(auth -> auth.loginPage("/")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/authBranch", true)
                .permitAll()
        );

        http.csrf(AbstractHttpConfigurer::disable);


        http
                .sessionManagement(auth -> auth
                        .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession));

        return http.build();
    }
}
