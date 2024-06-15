package com.group.libraryapp.project.dto.book;

import com.group.libraryapp.project.domain.book.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {

    private int bookId;
    private String bookname;
    private String regiDate;
    private boolean status;
    private boolean deleted;

    public BookDTO() {}

    public BookDTO(int bookId,
                   String bookname,
                   String regiDate,
                   boolean status,
                   boolean deleted) {
        this.bookId = bookId;
        this.bookname = bookname;
        this.regiDate = regiDate;
        this.status = status;
        this.deleted = deleted;
    }

    // List<Book>
    public BookDTO(Book book) {
        this.bookId = book.getId();
        this.bookname = book.getBookname();
        this.regiDate = String.valueOf(book.getRegiDate());
        this.status = book.isStatus();
        this.deleted = book.isDeleted();
    }

}
