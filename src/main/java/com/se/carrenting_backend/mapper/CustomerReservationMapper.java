package com.se.carrenting_backend.mapper;

import com.se.carrenting_backend.model.CustomerReservation;
import com.se.carrenting_backend.model.dto.CustomerReservationDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerReservationMapper {
    public CustomerReservationDto convertToDto(CustomerReservation customerReservation){
        return CustomerReservationDto.builder()
                .reservationId(customerReservation.getReservationId())
                .beginDate(customerReservation.getBeginDate())
                .endDate(customerReservation.getEndDate())
                .isValid(customerReservation.isValid())
                .username(customerReservation.getUser().getUsername())
                .licencePlate(customerReservation.getCar().getLicencePlate())
                .price(customerReservation.getPrice())
                .build();
    }

    public List<CustomerReservationDto> customerReservationToDtoList(List<CustomerReservation> customerReservationList) {
        List<CustomerReservationDto> customerReservationDtoList = new ArrayList<>();
        for (CustomerReservation customerReservation: customerReservationList ) {
            customerReservationDtoList.add(convertToDto(customerReservation));
        }
        return customerReservationDtoList;
    }
}
