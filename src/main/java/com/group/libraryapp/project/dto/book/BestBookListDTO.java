package com.group.libraryapp.project.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BestBookListDTO {
    String bookName;
    String author;
    String imgPath;
    Long bookRank;
}
