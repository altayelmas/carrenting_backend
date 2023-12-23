package com.se.carrenting_backend.model.dto;

import java.util.Date;

import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReservationDto {
    private Integer reservationId;
    private Date beginDate;
    private Date endDate;
    private boolean isValid;
    private String idNumber;
    private String licencePlate;
}
