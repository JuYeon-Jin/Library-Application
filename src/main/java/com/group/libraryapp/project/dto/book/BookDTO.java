package com.group.libraryapp.project.dto.book;

import com.group.libraryapp.project.domain.book.Book;
import com.group.libraryapp.project.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {

    private int bookId;
    private String bookName;
    private String writer;
    private String registeredAt;

    public BookDTO() {}

    public BookDTO(int bookId,
                   String bookName,
                   String registeredAt,
                   boolean status,
                   boolean deleted) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.writer = writer;
        this.registeredAt = registeredAt;
    }

    // List<Book>
    public BookDTO(Book book) {
        this.bookId = book.getBookId();
        this.bookName = book.getBookName();
        this.writer = book.getWriter();
        this.registeredAt = String.valueOf(book.getRegisteredAt());
    }

}
