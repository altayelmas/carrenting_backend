package com.se.carrenting_backend.controller;

import com.se.carrenting_backend.exception.NotAvailableException;
import com.se.carrenting_backend.exception.NotFoundException;
import com.se.carrenting_backend.model.dto.CustomerReservationCreateRequest;
import com.se.carrenting_backend.model.dto.CustomerReservationDto;
import com.se.carrenting_backend.model.dto.ReservationResponse;
import com.se.carrenting_backend.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/createReservation")
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody CustomerReservationCreateRequest reservationCreateRequest) {
        try {
           ReservationResponse reservationResponse = reservationService.createReservation(reservationCreateRequest);
           return new ResponseEntity<>(reservationResponse, HttpStatus.OK);
        } catch (NotFoundException notFoundException) {
            ReservationResponse reservationResponse = new ReservationResponse();
            reservationResponse.setSuccess(false);
            reservationResponse.setMessage(HttpStatus.NOT_FOUND.toString());
            return new ResponseEntity<>(reservationResponse, HttpStatus.NOT_FOUND);
        } catch (NotAvailableException notAvailableException) {
            ReservationResponse reservationResponse = new ReservationResponse();
            reservationResponse.setSuccess(false);
            reservationResponse.setMessage(HttpStatus.NOT_ACCEPTABLE.toString());
            return new ResponseEntity<>(reservationResponse, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
