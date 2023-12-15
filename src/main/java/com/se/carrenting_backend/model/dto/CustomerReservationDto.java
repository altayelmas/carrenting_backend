package com.se.carrenting_backend.model.dto;

import java.util.Date;

import com.se.carrenting_backend.model.Car;
import com.se.carrenting_backend.model.Customer;
import lombok.*;
@Getter
@Setter
@Builder
public class CustomerReservationDto {
    private Integer reservationId;
    private Date beginDate;
    private Date endDate;
    private boolean isValid;
    private Customer customer;
    private Car car;
}
