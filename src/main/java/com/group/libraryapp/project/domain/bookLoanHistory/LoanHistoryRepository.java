package com.group.libraryapp.project.domain.bookLoanHistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanHistoryRepository extends JpaRepository<LoanHistory, Integer> {
}
