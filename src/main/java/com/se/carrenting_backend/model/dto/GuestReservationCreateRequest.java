package com.se.carrenting_backend.model.dto;

import com.se.carrenting_backend.model.Car;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestReservationCreateRequest {
    private Integer reservationId;
    private Date beginDate;
    private Date endDate;
    private boolean isValid;
    private String email;
    private String idNumber;
    private String country;
    private String city;
    private String street;
    private String postCode;
    private String phoneNumber;
    private Car car;
}
