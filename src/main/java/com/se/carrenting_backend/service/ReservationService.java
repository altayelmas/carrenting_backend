package com.se.carrenting_backend.service;

import com.se.carrenting_backend.mapper.CustomerReservationMapper;
import com.se.carrenting_backend.mapper.GuestReservationMapper;
import com.se.carrenting_backend.repository.CustomerReservationRepository;
import com.se.carrenting_backend.repository.GuestReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final CustomerReservationRepository customerReservationRepository;
    private final CustomerReservationMapper customerReservationMapper;
    private final GuestReservationRepository guestReservationRepository;
    private final GuestReservationMapper guestReservationMapper;


    public ReservationService(CustomerReservationRepository customerReservationRepository, CustomerReservationMapper customerReservationMapper, GuestReservationRepository guestReservationRepository, GuestReservationMapper guestReservationMapper) {
        this.customerReservationRepository = customerReservationRepository;
        this.customerReservationMapper = customerReservationMapper;
        this.guestReservationRepository = guestReservationRepository;
        this.guestReservationMapper = guestReservationMapper;
    }


}
