package com.group.libraryapp.project.dto.book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class BookListDTO {
    private int bookId;
    private String bookName;
    private String writer;
    private String publisher;
    private String publishedAt;
    private String registeredAt;
    private boolean isBorrowed;
    private boolean isAlreadyReserved;
    private String imgPath;

    public BookListDTO(int bookId, String bookName, String writer, String publisher, LocalDate publishedAt, LocalDateTime registeredAt, String imgPath,
                       boolean isBorrowed, boolean isAlreadyReserved) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.writer = writer;
        this.publisher = publisher;
        this.publishedAt = publishedAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.registeredAt = registeredAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.isBorrowed = isBorrowed;
        this.isAlreadyReserved = isAlreadyReserved;
        this.imgPath = imgPath;
    }
}
