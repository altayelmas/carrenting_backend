package com.se.carrenting_backend.mapper;

import com.se.carrenting_backend.model.Car;
import com.se.carrenting_backend.model.dto.VehicleDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VehicleMapper {

    public VehicleDto convertToDto(Car car) {
        return VehicleDto.builder()
                .licencePlate(car.getLicencePlate())
                .carType(car.getCarType())
                .carBrand(car.getCarBrand())
                .carModel(car.getCarModel())
                .gearType(car.getGearType())
                .seats(car.getSeats())
                .img(car.getImg())
                .isAvailable(car.isAvailable())
                .build();
    }

    public List<VehicleDto> carListToDtoList(List<Car> carList) {
        List<VehicleDto> vehicleDtoList = new ArrayList<>();
        for (Car car: carList) {
            vehicleDtoList.add(convertToDto(car));
        }
        return vehicleDtoList;
    }
}
