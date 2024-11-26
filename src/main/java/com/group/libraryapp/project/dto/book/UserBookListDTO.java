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
public class UserBookListDTO {
    private int bookId;
    private String bookName;
    private String author;
    private String publisher;
    private String publishedAt;
    private String registeredAt;
    private boolean isBorrowed;
    private boolean amIBorrower;
    private boolean isAlreadyReserved;
    private String imgPath;
    private Long totalReservations;

    public UserBookListDTO(int bookId, String bookName, String author, String publisher, LocalDate publishedAt, LocalDateTime registeredAt, String imgPath,
                           boolean isBorrowed, boolean amIBorrower, boolean isAlreadyReserved, Long totalReservations) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.publisher = publisher;
        this.publishedAt = publishedAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.registeredAt = registeredAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.isBorrowed = isBorrowed;
        this.amIBorrower = amIBorrower;
        this.isAlreadyReserved = isAlreadyReserved;
        this.imgPath = imgPath;
        this.totalReservations = totalReservations;
    }
}
