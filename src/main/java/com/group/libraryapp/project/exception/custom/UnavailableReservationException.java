package com.group.libraryapp.project.exception.custom;

public class UnavailableReservationException extends RuntimeException {
    public UnavailableReservationException(String message) {
        super(message);
    }
}