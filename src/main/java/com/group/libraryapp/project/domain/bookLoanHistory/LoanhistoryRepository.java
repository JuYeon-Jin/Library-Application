package com.group.libraryapp.project.domain.bookLoanHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoanhistoryRepository extends JpaRepository<Loanhistory, Integer> {
    List<Loanhistory> findByUserIdAndReturnBook(String userId, boolean returnBook);

    @Modifying
    @Query("UPDATE Loanhistory l SET l.returnBook = true WHERE l.id = :id")
    void updateReturnBookById(int id);
}
