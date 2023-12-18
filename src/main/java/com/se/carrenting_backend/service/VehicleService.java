package com.se.carrenting_backend.service;

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
import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    public VehicleService (VehicleRepository vehicleRepository, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    public List<VehicleDto> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Car> pageContent = vehicleRepository.findAll(pageable);
        List<Car> carList = pageContent.getContent();
        List<VehicleDto> vehicleDtoList = vehicleMapper.carListToDtoList(carList);

        return vehicleDtoList;
    }

    public VehicleDto createVehicle(VehicleCreateRequest request) {
            Car car = Car.builder()
                    .licencePlate(request.getLicencePlate())
                    .carType(request.getCarType())
                    .gearType(request.getGearType())
                    .carBrand(request.getCarBrand())
                    .carModel(request.getCarModel())
                    .engine(request.getEngine())
                    .seats(request.getSeats())
                    .isAvailable(true)
                    .img(request.getImg())
                    .customerReservationList(new ArrayList<>())
                    .guestReservationList(new ArrayList<>())
                    .build();
            return vehicleMapper.convertToDto(vehicleRepository.save(car));
    }

    public Integer getAvailableVehicleAmount() {
        List<Car> carList = vehicleRepository.findAllByIsAvailable(true);
        return carList.size();
    }
    public List<VehicleDto> getAllAvailableCarsWithPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Car> carList = vehicleRepository.findAllByIsAvailable(true, pageable);
        List<VehicleDto> vehicleDtoList = vehicleMapper.carListToDtoList(carList);

        return vehicleDtoList;
    }

    public List<VehicleDto> getAllAvailableCarsWithModel(Integer page, Integer size, String model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Car> carList = vehicleRepository.findAllByCarModelContainingAndIsAvailable(model, true, pageable);
        List<VehicleDto> vehicleDtoList = vehicleMapper.carListToDtoList(carList.getContent());

        return vehicleDtoList;
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
