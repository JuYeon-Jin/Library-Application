package com.group.libraryapp.project.dto.security;

import com.group.libraryapp.project.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Spring Security 에서 사용자 인증에 사용되는 사용자 정보를 담고 있는 클래스입니다.
 * 이 클래스는 'UserDetails' 인터페이스를 구현하며, 사용자의 인증 정보와 권한을 Spring Security 에 제공합니다.
 * 이 클래스를 통해 로그인 인증, 권한 부여 등을 처리할 수 있으며
 * 사용자에 대한 인증 정보를 Spring Security 에 제공하기 위해 `CustomUserDetailsService` 에서 반환 타입으로 사용됩니다.
 * 이 클래스는 `User` 엔티티를 기반으로 생성되며, 인증 정보는 `User` 엔티티의 데이터에서 가져옵니다.
 *
 * @see UserDetails
 * @see GrantedAuthority
 */
public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }


    // TODO [공부] 왜 COLLECTION 인지
    /**
     * 이 메서드는 사용자의 역할(role)등의 권한 정보를 Spring Security 에 제공하기 위해 `GrantedAuthority`를 반환합니다.
     *
     * @return 사용자의 권한 정보가 담긴 `Collection<GrantedAuthority>`
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });

        return collection;
    }



    /**
     * 이 메서드는 Spring Security 에서 로그인 시 입력된 비밀번호와 비교하는 데 사용하기 위해
     * DB에 저장된 비밀번호를 가져와 반환합니다.
     *
     * @return 데이터베이스에서 username 으로 조회한 User 엔티티의 비밀번호
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }



    /**
     * 이 메서드는 Spring Security 에서 로그인 시 입력된 사용자명(username)과 비교하는 데 사용하기 위해
     * DB에 저장된 사용자 이름(username)을 가져와 반환합니다.
     *
     * @return 데이터베이스에서 username 으로 조회한 User 엔티티의 사용자 이름(username)
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }



    /**
     * 이 메서드는 인증된 사용자의 고유 식별자인 user_id를 반환합니다.
     *
     * @return User 의 고유 식별자(user_id)
     */
    public String getUserId() {
        return user.getUserId();
    }



    /**
     * 사용자의 계정이 만료되지 않았는지 여부를 반환합니다.
     *
     * @return 계정 만료 여부. `true`를 반환하는 경우 계정이 유효함을 나타내며
     *         여기서는 계정이 만료될 일이 없고, 모든 사용자가 유효하다고 간주하기 위해 true 로 설정하였습니다.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }



    /**
     * 사용자의 계정이 잠기지 않았는지 여부를 반환합니다.
     *
     * @return 계정 잠금 여부. `true`를 반환하는 경우 계정이 잠기지 않았음을 나타내며
     *         여기서는 계정 잠금을 사용하지 않기 때문에 true 로 설정하였습니다.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }



    /**
     * 사용자의 자격 증명이 만료되지 않았는지 여부를 반환합니다.
     * 자격 증명은 보통 비밀번호의 유효 기간을 의미합니다
     *
     * @return 자격 증명 만료 여부. true 를 반환하면 자격 증명이 만료되지 않은 것으로 간주되며,
     *         여기서는 비밀번호 만료 여부를 사용하지 않기 때문에 true 로 설정하였습니다.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }



    /**
     * 사용자의 계정의 활성화 여부를 반환합니다.
     * 계정이 활성화되지 않은 경우, 인증할 수 없으며 계정이 활성화된 경우 `true`를 반환합니다.
     *
     * @return 계정 활성화 여부. 여기서는 사용하지 않기 때문에 true 로 설정하였습니다.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
