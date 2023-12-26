package com.se.carrenting_backend.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {
    private List<CustomerReservationDto> customerReservationDto;
    private Integer size;
    private boolean isSuccess;
    private String message;
}
