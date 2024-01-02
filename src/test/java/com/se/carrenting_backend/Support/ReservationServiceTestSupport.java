package com.se.carrenting_backend.Support;

import com.se.carrenting_backend.model.Address;
import com.se.carrenting_backend.model.CustomerReservation;
import com.se.carrenting_backend.model.User;
import com.se.carrenting_backend.model.dto.CustomerReservationCreateRequest;
import com.se.carrenting_backend.model.dto.CustomerReservationDto;

import java.util.ArrayList;
import java.util.Date;

public class ReservationServiceTestSupport {
    public static CustomerReservation generateReservation(Integer reservationId, String licencePlate, boolean isValid) {
        CustomerReservation customerReservation = new CustomerReservation();
        customerReservation.setUser(generateUser("test"));
        customerReservation.setReservationId(1);
        customerReservation.setBeginDate(new Date());
        customerReservation.setEndDate(new Date());
        customerReservation.setPrice(50);
        customerReservation.setValid(isValid);
        customerReservation.setCar(VehicleServiceTestSupport.generateCar(licencePlate));

        return customerReservation;
    }

    public static CustomerReservationDto generateCustomerReservationDto(String licencePlate, boolean isValid) {
        return CustomerReservationDto.builder()
                .reservationId(1)
                .beginDate(new Date())
                .endDate(new Date())
                .isValid(isValid)
                .username("test")
                .licencePlate(licencePlate)
                .price(50)
                .build();
    }

    public static User generateUser(String username) {
        Address address = Address.builder()
                .addressId(1)
                .country("TestCountry")
                .city("TestCity")
                .street("TestStreet")
                .postCode("TestPostCode")
                .user(new User())
                .build();
        return User.builder()
                .idNumber(1)
                .email("testemail@test.com")
                .username(username)
                .password("testpassword")
                .address(address)
                .reservationList(new ArrayList<>())
                .roles("USER")
                .build();

    }

    public static CustomerReservationCreateRequest generateCustomerReservationCreateRequest(String licencePlate, String username, Integer price) {
        return CustomerReservationCreateRequest.builder()
                .beginDate(new Date(2023, 12, 28))
                .endDate(new Date(2024, 1, 2))
                .licencePlate(licencePlate)
                .username(username)
                .price(price)
                .build();
    }

    public static CustomerReservationDto generateCustomerReservationDto(Integer reservationId,
                                                                        String username,
                                                                        String licencePlate,
                                                                        Integer price) {
        return CustomerReservationDto.builder()
                .reservationId(reservationId)
                .beginDate(new Date(2023, 12, 28))
                .endDate(new Date(2024, 1, 2))
                .isValid(false)
                .username(username)
                .licencePlate(licencePlate)
                .price(price)
                .build();
    }
}
