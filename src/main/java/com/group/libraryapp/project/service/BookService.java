package com.group.libraryapp.project.service;

import com.group.libraryapp.project.domain.book.BookRepository;
import com.group.libraryapp.project.domain.loan.LoanHistoryRepository;
import com.group.libraryapp.project.domain.reservation.ReservationRepository;
import com.group.libraryapp.project.dto.book.BookListDTO;
import com.group.libraryapp.project.dto.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository book;

    public BookService(BookRepository bookRepository) {
        this.book = bookRepository;
    }


    /**
     * 검색 조건으로 필터링 된 도서 목록을 조회하여 {@link BookListDTO} 형식으로 반환합니다.
     * 검색 조건이 null 이라면 전체 도서 목록을 반환합니다.
     *
     * @param userId  대출한 도서를 조회하려는 사용자 ID
     * @param keyword 검색을 원하는 도서명 keyword (null 일 수 있음)
     * @return 사용자가 대출한 도서 목록
     */
    public List<BookListDTO> listAllBooks(String userId, String keyword) {
        return book.findAllWithLoanStatus(userId, keyword);
    }



    // 도서 등록
    public void saveBook() {
        // 서버에 이미지 저장, bookId + bookName
    }

}
