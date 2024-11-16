package com.group.libraryapp.project.domain.book;

import com.group.libraryapp.project.dto.book.BookListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    /*
    - books 테이블에서 book_id 를 기준으로 loan_history 와 join 하기.
    - loan_history 에 book_id 가 존재하고, loan_history 에서 그 book_id 레코드의 가장 최신 데이터의 is_returned 가 false 인 경우 (alias - isBorrowed)
    - reservation 테이블에 해당 book_id가 존재하고, 외부에서 입력받은 user_id와 일치하는 예약 정보가 있는지 여부 (alias - isAlreadyReserved)

    TODO nativeQuery = true

    - JPQL 에서 COALESCE 는 괄호 안에 두 값을 비교합니다.
    기존 : COALESCE(l.is_returned = false, false) AS isBorrowed
    수정 : COALESCE(l.is_returned, false) AS isBorrowed

    - JPQL 에서는 엔티티 클래스 이름을 사용
    기존 : FROM books b / b.book_id
    수정 : FROM Book b

    - JPQL 에서는 CASE 구문의 결과를 직접 BookListDTO 의 특정 필드로 매핑할 수 없으므로, 각 필드에 해당하는 값이 순서대로 정확히 전달되도록 new 구문 안에서 필드 순서에 주의해야 한다.
    * */

    @Query(value = """
        SELECT new com.group.libraryapp.project.dto.book.BookListDTO(
            b.bookId, b.bookName, b.writer, b.publisher, b.publishedAt, b.registeredAt, b.imgPath,
            CASE WHEN l.isReturned = false THEN true ELSE false END,
            CASE WHEN (COUNT(r) > 0) THEN true ELSE false END
        )
        FROM Book b
        LEFT JOIN  LoanHistory l ON b.bookId = l.book.bookId
                           AND l.borrowedAt = (SELECT MAX(l2.borrowedAt)
                                               FROM LoanHistory l2
                                               WHERE l2.book.bookId = b.bookId)
       LEFT JOIN Reservation r ON r.book.bookId = b.bookId AND r.user.userId = :inputUserId
       WHERE (:inputBookName IS NULL OR b.bookName LIKE %:inputBookName%)
       GROUP BY b.bookId, b.bookName, b.writer, b.registeredAt, l.isReturned
    """)
    List<BookListDTO> findAllWithLoanStatus(@Param("inputUserId") String userId, @Param("inputBookName") String bookName);

    // 대출 기록 많은 순으로 책 5개만 조회하기


}
