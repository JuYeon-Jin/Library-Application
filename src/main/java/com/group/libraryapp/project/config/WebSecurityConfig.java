package com.group.libraryapp.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.HiddenHttpMethodFilter;

/**
 * 웹 애플리케이션 보안 설정을 담당하는 클래스입니다.
 * Spring Security 를 사용하여 접근 권한, 인증, 세션 관리, 비밀번호 암호화를 설정합니다.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    /*
    TODO [공부] BCryptPasswordEncoder 의 여러가지 설정 방법

    왜 빈으로 등록했을까? → 해결함. 시큐리티 때문임
    Spring Security 에서 자동으로 암호화/비교 로직을 처리할 수 있도록
    @EnableWebSecurity(Spring Security 설정을 활성화하는 클래스) 어노테이션이 붙은 클래스에 Bean 으로 등록해야 함

    ※ @EnableWebSecurity 는 WebSecurityConfigurerAdapter 또는 SecurityFilterChain 을 사용하여 보안 구성을 한다.

     * */

    /**
     * BCryptPasswordEncoder 를 빈으로 등록합니다.
     * 사용자 비밀번호를 안전하게 암호화하는 데 사용됩니다.
     *
     * @return BCryptPasswordEncoder 인스턴스
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * HiddenHttpMethodFilter 를 빈으로 등록하여
     * 이 필터는 HTTP 메서드 오버라이드를 지원하여 POST 요청에서 실제 PUT, DELETE 등의
     * HTTP 메서드를 사용할 수 있도록 처리합니다.
     *
     * @return HiddenHttpMethodFilter 인스턴스
     */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }


    /**
     * 웹 애플리케이션 보안을 설정하는 SecurityFilterChain 을 정의합니다.
     * 접근 권한, 폼 로그인, CSRF 비활성화, 세션 관리를 설정합니다.
     *
     * @param http HttpSecurity 객체로 보안 설정을 구성합니다.
     * @return SecurityFilterChain 인스턴스
     * @throws Exception 예외가 발생할 경우 던집니다.
     */
    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {

        /**
         * URL 패턴에 대해 접근 권한을 설정합니다.
         * URL 은 서버의 컨텍스트 루트를 기준으로 하는 상대경로입니다.
         * 서버 경로는 현재 프로젝트를 기준으로 http://localhost:8080"/이부분" 이며,
         * 정적 리소스라면 src/main/resources/static"/이부분" 입니다.
         */
        http
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/css/**", "/img/**", "/js/**").permitAll()
            .requestMatchers("/", "/login", "/join", "/test").permitAll()
            .requestMatchers("/authBranch").authenticated()
            .requestMatchers("/user/**").hasRole("USER")
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
        );

        /**
         * loginPage("/")는 인증이 필요한 페이지에 접근할 때 인증되지 않은 경우 자동으로 이동되는 로그인 페이지입니다.
         * loginProcessingUrl("/login")은 사용자가 이 URL 로 POST 요청을 보내면, Spring Security 가 이를 자동으로 처리하여 인증을 수행합니다.
         */
        http
            .formLogin(auth -> auth.loginPage("/")
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/authBranch", true)
            .permitAll()
        );

        /**
         * CSRF 보호를 비활성화합니다.
         */
        http.csrf(AbstractHttpConfigurer::disable);

        /**
         * 세션 고정 보호를 위해 새로운 세션을 생성합니다.
         * 세션 ID는 Spring Security 와 웹 서버가 협력하여 발급하고 클라이언트의 쿠키에 저장됩니다.
         * 세션 내의 실제 데이터(예: 사용자 정보, 인증 정보)는 서버 측에서 관리됩니다. 쿠키에 저장된 세션 ID로 Session 데이터를 찾습니다.
         */
        http.sessionManagement(auth -> auth
                .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession));

        /**
         * 사용자가 로그아웃을 할 때 세션 정보를 무효화하고 JSESSIONID 쿠키를 삭제합니다
         * 로그아웃 성공 후 추가적으로 ("/") 페이지로 리디렉션됩니다.
         */
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
        );

        return http.build();
    }
}
