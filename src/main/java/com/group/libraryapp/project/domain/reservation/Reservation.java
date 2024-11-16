package com.group.libraryapp.project.domain.reservation;

import com.group.libraryapp.project.domain.book.Book;
import com.group.libraryapp.project.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private int reservationId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "reserved_at", nullable = false)
    private LocalDateTime reservedAt;



    /**
     * JPA 기본 생성자 (필수)
     */
    protected Reservation() { }



    /**
     * 도서 예약용 생성자입니다.
     */
    public Reservation(User user, Book book) {
        this.user = user;
        this.book = book;
        this.reservedAt = LocalDateTime.now();
    }


    /**
     * 예약일을 "yyyy-MM-dd" 형식으로 반환합니다.
     */
    public String getReservedAt() {
        return reservedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
