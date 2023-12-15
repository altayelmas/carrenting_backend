package com.se.carrenting_backend.repository;

import com.se.carrenting_backend.model.GuestReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestReservationRepository extends JpaRepository<GuestReservation, Integer> {
}
