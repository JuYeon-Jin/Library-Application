package com.group.libraryapp.project.config;

import com.group.libraryapp.project.service.security.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserAuthService userAuthService;

    public WebSecurityConfig(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {

        // CSRF (Cross-Site Request Forgery, 사이트 간 요청 위조)
        http.csrf(AbstractHttpConfigurer::disable)
                        .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/css/**", "/img/**", "/js/**").permitAll()
                        .requestMatchers("/", "/home", "/login", "/join").permitAll()
                        .requestMatchers("/view/user/**").hasRole("USER")
                        .requestMatchers("/view/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
            );

        // 로그인 페이지 설정
        http.formLogin(form -> form.loginPage("/home")
                        .loginProcessingUrl("/login") //<form action="/login" method="post">
                        .usernameParameter("id")
                        .passwordParameter("pw")
                        .permitAll()).httpBasic(AbstractHttpConfigurer::disable);


        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userAuthService)
                .passwordEncoder(new PasswordEncoder() {
                    @Override
                    public String encode(CharSequence rawPassword) {
                        return rawPassword.toString();
                    }

                    @Override
                    public boolean matches(CharSequence rawPassword, String encodedPassword) {
                        return rawPassword.toString().equals(encodedPassword);
                    }
                });
    }


}
