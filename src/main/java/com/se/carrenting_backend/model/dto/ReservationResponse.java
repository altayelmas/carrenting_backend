package com.se.carrenting_backend.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {
    private CustomerReservationDto customerReservationDto;
    private boolean isSuccess;
    private String message;
}
