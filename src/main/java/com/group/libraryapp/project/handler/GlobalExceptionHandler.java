package com.group.libraryapp.project.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

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
