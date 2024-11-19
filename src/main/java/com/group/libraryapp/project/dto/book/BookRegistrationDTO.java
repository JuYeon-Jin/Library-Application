package com.group.libraryapp.project.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRegistrationDTO {
    private String bookName;
    private String author;
    private String publisher;
    private String publishedAt;
}
