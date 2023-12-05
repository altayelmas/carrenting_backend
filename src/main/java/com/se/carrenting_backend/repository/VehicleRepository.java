package com.se.carrenting_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.se.carrenting_backend.model.Car;

public interface VehicleRepository extends JpaRepository<Car, String> {
}
