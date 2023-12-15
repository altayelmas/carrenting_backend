package com.se.carrenting_backend.repository;

import com.se.carrenting_backend.model.CustomerReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerReservationRepository extends JpaRepository<CustomerReservation, Integer> {
}
