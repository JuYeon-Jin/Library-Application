package com.group.libraryapp.project.exception.custom;

public class PriorReservationExistsException  extends RuntimeException {
    public PriorReservationExistsException(String message) {
        super(message);
    }
}
