package com.group.libraryapp.project.service.security;

import com.group.libraryapp.project.domain.user.User;
import com.group.libraryapp.project.domain.user.UserRepository;
import com.group.libraryapp.project.dto.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 사용자 인증을 처리하는 서비스 클래스입니다.
 * Spring Security 에서 사용자 인증을 위한 'UserDetailService' 를 구현한 클래스로
 * 사용자가 로그인할 때 주어진 username 에 해당하는 사용자 정보를 데이터베이스에서 조회하고,
 * 해당 사용자 정보를 'CustomUserDetails' 형식으로 반환하여 인증을 처리합니다.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Spring Security 에서 사용자의 로그인 인증을 처리할 때,
     * username 으로 데이터베이스에서 사용자의 인증 정보를 가져오는 메서드입니다.
     * 만약 사용자가 존재하지 않으면 `UsernameNotFoundException` 예외를 던지고 이는 Spring Security 에서 로그인 실패로 처리됩니다
     *
     * @param username 사용자 이름 (로그인할 때 입력된 사용자명, 이 프로젝트에서는 id로 사용됨)
     * @return User.java(Entity)를 담은 `UserDetails(CustomUserDetails)` 객체
     * @throws UsernameNotFoundException 사용자 이름에 해당하는 사용자가 존재하지 않으면 던져지는 예외
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userData = userRepository.findByUsername(username);
        if (userData != null) {
            return new CustomUserDetails(userData);
        }

        return null;
    }
}

/*
    TODO [공부] UserDetailsService 와 Spring Security

    Spring Security 는 기본적으로 사용자 인증을 위해 **UserDetailsService**를 사용합니다.
    CustomUserDetailsService 클래스는 이 인터페이스를 구현하여 사용자가 입력한 username 에 해당하는 사용자 정보를
    데이터베이스에서 가져오는 방식으로 인증을 처리합니다.
    따라서, Spring Security 가 내부적으로 loadUserByUsername() 메서드를 호출하여 인증을 수행하기 때문에, 직접 호출할 필요는 없습니다.

    이제 로그인 로직을 따로 작성할 필요가 없습니다.
    Spring Security 는 인증을 담당하는 필수적인 기능을 자동으로 제공하므로,
    로그인 처리는 보통 **SecurityFilterChain**이나 **AuthenticationManager**를 통해 Spring Security 가 알아서 처리합니다.

    즉, Spring Security 는 다음을 자동으로 처리해 줍니다:
    - 로그인 페이지로의 리다이렉션
    - 로그인 폼 제출 후 사용자 인증
    - 인증된 사용자의 세션 관리
    - 로그인 실패 시 오류 처리

    로그인 프로세스
    - 사용자가 로그인 폼에서 **username**과 **password**를 입력하고 로그인 버튼을 클릭합니다.
    - Spring Security 는 **CustomUserDetailsService**를 통해 **username**에 해당하는 사용자를 데이터베이스에서 조회합니다.
    - loadUserByUsername() 메서드가 호출되어 해당 사용자가 존재하면, 해당 사용자의 UserDetails 객체(CustomUserDetails)를 반환합니다.
    - Spring Security 는 반환된 CustomUserDetails 객체에서 비밀번호를 검증하고, 인증이 완료되면 로그인 처리를 합니다.

* */
