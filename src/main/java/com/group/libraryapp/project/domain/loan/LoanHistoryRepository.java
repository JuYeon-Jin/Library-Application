package com.group.libraryapp.project.domain.loan;

import com.group.libraryapp.project.dto.loan.BorrowedBookDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanHistoryRepository extends JpaRepository<LoanHistory, Integer> {


    /*
    - loan_history 테이블에 외부에서 입력받은 user_id 와 is_returned 가 false 인 데이터만 필터링해서 보기
    - 이때 users 테이블에서 username 과 books 테이블에서 book_name 과 writer 도 가져오기 위해 join 이 필요함
    * */
    @Query(value = """
        SELECT new com.group.libraryapp.project.dto.loan.BorrowedBookDTO(
            lh.loanId, b.bookName, b.publisher, b.imgPath, lh.borrowedAt
        )
        FROM LoanHistory lh
            JOIN lh.book b
        WHERE lh.user.userId = :inputUserId
          AND lh.isReturned = false
        ORDER BY lh.loanId
    """)
    List<BorrowedBookDTO> listMyBorrowedBooks(@Param("inputUserId") String userId);


    /* * @Modifying
    @Query("UPDATE Loanhistory l SET l.returnBook = true WHERE l.id = :id")
    void updateReturnBookById(int id);*/
}
