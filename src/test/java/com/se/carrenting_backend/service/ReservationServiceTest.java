package com.se.carrenting_backend.service;

import com.se.carrenting_backend.Support.ReservationServiceTestSupport;
import com.se.carrenting_backend.Support.VehicleServiceTestSupport;
import com.se.carrenting_backend.mapper.CustomerReservationMapper;
import com.se.carrenting_backend.mapper.GuestReservationMapper;
import com.se.carrenting_backend.model.Car;
import com.se.carrenting_backend.model.CustomerReservation;
import com.se.carrenting_backend.model.User;
import com.se.carrenting_backend.model.dto.CustomerReservationCreateRequest;
import com.se.carrenting_backend.model.dto.CustomerReservationDto;
import com.se.carrenting_backend.repository.CustomerReservationRepository;
import com.se.carrenting_backend.repository.GuestReservationRepository;
import com.se.carrenting_backend.repository.UserRepository;
import com.se.carrenting_backend.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    private ReservationService reservationService;
    private CustomerReservationRepository customerReservationRepository;
    private CustomerReservationMapper customerReservationMapper;
    private GuestReservationRepository guestReservationRepository;
    private GuestReservationMapper guestReservationMapper;
    private VehicleRepository vehicleRepository;
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        customerReservationRepository = mock(CustomerReservationRepository.class);
        customerReservationMapper = mock(CustomerReservationMapper.class);
        guestReservationRepository = mock(GuestReservationRepository.class);
        guestReservationMapper = mock(GuestReservationMapper.class);
        vehicleRepository = mock(VehicleRepository.class);
        userRepository = mock(UserRepository.class);

        reservationService = new ReservationService(customerReservationRepository,
                customerReservationMapper,
                guestReservationRepository,
                guestReservationMapper,
                vehicleRepository,
                userRepository);
    }

    @Test
    public void validateReservationTest() {
        CustomerReservation customerReservation = ReservationServiceTestSupport.generateReservation(1, "34ABC1", false);
        CustomerReservation newCustomerReservation = ReservationServiceTestSupport.generateReservation( 1,"34ABC1",true);
        CustomerReservationDto customerReservationDto = ReservationServiceTestSupport.generateCustomerReservationDto("34ABC1",true);

        when(customerReservationRepository.findById(1)).thenReturn(Optional.of(customerReservation));
        when(customerReservationRepository.save(any())).thenReturn(newCustomerReservation);
        when(customerReservationMapper.convertToDto(customerReservation)).thenReturn(customerReservationDto);

        CustomerReservationDto result = reservationService.validateReservation(1).getCustomerReservationDto().get(0);
        assertEquals(customerReservationDto, result);
        verify(customerReservationRepository).findById(1);
        verify(customerReservationRepository).save(any());
        verify(customerReservationMapper).convertToDto(customerReservation);
    }

    @Test
    public void createReservationTest() {
        User oldUser = ReservationServiceTestSupport.generateUser("test");
        User newUser = ReservationServiceTestSupport.generateUser("test");
        Car oldCar = VehicleServiceTestSupport.generateCar("34ABC123");
        Car newCar = VehicleServiceTestSupport.generateCar("34ABC123");
        CustomerReservation customerReservation = ReservationServiceTestSupport
                .generateReservation(1, "34ABC123", false);
        newCar.getCustomerReservationList().add(customerReservation);
        newCar.setAvailable(false);
        CustomerReservationCreateRequest customerReservationCreateRequest = ReservationServiceTestSupport
                .generateCustomerReservationCreateRequest("34ABC123",
                "test",
                30);
        newUser.getReservationList().add(customerReservation);
        CustomerReservationDto customerReservationDto = ReservationServiceTestSupport
                .generateCustomerReservationDto(1,
                "test",
                "34ABC123",
                30);

        when(userRepository.findByUsername("test")).thenReturn(Optional.of(oldUser));
        when(vehicleRepository.findById("34ABC123")).thenReturn(Optional.of(oldCar));
        when(customerReservationRepository.save(any())).thenReturn(customerReservation);
        when(vehicleRepository.save(any())).thenReturn(newCar);
        when(userRepository.save(any())).thenReturn(newUser);
        when(customerReservationMapper.convertToDto(customerReservation)).thenReturn(customerReservationDto);

        CustomerReservationDto result = reservationService
                .createReservation(customerReservationCreateRequest).getCustomerReservationDto().get(0);
        assertEquals(customerReservationDto, result);
        verify(userRepository).findByUsername("test");
        verify(vehicleRepository).findById("34ABC123");
        verify(vehicleRepository).save(any());
        verify(userRepository).save(any());
        verify(customerReservationMapper).convertToDto(customerReservation);
    }
}
