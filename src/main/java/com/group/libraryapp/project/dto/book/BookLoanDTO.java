package com.group.libraryapp.project.dto.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookLoanDTO {

    private int id;
    private String userId;
    private int bookId;
    private String bookname;
    private String loanDate;
    private boolean returnBook;

    public BookLoanDTO() {}
/*
    public BookLoanDTO(Loanhistory loanhistory) {
        this.id = loanhistory.getId();
        this.bookname = loanhistory.getBookname();
        this.loanDate = loanhistory.getLoanDate();
        this.returnBook = loanhistory.isReturnBook();
    }
*/
}
