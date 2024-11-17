package com.group.libraryapp.project.domain.reservation;

import com.group.libraryapp.project.dto.book.BookListDTO;
import com.group.libraryapp.project.dto.loan.BorrowedBookDTO;
import com.group.libraryapp.project.dto.reservation.ReservedBookDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {


    /**
     * 주어진 사용자 ID로 예약한 도서 목록을 조회하고, 예약 대기 순서를 계산하여 함께 반환합니다.
     *
     * - 예약 대기 순서: 각 도서에 대해 해당 사용자 이전에 예약한 예약자의 수를 계산합니다.
     *                해당 사용자의 예약 순서는 첫 번째 예약은 1번, 두 번째 예약은 2번 ... 순서로 설정됩니다.
     *
     * @param userId 사용자의 ID
     * @return 사용자가 예약한 도서 목록
     */
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



    /**
     * 주어진 도서에 대해 예약된 순서대로 가장 먼저 예약한 예약을 조회합니다.
     *
     * @param bookId 예약을 조회할 도서의 고유 ID.
     * @return 주어진 도서에 대해 가장 먼저 예약된 예약 객체. 해당 도서에 예약이 없는 경우 null 을 반환합니다.
     */
    @Query("SELECT r FROM Reservation r WHERE r.book.bookId = :inputBookId ORDER BY r.reservedAt ASC")
    Reservation findFirstReservationByBookId(@Param("inputBookId") int bookId);

    /* TODO [공부] 필요한 데이터(userId, reservationId)만을 가져오기 위한 Object[] 타입
       - Reservation 객체에는 book, user, reservedAt 등등 여러 필드가 있기에 이 모든 데이터를 불러오면 불필요한 리소스를 소모할 수 있다.

        @Query("SELECT r.reservationId, r.user.userId FROM Reservation r WHERE r.book.bookId = :inputBookId ORDER BY r.reservedAt ASC")
        Object[] findFirstReservationUserIdAndReservationIdByBookId(@Param("inputBookId") int bookId);

        Object[] result = reservationRepository.findFirstReservationUserIdAndReservationIdByBookId(bookId);
        if (result != null) {
            String reservedUserId = (String) result[1];  // 첫 번째 예약자의 userId
            if (reservedUserId.equals(userId)) {
                reservationRepository.deleteById((Integer) result[0]);  // 해당 예약 삭제
            } else {
                throw new PriorReservationExistsException("선순위 예약자가 존재하여 현재 대출이 불가능합니다. 예약 순서를 확인해 주세요.");
            }
        }
    * */



    /**
     * 주어진 예약 ID와 사용자 ID에 해당하는 예약을 찾아 반환합니다.
     * 예약이 존재하고, 해당 예약이 요청한 사용자(userId)에 속한 예약인 경우에만 예약 삭제가 가능합니다.
     * 만약 예약이 존재하지 않거나 사용자가 해당 예약을 삭제할 권한이 없으면, 빈 Optional 을 반환합니다.
     *
     * @param reservationId 예약 ID
     * @param userId 예약을 삭제하려는 사용자의 고유 ID.
     * @return 예약이 존재하고 사용자가 해당 예약을 삭제할 권한이 있을 경우 예약 객체를 반환합니다.
     *         예약이 존재하지 않거나 사용자가 권한이 없는 경우 Optional.empty()를 반환합니다.
     */
    Optional<Reservation> findByIdAndUser_UserId(int reservationId, String userId);

    /* TODO [공부] Optional<Reservation> 과 Reservation 의 차이

    - Optional<Reservation>  → orElseThrow 가능, 널 포인터 예외 방지, 약간의 오버헤드
    Reservation reservation = reservationRepository.findByIdAndUser_UserId(reservationId, userId)
            .orElseThrow(() -> new RuntimeException("Reservation not found or you are not authorized to cancel it"));

    - Reservation → orElseThrow 불가능, 불필요한 객체 래핑 방지, NullPointerException 위험
    Reservation reservation = reservationRepository.findByIdAndUser_UserId(reservationId, userId);
    if (reservation == null) {
        throw new RuntimeException("Reservation not found or you are not authorized to cancel it");
    }
    * */



    /**
     * 주어진 도서 ID와 사용자 ID 로 예약여부를 확인합니다.
     * 예약이 이미 존재하면 true 를 반환하며, 그렇지 않으면 false 를 반환합니다.
     *
     * @param bookId 예약하려는 도서의 고유 ID.
     * @param userId 예약하려는 사용자의 고유 ID.
     * @return 주어진 도서에 대해 해당 사용자가 이미 예약한 경우 true 를 반환하고, 예약하지 않은 경우 false 를 반환합니다.
     */
    boolean existsByBook_BookIdAndUser_UserId(int bookId, String userId);
}
