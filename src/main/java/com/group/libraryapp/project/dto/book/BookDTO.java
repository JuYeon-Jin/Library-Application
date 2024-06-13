package com.group.libraryapp.project.dto.book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {

    private String bookname;
    private String regiDate;
    private boolean status;
    private boolean deleted;

}
