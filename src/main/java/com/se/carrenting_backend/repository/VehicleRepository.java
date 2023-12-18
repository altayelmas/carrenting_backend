package com.se.carrenting_backend.repository;

import com.se.carrenting_backend.model.enums.CarBrand;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.se.carrenting_backend.model.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Car, String> {

    Page<Car> findAll(Pageable pageable);
    Page<Car> findAllByCarModelContainingAndCarBrand(String carModel, CarBrand carBrand, Pageable pageable);
    List<Car> findAllByCarModelContainingAndCarBrand(String carModel, CarBrand carBrand);
    Page<Car> findAllByCarModelContaining(String carModel, Pageable pageable);
    List<Car> findAllByCarModelContaining(String carModel);
    Page<Car> findAllByCarModelContainingAndIsAvailable(String carModel, boolean isAvailable, Pageable pageable);
    List<Car> findAllByCarModelContainingAndIsAvailable(String carModel, boolean isAvailable);
    Page<Car> findAllByCarModelContainingAndIsAvailableAndCarBrand(String carModel, boolean isAvailable, CarBrand carBrand, Pageable pageable);
    List<Car> findAllByCarModelContainingAndIsAvailableAndCarBrand(String carModel, boolean isAvailable, CarBrand carBrand);
    //Car findByLicencePlate(String licencePlate);

}
