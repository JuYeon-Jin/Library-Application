package com.group.libraryapp.project.service;

import com.group.libraryapp.project.domain.book.Book;
import com.group.libraryapp.project.domain.book.BookRepository;
import com.group.libraryapp.project.domain.loan.LoanHistory;
import com.group.libraryapp.project.domain.loan.LoanHistoryRepository;
import com.group.libraryapp.project.domain.reservation.Reservation;
import com.group.libraryapp.project.domain.reservation.ReservationRepository;
import com.group.libraryapp.project.domain.user.User;
import com.group.libraryapp.project.domain.user.UserRepository;
import com.group.libraryapp.project.dto.loan.BorrowedBookDTO;
import com.group.libraryapp.project.exception.custom.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookLoanService {

    private final LoanHistoryRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReservationRepository reservationRepository;

    public BookLoanService(LoanHistoryRepository loanRepository, UserRepository userRepository, BookRepository bookRepository, ReservationRepository reservationRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.reservationRepository = reservationRepository;
    }


    /**
     * 사용자가 대출한 도서 목록을 조회하여 {@link BorrowedBookDTO} 형식으로 반환합니다.
     *
     * @param userId 대출한 도서를 조회하려는 사용자 ID
     * @return 사용자가 대출한 도서 목록
     * @throws UserNotFoundException 해당 사용자가 존재하지 않는 경우
     */
    public List<BorrowedBookDTO> listAllLoanBooks(String userId) {
        return loanRepository.listMyBorrowedBooks(userId);
    }


    /**
     * 대출 조건울 만족하는 경우, 대출 처리를 수행합니다.
     * 선순위 예약자인 경우라면 예약을 취소하고 자동으로 대출을 진행합니다.
     * 조건은 아래와 같습니다.
     *
     * 1. 사용자가 존재하는지 확인합니다. 존재하지 않는 사용자라면 예외를 던집니다.
     * 2. 책이 존재하는지 확인합니다. 존재하지 않는 도서라면 예외를 던집니다.
     * 3. 책이 이미 대출 중인지 확인합니다. 이미 대출중이라면 isAlreadyLoaned 이 true 를 반환하고 예외를 던집니다.
     * 4. 예약자가 있는 경우, 사용자가 선순위 예약자인지 확인합니다. 아니라면 예외를 던집니다.
     *
     * @param userId 대출을 요청한 사용자 ID
     * @param bookId 대출하려는 책의 ID
     * @throws UserNotFoundException 해당 사용자가 존재하지 않는 경우
     * @throws BookNotFoundException 해당 책이 존재하지 않는 경우
     * @throws PriorReservationExistsException 이미 대출 중인 책이거나, 선순위 예약자가 존재하는 경우
     */
    public void borrowBook(String userId, int bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("해당 책을 찾을 수 없습니다."));

        boolean isAlreadyLoaned = loanRepository.existsByBook_BookIdAndIsReturnedFalse(bookId);
        if (isAlreadyLoaned) {
            throw new PriorReservationExistsException("이 책은 이미 대출 중입니다.");
        }

        Reservation reservation = reservationRepository.findFirstReservationByBookId(bookId);
        if (reservation != null) {
            if (reservation.getUser().getUserId().equals(userId)) {
                reservationRepository.delete(reservation);
            } else {
                throw new PriorReservationExistsException("선순위 예약자가 존재하여 현재 대출이 불가능합니다. 예약 순서를 확인해 주세요.");
            }
        }

        LoanHistory loanHistory = new LoanHistory(user, book);
        loanRepository.save(loanHistory);
    }


    /**
     * 주어진 대출 ID(loanId)를 통해 대출 기록을 찾아 반납을 처리합니다.
     * 반납이 성공하면 대출 상태가 업데이트 되고 업데이트 된 레코드 갯수를 반환합니다. 이 경우 성공적이라면 1이 반환됩니다.
     * 만약 해당 조건에 맞는 대출 기록을 찾지 못하거나 반납할 수 없는 경우 0을 반환하고 예외를 던집니다.
     *
     * @param userId 대출을 반납하는 사용자 ID
     * @param loanId 대출 기록의 ID
     * @throws LoanNotFoundException 해당 대출 기록이 존재하지 않거나 반납할 수 없는 경우
     */
    public void returnBook(String userId, int loanId) {
        int result = loanRepository.returnBook(userId, loanId);

        if (result == 0) {
            throw new LoanNotFoundException("반납을 처리할 수 없습니다. 대출 상태를 확인하고 다시 시도해 주세요.");
        }
    }


    /**
     * 예약 ID로 예약을 조회하여 사용자 본인의 예약이 맞는지 확인 한 후 대출 진행합니다.
     * 예약 취소와 대출 수행은 borrowBook() 에서 진행하며, 예약 기록을 찾을 수 없는 경우 예외를 던집니다.
     *
     * @param userId 예약을 진행하는 사용자 ID
     * @param reservationId 예약 기록의 ID
     * @throws ReservationNotFoundException 해당 예약이 존재하지 않거나, 예약자가 아닌 경우
     */
    public void proceedReservationToCheckout(String userId, int reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .filter(r -> r.getUser().getUserId().equals(userId))
                .orElseThrow(() -> new ReservationNotFoundException("예약을 찾을 수 없습니다. 상태를 확인해주세요."));

        borrowBook(userId, reservation.getBook().getBookId());
    }
    
    
    


    /* *
    // TODO [공부] 구 버전 코드와 비교하여 정리하기
    // 대출 도서 조회
    @Transactional(readOnly = true)
    public List<BookLoanDTO> loanBookList() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);

        return loanHistoryRepository.findByUserIdAndReturnBook(user.getId(), false).stream()
                .map(BookLoanDTO::new)
                .collect(Collectors.toList());
    }
    → BookLoanDTO 객체로 변환하는 과정이 존재
    
    // 도서 대출
    @Transactional
    public void loanBook(int bookId) {
        // 1. 도서 정보
        Book book = bookRepository.findById(bookId);
        // orElseThrow(IllegalArgumentException::new) 설정 안하면 Optional<Book> 타입으로 바꿔줘야 한다.

        // 2. 중복 대출 예외처리
        if (book.isStatus()) {
            return;
        }

        // 3. 유저 정보
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username);

        // 4. 도서 상태 변경
        bookRepository.updateStatusById(bookId, true);

        // 5. 대출 기록 저장
        Loanhistory loanData = new Loanhistory(user, book, book.getBookname());
        loanHistoryRepository.save(loanData);
    }

    // 도서 반납
    @Transactional
    public void returnBook(int loanId, int bookId) {
        // 대출 기록 returnBook → true
        loanHistoryRepository.updateReturnBookById(loanId);

        // 도서 정보 statue → false
        bookRepository.updateStatusById(bookId, false);
    }
    */

}
