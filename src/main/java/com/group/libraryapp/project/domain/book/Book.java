package com.group.libraryapp.project.domain.book;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String bookname;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean status;

    @Column(nullable = false, name = "REGI_DATE", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime regiDate;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted;

    @PrePersist
    public void prePersist() {
        if (this.regiDate == null) {
            this.regiDate = LocalDateTime.now();
        }
    }

    public String getRegiDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");    // 날짜 형식 지정
        return regiDate.format(formatter);
    }

    // JPA 기본 생성자
    protected Book() { }

    // 도서 등록 접근자
    public Book(String bookname) {
        this.bookname = bookname;
    }

}
