package com.se.carrenting_backend.controller;

import com.se.carrenting_backend.exception.NotAvailableException;
import com.se.carrenting_backend.exception.NotFoundException;
import com.se.carrenting_backend.model.dto.CustomerReservationCreateRequest;
import com.se.carrenting_backend.model.dto.ReservationResponse;
import com.se.carrenting_backend.service.ReservationService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/reservation")
@CrossOrigin("http://localhost:3000")
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
            reservationResponse.setCustomerReservationDto(new ArrayList<>());
            reservationResponse.setSize(0);
            reservationResponse.setSuccess(false);
            reservationResponse.setMessage(notFoundException.getMessage());
            return new ResponseEntity<>(reservationResponse, HttpStatus.NOT_FOUND);
        } catch (NotAvailableException notAvailableException) {
            ReservationResponse reservationResponse = new ReservationResponse();
            reservationResponse.setCustomerReservationDto(new ArrayList<>());
            reservationResponse.setSize(0);
            reservationResponse.setSuccess(false);
            reservationResponse.setMessage(notAvailableException.getMessage());
            return new ResponseEntity<>(reservationResponse, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<ReservationResponse> getAllReservations(@RequestParam Integer page,
                                                                  @RequestParam Integer size,
                                                                  @RequestParam String username) {
        ReservationResponse reservationResponse = reservationService.getAllReservations(page, size, username);
        return new ResponseEntity<>(reservationResponse, HttpStatus.OK);
    }

    @PatchMapping("/validateReservation")
    public ResponseEntity<ReservationResponse> validateReservation (@RequestParam Integer reservationId) {
        try {
            ReservationResponse reservationResponse = reservationService.validateReservation(reservationId);
            return new ResponseEntity<>(reservationResponse, HttpStatus.OK);
        } catch (NotFoundException notFoundException) {
            ReservationResponse reservationResponse = ReservationResponse.builder()
                    .customerReservationDto(new ArrayList<>())
                    .size(0)
                    .message(notFoundException.getMessage())
                    .isSuccess(false)
                    .build();
            return new ResponseEntity<>(reservationResponse, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getMyReservations")
    public ResponseEntity<ReservationResponse> getReservationsOfUser(@RequestHeader String token) {
        try {
            ReservationResponse reservationResponse = reservationService.getReservationsOfUser(token);
            return new ResponseEntity<>(reservationResponse, HttpStatus.OK);
        } catch (NotFoundException notFoundException) {
            ReservationResponse reservationResponse = ReservationResponse.builder()
                    .customerReservationDto(new ArrayList<>())
                    .isSuccess(false)
                    .message(notFoundException.getMessage())
                    .size(0)
                    .build();
            return new ResponseEntity<>(reservationResponse, HttpStatus.NOT_FOUND);
        }
    }

}
