package com.group.libraryapp.project.exception;

import com.group.libraryapp.project.dto.error.ErrorResponseDTO;
import com.group.libraryapp.project.exception.custom.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public ResponseEntity<ErrorResponseDTO> handleDuplicateUsername(DuplicateUsernameException e, RedirectAttributes redirectAttributes) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                1, e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    // TODO 유저 회원가입 시 에러페이지는 return ResponseEntity

    // 2. 입력값 검증 오류
    @ExceptionHandler(InvalidFormatException.class)
    public String handleInvalidInput(InvalidFormatException e, RedirectAttributes redirectAttributes) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                2, e.getMessage()
        );
        // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        redirectAttributes.addFlashAttribute(errorResponse);
        return "redirect:/error";
    }

    // 3. userId로 조회한 유저 데이터 없음
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(UserNotFoundException e, RedirectAttributes redirectAttributes) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                3, e.getMessage()
        );
        // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        redirectAttributes.addFlashAttribute(errorResponse);
        return "redirect:/error";
    }

    // 4. bookId로 조회한 책 없음
    @ExceptionHandler(BookNotFoundException.class)
    public String handleBookNotFound(BookNotFoundException e, RedirectAttributes redirectAttributes) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                4, e.getMessage()
        );
        // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        redirectAttributes.addFlashAttribute(errorResponse);
        return "redirect:/error";
    }

    // 5. 대출 기록 찾을 수 없음
    @ExceptionHandler(LoanNotFoundException.class)
    public String handleLoanNotFound(LoanNotFoundException e, RedirectAttributes redirectAttributes) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                5, e.getMessage()
        );
        // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        redirectAttributes.addFlashAttribute(errorResponse);
        return "redirect:/error";
    }

    // 6. 예약 기록 찾을 수 없음
    @ExceptionHandler(ReservationNotFoundException.class)
    public String handleReservationNotFound(ReservationNotFoundException e, RedirectAttributes redirectAttributes) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                6, e.getMessage()
        );
        // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        redirectAttributes.addFlashAttribute(errorResponse);
        return "redirect:/error";
    }

    // 7. 선순위 예약자 존재
    @ExceptionHandler(PriorReservationExistsException.class)
    public String handlePriorReservationExists(PriorReservationExistsException e, RedirectAttributes redirectAttributes) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                7, e.getMessage()
        );
        // return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        redirectAttributes.addFlashAttribute(errorResponse);
        return "redirect:/error";
    }

    // 8. 대출이 허용되지 않음
    @ExceptionHandler(UnavailableBorrowedException.class)
    public String handleUnavailableBorrowed(UnavailableBorrowedException e, RedirectAttributes redirectAttributes) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                8, e.getMessage()
        );
        // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        redirectAttributes.addFlashAttribute(errorResponse);
        return "redirect:/error";
    }

    // 9. 예약이 허용되지 않음
    @ExceptionHandler(UnavailableReservationException.class)
    public String handleUnavailableReservation(UnavailableBorrowedException e, RedirectAttributes redirectAttributes) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                9, e.getMessage()
        );
        // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        redirectAttributes.addFlashAttribute(errorResponse);
        return "redirect:/error";
    }

    // 10. (관리자) 이미지 저장 중 에러가 발생함
    @ExceptionHandler(ImageSaveException.class)
    public String handleImageSaveError(ImageSaveException e, RedirectAttributes redirectAttributes) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                10, e.getMessage()
        );
        // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        redirectAttributes.addFlashAttribute(errorResponse);
        return "redirect:/error";
    }

    // 11. (관리자) 입력값 검증 오류
    @ExceptionHandler(AdminValidationException.class)
    public String handleInvalidInput(AdminValidationException e, RedirectAttributes redirectAttributes) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                11, e.getMessage()
        );
        // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        redirectAttributes.addFlashAttribute(errorResponse);
        return "redirect:/error";
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
}
