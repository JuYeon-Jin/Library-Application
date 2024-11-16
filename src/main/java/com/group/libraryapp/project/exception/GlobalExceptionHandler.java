package com.group.libraryapp.project.exception;

import com.group.libraryapp.project.dto.error.ErrorResponseDTO;
import com.group.libraryapp.project.exception.custom.DuplicateUsernameException;
import com.group.libraryapp.project.exception.custom.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

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
