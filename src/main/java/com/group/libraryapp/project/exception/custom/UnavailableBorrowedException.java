package com.group.libraryapp.project.exception.custom;

public class UnavailableBorrowedException extends RuntimeException {
    public UnavailableBorrowedException(String message) {
        super(message);
    }
}
