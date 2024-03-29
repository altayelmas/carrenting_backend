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
import com.se.carrenting_backend.model.dto.ReservationResponse;
import com.se.carrenting_backend.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
    private final JwtService jwtService;


    public ReservationService(CustomerReservationRepository customerReservationRepository,
                              CustomerReservationMapper customerReservationMapper,
                              GuestReservationRepository guestReservationRepository,
                              GuestReservationMapper guestReservationMapper,
                              VehicleRepository vehicleRepository,
                              UserRepository userRepository,
                              JwtService jwtService) {
        this.customerReservationRepository = customerReservationRepository;
        this.customerReservationMapper = customerReservationMapper;
        this.guestReservationRepository = guestReservationRepository;
        this.guestReservationMapper = guestReservationMapper;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public ReservationResponse createReservation(CustomerReservationCreateRequest reservationCreateRequest) {

        Optional<User> optionalUser = userRepository.findByUsername(reservationCreateRequest.getUsername());
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
        if (reservationCreateRequest.getBeginDate().after(reservationCreateRequest.getEndDate())) {
            throw new NotAvailableException("Begin date can't be later than end date");
        }

        CustomerReservation customerReservation = CustomerReservation.builder()
                .user(user)
                .car(car)
                .build();
        customerReservation.setPrice(reservationCreateRequest.getPrice());
        customerReservation.setValid(false);
        customerReservation.setBeginDate(reservationCreateRequest.getBeginDate());
        customerReservation.setEndDate(reservationCreateRequest.getEndDate());
        car.getCustomerReservationList().add(customerReservation);
        car.setAvailable(false);
        user.getReservationList().add(customerReservation);
        customerReservation = customerReservationRepository.save(customerReservation);
        vehicleRepository.save(car);
        userRepository.save(user);

        CustomerReservationDto customerReservationDto = customerReservationMapper.convertToDto(customerReservation);
        List<CustomerReservationDto> customerReservationDtoList = new ArrayList<>();
        customerReservationDtoList.add(customerReservationDto);
        return ReservationResponse.builder()
                .customerReservationDto(customerReservationDtoList)
                .size(1)
                .isSuccess(true)
                .message(HttpStatus.OK.toString())
                .build();
    }


    public ReservationResponse getAllReservations(Integer page, Integer size, String username) {
        Pageable pageable = PageRequest.of(page, size);
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (username.isEmpty()) {
            Page<CustomerReservation> customerReservationPage = customerReservationRepository.findAll(pageable);
            return ReservationResponse.builder()
                    .customerReservationDto(customerReservationMapper.customerReservationToDtoList(
                            customerReservationPage.getContent()
                    ))
                    .size(getAmountOfReservations())
                    .isSuccess(true)
                    .message(HttpStatus.OK.toString())
                    .build();
        } else {
            if (optionalUser.isEmpty()) {
                return ReservationResponse.builder()
                        .customerReservationDto(new ArrayList<>())
                        .size(0)
                        .isSuccess(true)
                        .message(HttpStatus.OK.toString())
                        .build();
            } else {
                User user = optionalUser.get();
                Page<CustomerReservation> customerReservationPage = customerReservationRepository.findAllByUser(user, pageable);
                return ReservationResponse.builder()
                        .customerReservationDto(customerReservationMapper.customerReservationToDtoList(
                                customerReservationPage.getContent()))
                        .size(getAmountOfReservationsWithUsername(username))
                        .isSuccess(true)
                        .message(HttpStatus.OK.toString())
                        .build();
            }
        }
    }

    public Integer getAmountOfReservationsWithUsername(String username) {
        User user = userRepository.findByUsername(username).get();
        return customerReservationRepository.findAllByUser(user).size();
    }

    public Integer getAmountOfReservations() {
        return customerReservationRepository.findAll().size();
    }

    public ReservationResponse validateReservation(Integer reservationId) {
        Optional<CustomerReservation> optionalCustomerReservation = customerReservationRepository.findById(reservationId);
        if (optionalCustomerReservation.isEmpty()) {
            throw new NotFoundException("Reservation not found");
        }
        CustomerReservation customerReservation = optionalCustomerReservation.get();
        customerReservation.setValid(true);
        customerReservationRepository.save(customerReservation);
        List<CustomerReservationDto> customerReservationDtoList = new ArrayList<>();
        customerReservationDtoList.add(customerReservationMapper.convertToDto(customerReservation));
        return new ReservationResponse().builder()
                .customerReservationDto(customerReservationDtoList)
                .size(1)
                .isSuccess(true)
                .message(HttpStatus.OK.toString())
                .build();
    }

    public ReservationResponse getReservationsOfUser(String token, Integer page, Integer size) {
        String username = jwtService.extractUsername(token);
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        Pageable pageable = PageRequest.of(page, size);
        List<CustomerReservation> customerReservationList = customerReservationRepository.findAllByUser(user.get(), pageable).getContent();
        return ReservationResponse.builder()
                .customerReservationDto(customerReservationMapper.customerReservationToDtoList(customerReservationList))
                .size(customerReservationList.size())
                .message(HttpStatus.OK.toString())
                .isSuccess(true)
                .build();
    }
}
