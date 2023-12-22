package com.se.carrenting_backend.service;

import com.se.carrenting_backend.exception.NotAvailableException;
import com.se.carrenting_backend.exception.NotFoundException;
import com.se.carrenting_backend.mapper.CustomerReservationMapper;
import com.se.carrenting_backend.mapper.GuestReservationMapper;
import com.se.carrenting_backend.model.Car;
import com.se.carrenting_backend.model.Customer;
import com.se.carrenting_backend.model.CustomerReservation;
import com.se.carrenting_backend.model.dto.CustomerReservationCreateRequest;
import com.se.carrenting_backend.model.dto.CustomerReservationDto;
import com.se.carrenting_backend.repository.CustomerRepository;
import com.se.carrenting_backend.repository.CustomerReservationRepository;
import com.se.carrenting_backend.repository.GuestReservationRepository;
import com.se.carrenting_backend.repository.VehicleRepository;
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
    private final CustomerRepository customerRepository;


    public ReservationService(CustomerReservationRepository customerReservationRepository,
                              CustomerReservationMapper customerReservationMapper,
                              GuestReservationRepository guestReservationRepository,
                              GuestReservationMapper guestReservationMapper,
                              VehicleRepository vehicleRepository,
                              CustomerRepository customerRepository) {
        this.customerReservationRepository = customerReservationRepository;
        this.customerReservationMapper = customerReservationMapper;
        this.guestReservationRepository = guestReservationRepository;
        this.guestReservationMapper = guestReservationMapper;
        this.vehicleRepository = vehicleRepository;
        this.customerRepository = customerRepository;
    }

    public CustomerReservationDto createReservation(CustomerReservationCreateRequest reservationCreateRequest) {
        Optional<Customer> optionalCustomer = customerRepository.findById(reservationCreateRequest.getIdNumber());
        if (optionalCustomer.isEmpty()) {
            throw new NotFoundException("Customer not found");
        }
        Customer customer = optionalCustomer.get();
        List<CustomerReservation> reservations = customer.getReservationList();

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
                .customer(customer)
                .car(car)
                .build();
        car.getCustomerReservationList().add(customerReservation);
        customer.getReservationList().add(customerReservation);
        vehicleRepository.save(car);
        customerRepository.save(customer);
        customerReservationRepository.save(customerReservation);
        return customerReservationMapper.convertToDto(customerReservation);

    }


}
