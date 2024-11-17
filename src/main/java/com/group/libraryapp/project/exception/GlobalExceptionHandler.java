package com.group.libraryapp.project.exception;

import com.group.libraryapp.project.dto.error.ErrorResponseDTO;
import com.group.libraryapp.project.exception.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    /*
    *   400 Bad Request: 클라이언트가 잘못된 요청을 보냈을 때.
        401 Unauthorized: 인증되지 않은 요청.
        403 Forbidden: 권한이 없는 요청.
        404 Not Found: 리소스를 찾을 수 없을 때.
        409 Conflict: 요청이 현재 서버의 상태와 충돌할 때.
        500 Internal Server Error: 서버 내부에서 문제가 발생했을 때.
    * */


    // 1. 중복된 ID
    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateUsername(DuplicateUsernameException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "ERROR_CODE_01",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    // 2. 입력값 검증 오류
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidInput(InvalidFormatException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "ERROR_CODE_02",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // 3. userId로 조회한 유저 데이터 없음
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFound(UserNotFoundException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "ERROR_CODE_03",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // 4. bookId로 조회한 책 없음
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleBookNotFound(BookNotFoundException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "ERROR_CODE_04",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // 5. 대출 기록 찾을 수 없음
    @ExceptionHandler(LoanNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleLoanNotFound(LoanNotFoundException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "ERROR_CODE_05",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // 6. 예약 기록 찾을 수 없음
    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleReservationNotFound(ReservationNotFoundException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "ERROR_CODE_06",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // 7. 선순위 예약자 존재
    @ExceptionHandler(PriorReservationExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handlePriorReservationExists(PriorReservationExistsException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "ERROR_CODE_07",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    // 8. 대출이 허용되지 않음
    @ExceptionHandler(UnavailableBorrowedException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnavailableBorrowed(UnavailableBorrowedException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "ERROR_CODE_08",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // 9. 예약이 허용되지 않음
    @ExceptionHandler(UnavailableReservationException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnavailableReservation(UnavailableBorrowedException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "ERROR_CODE_09",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }




    // NullPointerException
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNullPointerException(NullPointerException e) {
        return "데이터가 존재하지 않습니다. (" + e + ")";
    }

    // 모든 종류의 예외 처리
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // 500 에러 반환
    public String handleGeneralException(Exception e) {
        return "권한이 없습니다. (" + e + ")";
    }

    /*  이건 뷰 네임으로 반환하는 방법
    @ExceptionHandler(InvalidPasswordException.class)
    public String handleInvalidPasswordException(InvalidPasswordException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", ex.getMessage());
        // return "redirect:/edit-post";
        return "views/error";
    }
    * */

}
