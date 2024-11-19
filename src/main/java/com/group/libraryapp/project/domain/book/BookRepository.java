package com.group.libraryapp.project.domain.book;

import com.group.libraryapp.project.dto.book.BestBookListDTO;
import com.group.libraryapp.project.dto.book.BookListDTO;
import com.group.libraryapp.project.dto.book.UserBookListDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {

    /*
    TODO [공부] JPQL 에서는 엔티티 클래스 이름을 사용 등등 문법의 차이 / nativeQuery = true
    - JPQL 에서는 CASE 구문의 결과를 직접 BookListDTO 의 특정 필드로 매핑할 수 없으므로, 각 필드에 해당하는 값이 순서대로 정확히 전달되도록 new 구문 안에서 필드 순서에 주의해야 한다.
    * */


    /**
     * 주어진 사용자 ID와 검색 키워드(도서명)을 기준으로 도서 목록을 조회하고, 각 도서의 대출 가능 여부와 예약 상태를 함께 반환합니다.
     *
     * - 대출 상태: LoanHistory 에서 bookId 를 기준으로 MAX(borrowedAt)인 레코드를 찾습니다.
     *            isReturned 가 false 라면 true 를 반환하고, true 거나 null 이라면 false 를 반환합니다.
     *            대출이 되어있는지 아닌지의 여부만 필요하기 때문에 조건에 userId 는 추가하지 않습니다.
     * - 나의 대출 상태: 사용자가 해당 도서를 대출했는지 여부를 나타냅니다. 대출했다면 `true`로 표시됩니다.
     * - 나의 예약 상태: 사용자가 해당 도서를 예약했는지 여부를 나타냅니다. 예약되었다면 `true`로 표시됩니다.
     *
     * @param userId 전체 도서 목록에서 내가 예약한 도서의 상호작용을 비활성화 하게 하기 위해 필요한 사용자의 ID.
     * @param bookName 검색할 책의 이름. 책 이름에 포함된 문자열을 기준으로 필터링합니다.
     * @return 사용자에 대한 대출 상태와 예약 상태가 포함된 도서 목록을 {@link UserBookListDTO} 형식으로 반환합니다.
     */
    @Query(value = """
        SELECT new com.group.libraryapp.project.dto.book.UserBookListDTO(
            b.bookId, b.bookName, b.author, b.publisher, b.publishedAt, b.registeredAt, b.imgPath,
            CASE WHEN l.isReturned = false THEN true ELSE false END,
            CASE WHEN l.user.userId = :inputUserId AND l.isReturned = false THEN true ELSE false END,
            CASE WHEN COUNT(CASE WHEN r.user.userId = :inputUserId THEN 1 END) > 0 THEN true ELSE false END,
            COUNT(r)
        )
        FROM Book b
        LEFT JOIN  LoanHistory l ON b.bookId = l.book.bookId
                                 AND l.borrowedAt = (SELECT MAX(l2.borrowedAt)
                                                     FROM LoanHistory l2
                                                     WHERE l2.book.bookId = b.bookId)
        LEFT JOIN Reservation r ON r.book.bookId = b.bookId 
        WHERE (:inputBookName IS NULL OR b.bookName LIKE %:inputBookName%)
        GROUP BY b.bookId, b.bookName, b.author, b.publisher, b.publishedAt, b.registeredAt, b.imgPath, l.isReturned, l.user.userId
    """)
    List<UserBookListDTO> findAllWithLoanStatus(@Param("inputUserId") String userId, @Param("inputBookName") String bookName);
    // TODO 추가로 필요한 데이터(전체 예약 건 수) JOIN 으로 가져올 수 있는게 아니라면 Reservation 테이블에서 가져오기



    /**
     * 대출 횟수가 많은 순으로 상위 5개 도서를 조회합니다.
     * 동일한 대출 횟수인 경우, 도서의 `bookId` 기준으로 오름차순 정렬됩니다.
     *
     * @return 대출 횟수가 많은 순서대로 정렬된 도서 목록을 순위를 포함하여 {@link BestBookListDTO} 형식으로 반환합니다.
     */
    @Query(value = """
    SELECT new com.group.libraryapp.project.dto.book.BestBookListDTO(
        b.bookName, b.author, b.imgPath, ROW_NUMBER() OVER (ORDER BY COUNT(l) DESC, b.bookId ASC)
    )
    FROM Book b
    LEFT JOIN  LoanHistory l ON b.bookId = l.book.bookId
    GROUP BY b.bookId, b.bookName, b.author, b.imgPath
    ORDER BY COUNT(l) DESC, b.bookId
    LIMIT 5
    """)
    List<BestBookListDTO> findTop5BooksByLoanCount();



    /**
     * 도서 목록을 DESC 순서로 조회합니다.
     * 검색 키워드가 제공되면 해당 키워드가 도서명에 포함된 도서만 조회합니다.
     * Pageable 로 제한한 데이터가 반환됩니다.
     *
     * @param keyword 검색 키워드 (선택 사항, 도서명에 키워드가 포함된 도서를 조회)
     * @param pageable 페이징 정보 (페이지 번호(Integer.parseInt(page) - 1) 및 크기(LIMIT))
     * @return 검색 결과에 해당하는 도서 목록을 {@link BookListDTO} 형식으로 반환합니다.
     */
    @Query("""
        SELECT new com.group.libraryapp.project.dto.book.BookListDTO(
            bookId, bookName, author, publisher, publishedAt, registeredAt, imgPath
        )
        FROM Book
        WHERE (:keyword IS NULL OR bookName LIKE %:keyword%)
        ORDER BY bookId DESC
    """)
    List<BookListDTO> findAllBooks(@Param("keyword") String keyword, Pageable pageable);



    /**
     * 검색 키워드에 해당하는 전체 도서의 수를 반환합니다.
     * 제공되지 않으면 전체 도서의 수를 반환합니다.
     *
     * @param keyword 검색 키워드 (선택 사항, 도서명에 키워드가 포함된 도서를 조회)
     * @return 검색 키워드에 해당하는 도서의 총 개수
     */
    @Query(value = "SELECT COUNT(*) FROM books WHERE (:keyword IS NULL OR book_name LIKE %:keyword%)", nativeQuery = true)
    int countAllBooks(@Param("keyword") String keyword);
}
