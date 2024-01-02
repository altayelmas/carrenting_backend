package com.se.carrenting_backend.service;

import com.se.carrenting_backend.Support.ReservationServiceTestSupport;
import com.se.carrenting_backend.mapper.CustomerReservationMapper;
import com.se.carrenting_backend.mapper.GuestReservationMapper;
import com.se.carrenting_backend.model.CustomerReservation;
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
        CustomerReservation customerReservation = ReservationServiceTestSupport.createReservation(false);
        CustomerReservation newCustomerReservation = ReservationServiceTestSupport.createReservation(true);
        CustomerReservationDto customerReservationDto = ReservationServiceTestSupport.createCustomerReservationDto(true);

        when(customerReservationRepository.findById(1)).thenReturn(Optional.of(customerReservation));
        when(customerReservationRepository.save(any())).thenReturn(newCustomerReservation);
        when(customerReservationMapper.convertToDto(customerReservation)).thenReturn(customerReservationDto);

        CustomerReservationDto result = reservationService.validateReservation(1).getCustomerReservationDto().get(0);
        assertEquals(customerReservationDto, result);
        verify(customerReservationRepository).findById(1);
        verify(customerReservationRepository).save(any());
        verify(customerReservationMapper).convertToDto(customerReservation);
    }
}
