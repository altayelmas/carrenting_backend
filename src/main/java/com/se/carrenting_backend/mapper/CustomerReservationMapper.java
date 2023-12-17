package com.se.carrenting_backend.mapper;

import com.se.carrenting_backend.model.CustomerReservation;
import com.se.carrenting_backend.model.dto.CustomerReservationDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerReservationMapper {
    public CustomerReservationDto convertToDto(CustomerReservation customerReservation){
        return CustomerReservationDto.builder()
                .reservationId(customerReservation.getReservationId())
                .beginDate(customerReservation.getBeginDate())
                .endDate(customerReservation.getEndDate())
                .isValid(false)
                .idNumber(customerReservation.getCustomer().getIdNumber())
                .licencePlate(customerReservation.getCar().getLicencePlate())
                .build();
    }
}