package com.se.carrenting_backend.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.se.carrenting_backend.model.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VehicleRepository extends PagingAndSortingRepository<Car, String>, CrudRepository<Car, String> {

    Page<Car> findAll(Pageable pageable);
    List<Car> findAllByIsAvailable(boolean isAvailable, Pageable pageable);
    List<Car> findAllByIsAvailable(boolean isAvailable);
}
