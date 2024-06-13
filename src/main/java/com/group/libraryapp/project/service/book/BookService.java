package com.group.libraryapp.project.service.book;

import com.group.libraryapp.project.domain.book.Book;
import com.group.libraryapp.project.domain.book.BookRepository;
import com.group.libraryapp.project.domain.bookLoanHistory.LoanHistoryRepository;
import com.group.libraryapp.project.domain.user.UserRepository;
import com.group.libraryapp.project.dto.book.BookDTO;
import org.springframework.stereotype.Service;

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

}
