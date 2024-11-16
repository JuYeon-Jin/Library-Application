package com.group.libraryapp.project.service;

import com.group.libraryapp.project.domain.reservation.ReservationRepository;
import com.group.libraryapp.project.dto.reservation.ReservedBookDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservation;

    public ReservationService(ReservationRepository reservation) {
        this.reservation = reservation;
    }

    public List<ReservedBookDTO> listAllReservedBooks(String userId) {
        return reservation.listMyReservedBooks(userId);
    }
}
