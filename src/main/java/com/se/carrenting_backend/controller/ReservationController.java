package com.se.carrenting_backend.controller;

import com.se.carrenting_backend.model.dto.CustomerReservationDto;
import com.se.carrenting_backend.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/secure/createReservation")
    public ResponseEntity<CustomerReservationDto> createReservation() {
        return null;
    }
}
