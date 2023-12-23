package com.se.carrenting_backend.service;

import com.se.carrenting_backend.exception.NotAvailableException;
import com.se.carrenting_backend.exception.NotFoundException;
import com.se.carrenting_backend.mapper.CustomerReservationMapper;
import com.se.carrenting_backend.mapper.GuestReservationMapper;
import com.se.carrenting_backend.model.Car;
import com.se.carrenting_backend.model.CustomerReservation;
import com.se.carrenting_backend.model.User;
import com.se.carrenting_backend.model.dto.CustomerReservationCreateRequest;
import com.se.carrenting_backend.model.dto.CustomerReservationDto;
import com.se.carrenting_backend.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final CustomerReservationRepository customerReservationRepository;
    private final CustomerReservationMapper customerReservationMapper;
    private final GuestReservationRepository guestReservationRepository;
    private final GuestReservationMapper guestReservationMapper;

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;


    public ReservationService(CustomerReservationRepository customerReservationRepository,
                              CustomerReservationMapper customerReservationMapper,
                              GuestReservationRepository guestReservationRepository,
                              GuestReservationMapper guestReservationMapper,
                              VehicleRepository vehicleRepository,
                              UserRepository userRepository) {
        this.customerReservationRepository = customerReservationRepository;
        this.customerReservationMapper = customerReservationMapper;
        this.guestReservationRepository = guestReservationRepository;
        this.guestReservationMapper = guestReservationMapper;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    public CustomerReservationDto createReservation(CustomerReservationCreateRequest reservationCreateRequest) {
        Optional<User> optionalUser = userRepository.findById(reservationCreateRequest.getIdNumber());
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("Customer not found");
        }
        User user = optionalUser.get();
        List<CustomerReservation> reservations = user.getReservationList();

        if (reservations.size() >= 3 &&
                reservations.get(reservations.size() - 1).isValid() &&
                reservations.get(reservations.size() - 2).isValid() &&
                reservations.get(reservations.size() - 3).isValid()) {
            throw new NotAvailableException("Customer already has 3 valid reservations");
        }

        Optional<Car> optionalCar = vehicleRepository.findById(reservationCreateRequest.getLicencePlate());
        if (optionalCar.isEmpty()) {
            throw new NotFoundException("Car not found");
        }
        Car car = optionalCar.get();

        if (!car.isAvailable()) {
            throw new NotAvailableException("Car is not available");
        }

        CustomerReservation customerReservation = CustomerReservation.builder()
                .user(user)
                .car(car)
                .build();
        car.getCustomerReservationList().add(customerReservation);
        user.getReservationList().add(customerReservation);
        vehicleRepository.save(car);
        userRepository.save(user);
        customerReservationRepository.save(customerReservation);
        return customerReservationMapper.convertToDto(customerReservation);

    }


}
