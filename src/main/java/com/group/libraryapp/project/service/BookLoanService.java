package com.group.libraryapp.project.service;

import com.group.libraryapp.project.domain.loan.LoanHistoryRepository;
import com.group.libraryapp.project.dto.loan.BorrowedBookDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookLoanService {
    private final LoanHistoryRepository loan;

    public BookLoanService(LoanHistoryRepository loan) {
        this.loan = loan;
    }

    public List<BorrowedBookDTO> listAllLoanBooks(String userId) {
        return loan.listMyBorrowedBooks(userId);
    }
}
