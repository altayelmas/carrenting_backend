package com.se.carrenting_backend.repository;

import com.se.carrenting_backend.model.CustomerReservation;
import com.se.carrenting_backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerReservationRepository extends JpaRepository<CustomerReservation, Integer> {
    Page<CustomerReservation> findAllByUser(User user, Pageable pageable);
    List<CustomerReservation> findAllByUser(User user);
}
