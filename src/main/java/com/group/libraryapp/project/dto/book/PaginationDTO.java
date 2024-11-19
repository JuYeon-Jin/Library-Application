package com.group.libraryapp.project.dto.book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaginationDTO {
    private int currentPage;
    private int startPage;
    private int endPage;
    private int totalPages;
    private int totalPosts;

    public PaginationDTO(int currentPage, int totalPosts, int limit) {
        this.currentPage = currentPage;
        this.totalPosts = totalPosts;

        if (this.totalPosts == 0) {

            this.totalPages = 1;
            this.startPage = 1;
            this.endPage = 1;

        } else {

            this.totalPages = (int) Math.ceil((double) totalPosts / limit);

            int blockSize = 10;
            int currentBlock = (int) Math.ceil((double) currentPage / blockSize);
            this.startPage = (currentBlock - 1) * blockSize + 1;
            this.endPage = Math.min(currentBlock * blockSize, totalPages);
        }
    }
}
