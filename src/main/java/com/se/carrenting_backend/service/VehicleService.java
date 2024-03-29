package com.se.carrenting_backend.service;

import com.se.carrenting_backend.exception.NotAvailableException;
import com.se.carrenting_backend.exception.NotFoundException;
import com.se.carrenting_backend.mapper.VehicleMapper;
import com.se.carrenting_backend.model.Car;
import com.se.carrenting_backend.model.dto.VehicleCreateRequest;
import com.se.carrenting_backend.model.dto.VehicleDto;
import com.se.carrenting_backend.model.dto.VehicleResponse;
import com.se.carrenting_backend.model.enums.CarBrand;
import com.se.carrenting_backend.repository.VehicleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    public VehicleService (VehicleRepository vehicleRepository, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    public VehicleResponse getAllCars(Integer page, Integer size, String carBrand, String carModel) {
        Pageable pageable = PageRequest.of(page, size);
        if (carBrand.isEmpty()) {
            Page<Car> carList = vehicleRepository.findAllByCarModelContaining(carModel, pageable);
            VehicleResponse vehicleResponse = VehicleResponse.builder()
                    .vehicleDtoList(vehicleMapper.carListToDtoList(carList.getContent()))
                    .vehicleAmount(getAmountOfCarsWithModel(carModel))
                    .isSuccess(true)
                    .message(HttpStatus.OK.toString())
                    .build();
            return vehicleResponse;
        } else {
            CarBrand brand = CarBrand.valueOf(carBrand);
            Page<Car> carList = vehicleRepository.findAllByCarModelContainingAndCarBrand(carModel, brand, pageable);
            VehicleResponse vehicleResponse = VehicleResponse.builder()
                    .vehicleDtoList(vehicleMapper.carListToDtoList(carList.getContent()))
                    .vehicleAmount(getAmountOfCarsWithModelAndBrand(carModel, carBrand))
                    .isSuccess(true)
                    .message(HttpStatus.OK.toString())
                    .build();
            return vehicleResponse;
        }
    }

    public Integer getAmountOfCarsWithModel(String carModel) {
        List<Car> carList = vehicleRepository.findAllByCarModelContaining(carModel);
        return carList.size();
    }

    public Integer getAmountOfCarsWithModelAndBrand(String carModel, String carBrand) {
        CarBrand brand = CarBrand.valueOf(carBrand);
        List<Car> carList = vehicleRepository.findAllByCarModelContainingAndCarBrand(carModel, brand);
        return carList.size();
    }

    public VehicleResponse getVehicle(String licencePlate) {
        Optional<Car> optionalCar = vehicleRepository.findById(licencePlate);
        if (optionalCar.isEmpty()) {
            throw new NotFoundException("Vehicle not found");
        }
        Car car = optionalCar.get();
        List<Car> carList = new ArrayList<>();
        carList.add(car);
        VehicleResponse vehicleResponse = new VehicleResponse();
        vehicleResponse.setVehicleDtoList(vehicleMapper.carListToDtoList(carList));
        vehicleResponse.setVehicleAmount(carList.size());
        return vehicleResponse;
    }


    public VehicleResponse createVehicle(VehicleCreateRequest request) {
            String licencePlate = request.getLicencePlate();
            if (vehicleRepository.existsById(request.getLicencePlate())) {
                throw new NotAvailableException("Vehicle with this licence plate already exists");
            }
            if (licencePlate.length() <= 5) {
                throw new InputMismatchException("Licence plate cannot be shorter than 6 characters");
            } else {
                try {
                    Integer.parseInt(licencePlate.substring(0, 2));
                } catch (NumberFormatException numberFormatException) {
                    throw new InputMismatchException("Licence plate should start with a number between 01 and 81");
                }
                if (!(Integer.parseInt(licencePlate.substring(0, 2)) >= 1 &&
                        Integer.parseInt(licencePlate.substring(0, 2)) <= 81)) {
                    throw new InputMismatchException("Licence plate should start with a number between 01 and 81");
                } else {
                    Car car = Car.builder()
                            .licencePlate(request.getLicencePlate())
                            .carType(request.getCarType())
                            .gearType(request.getGearType())
                            .carBrand(request.getCarBrand())
                            .carModel(request.getCarModel())
                            .engine(request.getEngine())
                            .price(request.getPrice())
                            .seats(request.getSeats())
                            .isAvailable(true)
                            .img(request.getImg())
                            .customerReservationList(new ArrayList<>())
                            .guestReservationList(new ArrayList<>())
                            .build();
                    car = vehicleRepository.save(car);
                    VehicleResponse vehicleResponse = VehicleResponse.builder()
                            .vehicleDtoList(new ArrayList<>())
                            .message(HttpStatus.OK.toString())
                            .isSuccess(true)
                            .vehicleAmount(1)
                            .build();
                    vehicleResponse.getVehicleDtoList().add(vehicleMapper.convertToDto(car));
                    return vehicleResponse;
                }
            }
    }

    public Integer getAmountOfAvailableCarsWithModel(String model) {
        List<Car> carList = vehicleRepository.findAllByCarModelContainingAndIsAvailable(model, true);
        return carList.size();
    }

    public Integer getAmountOfAvailableCarsWithModelAndBrand(String model, String brand) {
        CarBrand carBrand = CarBrand.valueOf(brand);
        List<Car> carList = vehicleRepository.findAllByCarModelContainingAndIsAvailableAndCarBrand(model, true, carBrand);
        return carList.size();
    }

    public VehicleResponse getAllAvailableCars(Integer page, Integer size, String carModel, String carBrand) {
        Pageable pageable = PageRequest.of(page, size);
        if (carBrand.isEmpty()) {
           Page<Car> carList = vehicleRepository.findAllByCarModelContainingAndIsAvailable(carModel, true, pageable);
           VehicleResponse vehicleResponse = VehicleResponse.builder()
                   .vehicleDtoList(vehicleMapper.carListToDtoList(carList.getContent()))
                   .vehicleAmount(getAmountOfAvailableCarsWithModel(carModel))
                   .isSuccess(true)
                   .message(HttpStatus.OK.toString())
                   .build();
           return vehicleResponse;
        } else {
            CarBrand brand = CarBrand.valueOf(carBrand);
            Page<Car> carList = vehicleRepository.findAllByCarModelContainingAndIsAvailableAndCarBrand(carModel, true, brand, pageable);
            VehicleResponse vehicleResponse = VehicleResponse.builder()
                    .vehicleDtoList(vehicleMapper.carListToDtoList(carList.getContent()))
                    .vehicleAmount(getAmountOfAvailableCarsWithModelAndBrand(carModel, carBrand))
                    .isSuccess(true)
                    .message(HttpStatus.OK.toString())
                    .build();
            return vehicleResponse;
        }
    }
}
