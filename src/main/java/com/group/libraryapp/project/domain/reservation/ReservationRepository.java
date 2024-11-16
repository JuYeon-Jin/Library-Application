package com.group.libraryapp.project.domain.reservation;

import com.group.libraryapp.project.dto.reservation.ReservedBookDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {


    /*
    - reservation 테이블에 입력받은 user_id 로 데이터 전체와 레코드의 갯수도 보되,
      동일한 book_id 가 있는 레코드 중 해당 book_id 를 가지고 있는 내 레코드보다 reserved_at 이 빠른 레코드의 갯수도 book_id 별로 반환하기

      집계 함수는 long
    * */
    @Query(value = """
        SELECT new com.group.libraryapp.project.dto.reservation.ReservedBookDTO(
            r.reservationId, r.book.bookId, b.bookName, b.publisher, b.imgPath, r.reservedAt,
            (SELECT COUNT(r2) + 1
             FROM Reservation r2
             WHERE r2.book.bookId = r.book.bookId
             AND r2.reservedAt < r.reservedAt)
        )
        FROM Reservation r
        JOIN r.book b
        WHERE r.user.userId = :inputUserId
        ORDER BY r.reservationId
    """)
    List<ReservedBookDTO> listMyReservedBooks(@Param("inputUserId") String userId);

}
