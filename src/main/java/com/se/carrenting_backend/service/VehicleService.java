package com.se.carrenting_backend.service;

import com.se.carrenting_backend.mapper.VehicleMapper;
import com.se.carrenting_backend.model.Car;
import com.se.carrenting_backend.model.dto.VehicleCreateRequest;
import com.se.carrenting_backend.model.dto.VehicleDto;
import com.se.carrenting_backend.repository.VehicleRepository;
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

    public List<VehicleDto> getAll() {
        List<Car> carList = vehicleRepository.findAll();
        List<VehicleDto> vehicleDtoList = vehicleMapper.carListToDtoList(carList);

        return vehicleDtoList;
    }

    public VehicleDto createVehicle(VehicleCreateRequest request) {
            Car car = Car.builder()
                    .licencePlate(request.getLicencePlate())
                    .carType(request.getCarType())
                    .gearType(request.getGearType())
                    .carBrand(request.getCarBrand())
                    .engine(request.getEngine())
                    .seats(request.getSeats())
                    .isAvailable(true)
                    .img(request.getImg())
                    .customerReservationList(new ArrayList<>())
                    .guestReservationList(new ArrayList<>())
                    .build();
            return vehicleMapper.convertToDto(vehicleRepository.save(car));
    }

    public List<VehicleDto> getVehiclesByAmount(Integer amount) {
        List<Car> carList = vehicleRepository.findAll();
        List<VehicleDto> vehicleDtoList = new ArrayList<>();

        if (amount <= carList.size()) {
            for (int i = 0; i < amount; i++) {
                vehicleDtoList.add(vehicleMapper.convertToDto(carList.get(i)));
            }
        } else {
            for (int i = 0; i < carList.size(); i++) {
                vehicleDtoList.add(vehicleMapper.convertToDto(carList.get(i)));
            }
        }
        return vehicleDtoList;
    }
}
