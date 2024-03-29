package com.se.carrenting_backend.model.dto;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReservationCreateRequest {
    private Date beginDate;
    private Date endDate;
    private String licencePlate;
    private String username;
    private Integer price;
}
