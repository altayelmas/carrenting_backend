package com.se.carrenting_backend.mapper;

import com.se.carrenting_backend.model.GuestReservation;
import com.se.carrenting_backend.model.dto.GuestReservationDto;
import org.springframework.stereotype.Component;

@Component
public class GuestReservationMapper {
    public GuestReservationDto convertToDto(GuestReservation guestReservation){
        return GuestReservationDto.builder()
                .reservationId(guestReservation.getReservationId())
                .beginDate(guestReservation.getBeginDate())
                .endDate(guestReservation.getEndDate())
                .isValid(guestReservation.isValid())
                .email(guestReservation.getEmail())
                .idNumber(guestReservation.getIdNumber())
                .country(guestReservation.getCountry())
                .city(guestReservation.getCity())
                .street(guestReservation.getStreet())
                .postCode(guestReservation.getPostCode())
                .phoneNumber(guestReservation.getPhoneNumber())
                .build();
    }
}
