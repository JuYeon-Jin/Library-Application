package com.group.libraryapp.project.domain.loan;

import com.group.libraryapp.project.dto.loan.BorrowedBookDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LoanHistoryRepository extends JpaRepository<LoanHistory, Integer> {


    /**
     * 사용자가 대출한 도서 목록을 반환합니다.
     * `isReturned = false`인 대출 기록만 필터링합니다.
     *
     * @param userId 사용자의 ID
     * @return 사용자의 대출 도서 목록을 {@link BorrowedBookDTO}로 반환
     */
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



    /**
     * 대출 기록을 반납 처리합니다.
     * 주어진 `loanId`와 `userId`에 해당하는 대출 기록을 찾아, `isReturned` 상태를 `true`로 변경합니다.
     * 해당 레코드가 성공적으로 업데이트되면 1을, 해당 기록이 없거나 업데이트 실패 시 0을 반환합니다.
     *
     * @param userId 대출한 사용자의 ID
     * @param loanId 반납할 대출 기록의 ID
     * @return 업데이트된 레코드 수 (성공 시 1, 실패 시 0)
     */
    @Transactional
    @Modifying
    @Query("UPDATE LoanHistory lh SET lh.isReturned = true" +
           " WHERE lh.loanId = :loanId AND lh.user.userId = :userId AND lh.isReturned = false")
    int returnBook(@Param("userId") String userId, @Param("loanId") int loanId);

    /*
    TODO [공부] JPA 의 MODIFY
    JPA에서는 @Modifying 애너테이션을 사용하여 UPDATE, DELETE 쿼리를 실행할 때,
    쿼리 실행 자체는 실패하지 않지만 업데이트된 레코드가 없을 경우 그 결과를 확인하는 방법이 필요
    JPA에서 @Modifying 애너테이션을 사용하여 UPDATE 쿼리를 실행하면,
    쿼리가 정상적으로 실행되지만 업데이트된 레코드가 없으면 아무 영향도 주지 않는 경우가 발생할 수 있습니다.
    이런 경우 JPA는 쿼리 실행 자체는 성공으로 간주하지만, 업데이트된 레코드 수가 0일 경우 실패로 판단하고 그에 맞는 처리가 필요합니다.
    이때, @Modifying 쿼리가 성공적으로 실행되었는지, 즉 몇 개의 레코드가 업데이트되었는지를 확인할 수 있도록 반환 값을 이용하는 것입니다.
    * */



    /**
     * 특정한 도서의 대출 상태를 확인합니다.
     * `isReturned`가 `false`인 상태의 대출 기록이 존재하면, 해당 도서는 대출 중임을 의미하며
     * 해당 도서가 대출 중이면 `true`를 반환하고, 그렇지 않으면 `false`를 반환합니다.
     *
     * @param bookId 대출 중인 도서의 ID
     * @return boolean 대출 상태
     */
    boolean existsByBook_BookIdAndIsReturnedFalse(int bookId);



    /**
     * 사용자가 대출한 도서 중 반납되지 않은(`isReturned = false`) 도서의 수를 반환합니다.
     * 특정 사용자가 현재 대출 중인 도서가 몇 권인지 확인하는 데 사용합니다.
     *
     * @param userId 대출 도서 수를 확인하려는 사용자의 ID
     * @return 현재 대출 중인 도서의 수
     */
    long countByUser_UserIdAndIsReturnedFalse(String userId);



    /**
     * 다음 조건을 확인하여 특정 도서의 예약 불가 여부를 반환합니다.
     * 내부 쿼리가 결과를 반환하지 않으면 NOT EXISTS 는 true 를 반환하므로
     * ※ SELECT 1 : 특정 조건을 만족하면 레코드에 값 1 로 표시함. 레코드의 존재 여부만을 확인할 때 사용합니다.
     *
     * 1. 도서의 대출 상태 → 대출이 가능한 상태
     * 2. 도서의 예약 상태 → 해당 도서의 예약자가 없는 상태
     *
     * @param bookId 예약 금지 여부를 확인할 도서의 ID
     * @return 예약이 금지되어 있으면 `true`, 예약이 가능하면 `false`
     */
    @Query(value = """
        SELECT NOT EXISTS (
                SELECT 1
                FROM LoanHistory lh
                WHERE lh.book.bookId = :bookId
                  AND lh.isReturned = false
                  AND lh.borrowedAt = (
                      SELECT MAX(lh2.borrowedAt)
                      FROM LoanHistory lh2
                      WHERE lh2.book.bookId = :bookId
                  )
            ) AND NOT EXISTS (
                SELECT 1
                FROM Reservation r
                WHERE r.book.bookId = :bookId
            )
    """)
    boolean isReservationProhibited(@Param("bookId") int bookId);

    /*
    // TODO [공부] EXISTS 와 NOT EXISTS 의 핵심 차이

    - EXISTS:     레코드가 존재하는지 긍정적으로 확인.
                  "조건을 만족하는 데이터가 있는가?"에 초점.
    - NOT EXISTS: 레코드가 존재하지 않는지 부정적으로 확인.
                  "조건을 만족하는 데이터가 없는가?"에 초점.

    핵심은 대출 불가능 상태를 먼저 정의하고, 이를 부정하여 대출 가능성을 확인하는 방식.
        isReturned 가 false 인 경우(대출 중)
        - reservation 에 레코드가 있는 경우 - 예약 가능
        - reservation 에 레코드가 없는 경우 - 예약 가능

        isReturned 가 true 인 경우(대출 가능)
        - reservation 에 레코드가 있는 경우 - 예약 가능
        - reservation 에 레코드가 없는 경우 - ** 예약 불가능 **

      대출 중이지 **않고** & 레코드가 존재하지 **않는다**
      → NOT EXISTS (LoanHistory 가장 최신 레코드가 대출중 lh.isReturned = false)
        && NOT EXISTS (reservation 레코드가 존재)

    * */



    /**
     * 주어진 도서(`bookId`)와 사용자의 아이디(`userId`)에 대해 대출 중인 상태인지를 확인하는 메서드입니다.
     * 해당 도서가 사용자가 대출한 상태이고 반납되지 않은 (`isReturned = false`) 경우에만 `true`를 반환합니다.
     * 반환 값이 `true`일 경우, 해당 도서는 현재 대출 중인 상태로, 사용자는 해당 도서를 다시 대출하거나 예약할 수 없음을 의미합니다.
     *
     * @param bookId 대출 상태를 확인 할 도서의 ID
     * @param userId 사용자 ID
     * @return 해당 도서가 주어진 사용자가 대출 중이라면 `true`, 그렇지 않으면 `false`
     */
    boolean existsByBook_BookIdAndUser_UserIdAndIsReturnedFalse(int bookId, String userId);
}
