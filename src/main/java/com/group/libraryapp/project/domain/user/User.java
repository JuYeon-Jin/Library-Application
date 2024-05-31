package com.group.libraryapp.project.domain.user;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// 1. @Entity 어노테이션 붙이기
// 2. 모든 컬럼 가져와서 연동시키기 (pk - jakarta.persistence)
// 3. 매개변수가 하나도 없는 기본생성자 만들기, jpa 는 기본생성자가 꼭 필요하다. (@NoArgsConstructor 대신 직접)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String private_id;

    @Column(nullable = false, length = 40)   // ID VARCHAR(40) NOT NULL
    private String id;

    @Column(nullable = false, length = 40)   // PW VARCHAR(40) NOT NULL
    private String pw;

    // @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    // private int grade;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime signup_date;

    @PrePersist
    public void prePersist() {
        if (this.signup_date == null) {
            this.signup_date = LocalDateTime.now();
        }

        // if (this.grade == null) { this.grade = 0; }
    }

    protected User() {

    }

    public User(String id, String pw) {

        // 예외처리
        /*
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(String.format("잘못된 id(%s)이 들어왔습니다.", id));
        }
        */

        this.id = id;
        this.pw = pw;
    }

    public String getId() {
        return id;
    }

    public String getSignupDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return signup_date.format(formatter);
    }

}
