package com.group.libraryapp.project.service;

import com.group.libraryapp.project.domain.book.Book;
import com.group.libraryapp.project.domain.book.BookRepository;
import com.group.libraryapp.project.domain.loan.LoanHistoryRepository;
import com.group.libraryapp.project.domain.reservation.Reservation;
import com.group.libraryapp.project.domain.reservation.ReservationRepository;
import com.group.libraryapp.project.domain.user.User;
import com.group.libraryapp.project.domain.user.UserRepository;
import com.group.libraryapp.project.dto.reservation.ReservedBookDTO;
import com.group.libraryapp.project.exception.custom.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final LoanHistoryRepository loanRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, BookRepository bookRepository, LoanHistoryRepository loanRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }



    /**
     * 사용자가 예약 도서 목록을 조회하며, 사용자가 예약한 도서들의 예약 상태와 해당 도서들의 예약 순서 정보를 포함합니다.
     *
     * @param userId 사용자의 ID
     * @return 예약한 도서 목록을 담고 있는 {@link ReservedBookDTO} 객체 리스트
     */
    public List<ReservedBookDTO> listAllReservedBooks(String userId) {
        return reservationRepository.listMyReservedBooks(userId);
    }



    /**
     * 예약 조건을 만족하는 경우, 예약 처리를 수행합니다.
     * 조건은 아래와 같습니다.
     *
     * 1. 사용자가 존재하는지 확인합니다. 존재하지 않는 사용자라면 예외를 던집니다.
     * 2. 책이 존재하는지 확인합니다. 존재하지 않는 도서라면 예외를 던집니다.
     * 3. 예약이 가능한 도서인지 확인합니다. 예약이 불가능한 도서라면 예외를 던집니다.
     * 4. 사용자가 이미 해당 도서를 대출 중인지 확인합니다. 이미 대출 중이라면 예외를 던집니다.
     * 5. 사용자가 이미 해당 도서를 예약했는지 확인합니다. 이미 예약 중이라면 예외를 던집니다.
     *
     * @param userId 예약을 진행하는 사용자의 고유 아이디
     * @param bookId 예약하려는 도서의 고유 아이디
     * @throws UserNotFoundException 해당 사용자가 존재하지 않는 경우
     * @throws BookNotFoundException 해당 책이 존재하지 않는 경우
     * @throws UnavailableBorrowedException 예약이 불가능한 도서일 경우 (대출이 가능한 상태의 도서)
     * @throws UnavailableBorrowedException 사용자가 이미 대출 중인 도서일 경우
     * @throws UnavailableReservationException 사용자가 이미 예약 중인 도서일 경우
     */
    public void reservationBook(String userId, int bookId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("해당 책을 찾을 수 없습니다."));

        boolean isBookAvailable = loanRepository.isReservationProhibited(bookId);
        if (isBookAvailable) {
            throw new UnavailableBorrowedException("이 도서는 현재 대출 가능하기 때문에 예약할 수 없습니다.");
        }

        boolean alreadyBorrowed = loanRepository.existsByBook_BookIdAndUser_UserIdAndIsReturnedFalse(bookId, userId);
        if (alreadyBorrowed) {
            throw new UnavailableReservationException("이미 대출 중인 도서입니다.");
        }

        boolean alreadyReserved = reservationRepository.existsByBook_BookIdAndUser_UserId(bookId, userId);
        if (alreadyReserved) {
            throw new UnavailableReservationException("이미 예약 중인 도서입니다.");
        }

        Reservation reservation = new Reservation(user, book);
        reservationRepository.save(reservation);
    }



    /**
     * 사용자가 예약한 도서를 취소하는 메서드입니다.
     * 이 메서드는 주어진 예약 아이디와 사용자 아이디에 해당하는 예약 기록을 삭제하여 예약을 취소합니다.
     *
     * @param userId 예약 취소를 진행하는 사용자의 고유 아이디
     * @param reservationId 취소하려는 예약의 고유 아이디
     * @throws ReservationNotFoundException reservationId 와 userId로 조회한 예약이 존재하지 않는 경우
     */
    public void cancelReservation(String userId, int reservationId) {
        Reservation reservation = reservationRepository.findByIdAndUser_UserId(reservationId, userId)
                .orElseThrow(() -> new ReservationNotFoundException("예약 취소를 처리할 수 없습니다. 예약 상태를 확인하고 다시 시도해 주세요."));

        reservationRepository.delete(reservation);
    }
}
