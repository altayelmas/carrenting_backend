package com.se.carrenting_backend.Support;

import com.se.carrenting_backend.model.Address;
import com.se.carrenting_backend.model.CustomerReservation;
import com.se.carrenting_backend.model.User;
import com.se.carrenting_backend.model.dto.CustomerReservationDto;

import java.util.ArrayList;
import java.util.Date;

public class ReservationServiceTestSupport {
    public static CustomerReservation createReservation(boolean isValid) {
        CustomerReservation customerReservation = new CustomerReservation();
        customerReservation.setUser(createUser("test"));
        customerReservation.setReservationId(1);
        customerReservation.setBeginDate(new Date());
        customerReservation.setEndDate(new Date());
        customerReservation.setPrice(50);
        customerReservation.setValid(isValid);
        customerReservation.setCar(VehicleServiceTestSupport.createCar("34ABC1"));

        return customerReservation;
    }

    public static CustomerReservationDto createCustomerReservationDto(boolean isValid) {
        return CustomerReservationDto.builder()
                .reservationId(1)
                .beginDate(new Date())
                .endDate(new Date())
                .isValid(isValid)
                .username("test")
                .licencePlate("34ABC1")
                .price(50)
                .build();
    }

    public static User createUser(String username) {
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
}
