package com.group.libraryapp.project.service.book;

import com.group.libraryapp.project.domain.book.Book;
import com.group.libraryapp.project.domain.book.BookRepository;
import com.group.libraryapp.project.domain.bookLoanHistory.Loanhistory;
import com.group.libraryapp.project.domain.bookLoanHistory.LoanhistoryRepository;
import com.group.libraryapp.project.domain.user.User;
import com.group.libraryapp.project.domain.user.UserRepository;
import com.group.libraryapp.project.dto.book.BookDTO;
import com.group.libraryapp.project.dto.book.BookLoanDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LoanhistoryRepository loanHistoryRepository;

    public BookService(BookRepository bookRepository, UserRepository userRepository, LoanhistoryRepository loanHistoryRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.loanHistoryRepository = loanHistoryRepository;
    }

    // 도서 등록
    @Transactional
    public void saveBook(BookDTO dto) {
        bookRepository.save(new Book(dto.getBookname()));
    }

    // 전체 도서 리스트
    @Transactional(readOnly = true)
    public List<BookDTO> bookList() {
        return  bookRepository.findByDeletedFalse().stream()
                .map(BookDTO::new)
                .collect(Collectors.toList());
    }

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
    }
}
