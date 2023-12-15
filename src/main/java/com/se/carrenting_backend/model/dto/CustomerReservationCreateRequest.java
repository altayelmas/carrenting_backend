package com.se.carrenting_backend.model.dto;

import com.se.carrenting_backend.model.Car;
import com.se.carrenting_backend.model.Customer;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReservationCreateRequest {
    private Integer reservationId;
    private Date beginDate;
    private Date endDate;
    private String idNumber;
    private String licencePlate;
}
