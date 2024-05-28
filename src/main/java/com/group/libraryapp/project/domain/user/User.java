package com.group.libraryapp.project.domain.user;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

// 1. @Entity 어노테이션 붙이기
// 2. 모든 컬럼 가져와서 연동시키기 (pk - jakarta.persistence)
// 3. 매개변수가 하나도 없는 기본생성자 만들기, jpa 는 기본생성자가 꼭 필요하다.
@Entity
@NoArgsConstructor
public class User {

    @Id
    private String private_id;

    @Column(nullable = false, length = 40)   // ID VARCHAR(40) NOT NULL
    private String id;

    @Column(nullable = false, length = 40)   // PW VARCHAR(40) NOT NULL
    private String pw;

    // * GRADE 와 SIGNUP_DATE 는 생략


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
}
