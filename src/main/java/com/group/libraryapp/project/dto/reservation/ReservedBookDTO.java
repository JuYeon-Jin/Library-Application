package com.group.libraryapp.project.dto.reservation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class ReservedBookDTO {
    private int reservationId;
    private int bookId;
    private String bookName;
    private String publisher;
    private String imgPath;
    private String reservedAt;
    private boolean isReturned;
    private Long waitingRank;

    public ReservedBookDTO(int reservationId, int bookId, String bookName, String publisher, String imgPath, LocalDateTime reservedAt, boolean isReturned, Long waitingRank) {
        this.reservationId = reservationId;
        this.bookId = bookId;
        this.bookName = bookName;
        this.publisher = publisher;
        this.imgPath = imgPath;
        this.reservedAt = reservedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.isReturned = isReturned;
        this.waitingRank = waitingRank;
    }
}
