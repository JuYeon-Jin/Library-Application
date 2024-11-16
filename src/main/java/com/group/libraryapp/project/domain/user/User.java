package com.group.libraryapp.project.domain.user;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/*
    TODO JPA Entity 의 기본 규칙 (공부)
    // 1. @Entity 어노테이션 붙이기
    // 2. 모든 컬럼 가져와서 연동시키기 (pk - jakarta.persistence)
    // 3. 매개변수가 하나도 없는 기본생성자 만들기, jpa 는 기본생성자가 꼭 필요하다. (@NoArgsConstructor 대신 직접)

    JPA는 객체를 DB에서 조회할 때 기본적으로 기본 생성자(protected User())를 사용하여 객체를 생성합니다.
    생성자 이름이나 매개변수는 JPA의 동작에 영향을 주지 않으며,
    @Entity 어노테이션이 붙은 클래스는 기본 생성자가 필수입니다.
* */

/**
 * users 테이블의 데이터를 표현하고 관리하는 클래스입니다.
 */
@Entity
@Table(name = "users")
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private String userId;

    @Column(unique = true, nullable = false, length = 40)
    private String username;

    @Column(nullable = false, length = 80)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false, name = "signed_at")
    private LocalDateTime signedAt;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted;



    /**
     * JPA 기본 생성자 (필수)
     */
    protected User() { }


    /**
     * 회원가입용 생성자입니다.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = "ROLE_USER";
        this.signedAt = LocalDateTime.now();
    }


    /**
     * Date 타입의 가입일(signedAt)을 String(yyyy-MM-dd)으로 반환합니다.
     */
    public String getSignedAt() {
        return signedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}