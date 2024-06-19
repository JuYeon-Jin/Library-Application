package com.group.libraryapp.project.dto.book;

import com.group.libraryapp.project.domain.bookLoanHistory.Loanhistory;
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

    public BookLoanDTO(Loanhistory loanhistory) {
        this.id = loanhistory.getId();
        this.userId = loanhistory.getUser().getId();
        this.bookId = loanhistory.getBook().getId();
        this.bookname = loanhistory.getBookname();
        this.loanDate = loanhistory.getLoanDate();
        this.returnBook = loanhistory.isReturnBook();
    }

}
