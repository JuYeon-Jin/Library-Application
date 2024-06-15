package com.group.libraryapp.project.dto.book;

import com.group.libraryapp.project.domain.book.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookDTO {

    private int bookId;
    private String bookname;
    private String regiDate;
    private boolean status;
    private boolean deleted;

    public BookDTO(Book book) {
        this.bookId = book.getId();
        this.bookname = book.getBookname();
        this.regiDate = String.valueOf(book.getRegiDate());
        this.status = book.isStatus();
        this.deleted = book.isDeleted();
    }

}
