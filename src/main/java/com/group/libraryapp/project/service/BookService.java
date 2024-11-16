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

    public List<BookListDTO> listAllBooks(String userId, String bookName) {
        return book.findAllWithLoanStatus(userId, bookName);
    }


    // 도서 등록
    public void saveBook() {
        // 서버에 이미지 저장, bookId + uniqueKey + bookName
    }

/*



    // 대여 도서 리스트
    @Transactional(readOnly = true)
    public List<BookLoanDTO> loanBookList() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);

        return loanHistoryRepository.findByUserIdAndReturnBook(user.getId(), false).stream()
                .map(BookLoanDTO::new)
                .collect(Collectors.toList());
    }

    // 도서 대여
    @Transactional
    public void loanBook(int bookId) {
        // 1. 도서 정보
        Book book = bookRepository.findById(bookId);
        // orElseThrow(IllegalArgumentException::new) 설정 안하면 Optional<Book> 타입으로 바꿔줘야 한다.

        // 2. 중복 대출 예외처리
        if (book.isStatus()) {
            return;
        }

        // 3. 유저 정보
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);

        // 4. 도서 상태 변경
        bookRepository.updateStatusById(bookId, true);

        // 5. 대출 기록 저장
        Loanhistory loanData = new Loanhistory(user, book, book.getBookname());
        loanHistoryRepository.save(loanData);
    }

    // 도서 반납
    @Transactional
    public void returnBook(int loanId, int bookId) {
        // 대출 기록 returnBook → true
        loanHistoryRepository.updateReturnBookById(loanId);

        // 도서 정보 statue → false
        bookRepository.updateStatusById(bookId, false);
    }*/
}
