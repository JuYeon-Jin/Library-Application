package com.group.libraryapp.project.domain.bookLoanHistory;

import com.group.libraryapp.project.domain.book.Book;
import com.group.libraryapp.project.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
public class Loanhistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID", nullable = false)
    private Book book;

    @Column(nullable = false)
    private String bookname;

    @Column(name = "LOAN_DATE", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime loanDate;

    @Column(name = "RETURN_BOOK", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean returnBook;

    // 기본 생성자
    protected Loanhistory() {}

    public Loanhistory(User user, Book book, String bookname) {
        this.user = user;
        this.book = book;
        this.bookname = bookname;
    }

    @PrePersist
    public void prePersist() {
        if (this.loanDate == null) {
            this.loanDate = LocalDateTime.now();
        }
    }

    public String getLoanDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");    // 날짜 형식 지정
        return loanDate.format(formatter);
    }

}
