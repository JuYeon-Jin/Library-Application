package com.group.libraryapp.project.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository 의 인터페이스로 데이터베이스와 직접적으로 통신하여 데이터를 컨트롤합니다.
 * TODO extends 인터페이스, 상속, implements 등의 차이점 공부하기.
 */
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * 주어진 `username`에 해당하는 사용자를 조회하는 메서드입니다.
     * Spring Security 에서 사용자 인증을 위해 UserDetailsService 를 사용할 때 사용자 정보를 가져오기 위해 사용됩니다.
     * 해당 `username`을 가진 사용자를 찾아서 반환하고, 만약 사용자가 존재하지 않으면, null 을 반환합니다.
     */
    User findByUsername(String username);


    /**
     * username(이 프로젝트에서는 id) 이 이미 존재하는지 확인하는 메서드입니다.
     * 반환 값이 true 이면 해당 `username`이 이미 사용 중인 상태임을 나타냅니다.
     */
    boolean existsByUsername(String username);
}
