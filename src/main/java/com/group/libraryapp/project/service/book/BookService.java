package com.group.libraryapp.project.service.book;

import com.group.libraryapp.project.domain.book.Book;
import com.group.libraryapp.project.domain.book.BookRepository;
import com.group.libraryapp.project.domain.bookLoanHistory.LoanHistoryRepository;
import com.group.libraryapp.project.domain.user.UserRepository;
import com.group.libraryapp.project.dto.book.BookDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LoanHistoryRepository loanHistoryRepository;

    public BookService(BookRepository bookRepository, UserRepository userRepository, LoanHistoryRepository loanHistoryRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.loanHistoryRepository = loanHistoryRepository;
    }

    public void saveBook(BookDTO dto) {
        bookRepository.save(new Book(dto.getBookname()));
    }

    @Transactional(readOnly = true)
    public List<BookDTO> bookList() {
        return  bookRepository.findByDeletedFalse().stream()
                .map(BookDTO::new)
                .collect(Collectors.toList());
    }
}
