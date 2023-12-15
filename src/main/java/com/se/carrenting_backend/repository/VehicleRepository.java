package com.se.carrenting_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.se.carrenting_backend.model.Car;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Car, String> {

    public List<Car> findAllByIsAvailable(boolean isAvailable);
}
