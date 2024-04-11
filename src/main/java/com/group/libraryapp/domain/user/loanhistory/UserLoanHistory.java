package com.group.libraryapp.domain.user.loanhistory;

import com.group.libraryapp.domain.user.User;

import javax.persistence.*;

@Entity
public class UserLoanHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    // private long userId;
    @ManyToOne
    private User user;

    private String bookName;

    private boolean isReturn;

    // JPA 기본 생성자가 무조건 하나 필요하니까
    protected UserLoanHistory() {}

    public UserLoanHistory(User user, String bookName, boolean isReturn) {
        this.user = user;
        this.bookName = bookName;
        this.isReturn = isReturn;
        // this.isReturn = false;  이렇게 바꿔도 무방
    }

    public void doReturn() {
        this.isReturn = true;
    }

    public String getBookName() {
        return this.bookName;
    }
}
