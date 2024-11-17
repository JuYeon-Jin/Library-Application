package com.group.libraryapp.project.domain.book;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "books")
@Getter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    @Column(nullable = false, name = "book_name")
    private String bookName;

    @Column(nullable = false, length = 50)
    private String writer;

    @Column(nullable = false, length = 50)
    private String publisher;

    @Column(nullable = false, name = "img_path", length = 50)
    private String imgPath;

    @Column(nullable = false, name = "published_at")
    private LocalDate publishedAt;

    @Column(nullable = false, name = "registered_at")
    private LocalDateTime registeredAt;


    /**
     * JPA 기본 생성자 (필수)
     */
    protected Book() { }


    /**
     * 도서등록용 생성자입니다.
     * TODO [공부] 접근자와 생성자의 차이
     */
    public Book(String bookName, String writer, String publisher, String imgPath, LocalDate publishedAt) {
        this.bookName = bookName;
        this.writer = writer;
        this.publisher = publisher;
        this.publishedAt = publishedAt;
        this.imgPath = imgPath;
        this.registeredAt = LocalDateTime.now();
    }

}
