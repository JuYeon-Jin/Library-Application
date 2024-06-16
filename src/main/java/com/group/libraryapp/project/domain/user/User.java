package com.group.libraryapp.project.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// 1. @Entity 어노테이션 붙이기
// 2. 모든 컬럼 가져와서 연동시키기 (pk - jakarta.persistence)
// 3. 매개변수가 하나도 없는 기본생성자 만들기, jpa 는 기본생성자가 꼭 필요하다. (@NoArgsConstructor 대신 직접)
@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false, length = 80)   // USERNAME VARCHAR(40) NOT NULL
    private String username;

    @Column(nullable = false, length = 80)   // PASSWORD VARCHAR(40) NOT NULL
    private String password;

    private String role;

    @Column(nullable = false, name = "SIGNUP_DATE", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime signupDate;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted;

    @PrePersist
    public void prePersist() {
        if (this.signupDate == null) {
            this.signupDate = LocalDateTime.now();
        }
    }

    public String getSignupDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");    // 날짜 형식 지정
        return signupDate.format(formatter);
    }

    // JPA 기본 생성자
    protected User() { }

    // 회원가입 및 로그인 접근자
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.role = "ROLE_USER";
    }


    /*
    // JPA 기본 생성자 반드시 필요
    protected User() { }

    // ↓ 접근자 메소드
    public User(String id, String pw) {

        // 예외처리
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 형식의 id(%s) 입니다.", id));
        } else if (!krCheck(id) || lenCheck(id) < 4 || lenCheck(id) > 10) {
            throw new IllegalArgumentException(String.format("잘못된 형식의 id(%s) 입니다.", id));
        } else if (pw == null || pw.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 형식의 pw(%s) 입니다.", pw));
        } else if (!enCheck(pw) || lenCheck(pw) < 4 || lenCheck(pw) > 10) {
            throw new IllegalArgumentException(String.format("잘못된 형식의 pw(%s) 입니다.", pw));
        }

        this.id = id;
        this.pw = pw;
    }

    public String getPrivateId() {
        return privateId;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public Role getRole() {
        return role;
    }

    // ↓ 조건 충족 & 유효성 검증 메소드

    // ※ a-zA-Z0-9가-힣 (공백도 허용하지 않음)
    // 영 소문자 숫자 체크
    public boolean enCheck(String str) {
        String regex = "^[a-z0-9가-힣]+$";
        return str.matches(regex);
    }

    // 영 소문자, 숫자, 한국어 체크
    public boolean krCheck(String str) {
        String regex = "^[a-z0-9가-힣]+$";
        return str.matches(regex);
    }

    // 단순 길이 계산
    public int lenCheck(String str) {
        return str.toCharArray().length;
    }
*/
}