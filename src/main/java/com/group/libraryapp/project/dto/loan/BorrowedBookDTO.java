package com.group.libraryapp.project.dto.loan;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class BorrowedBookDTO {
    private int loanId;
    private String bookName;
    private String publisher;
    private String imgPath;
    private String borrowedAt;
    private String dueDate;
    private long overdueDays;

    public BorrowedBookDTO(int loanId, String bookName, String publisher, String imgPath, LocalDateTime borrowedAt) {
        this.loanId = loanId;
        this.bookName = bookName;
        this.publisher = publisher;
        this.imgPath = imgPath;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.borrowedAt = borrowedAt.format(formatter);

        LocalDateTime calculatedDueDate = borrowedAt.plusDays(7);
        this.dueDate = calculatedDueDate.format(formatter);
        this.overdueDays = calculateOverdueDays(calculatedDueDate);
    }

    private long calculateOverdueDays(LocalDateTime dueDate) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(dueDate) ? Duration.between(dueDate, now).toDays() : 0;
    }
}
