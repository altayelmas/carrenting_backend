package com.se.carrenting_backend.model.dto;

import java.util.Date;
import java.util.Objects;

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
    private String username;
    private String licencePlate;
    private Integer price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerReservationDto that = (CustomerReservationDto) o;
        return isValid == that.isValid && Objects.equals(reservationId, that.reservationId) && Objects.equals(username, that.username) && Objects.equals(licencePlate, that.licencePlate) && Objects.equals(price, that.price);
    }
}
