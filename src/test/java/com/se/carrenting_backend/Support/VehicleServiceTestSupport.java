package com.se.carrenting_backend.Support;

import com.se.carrenting_backend.model.Car;
import com.se.carrenting_backend.model.dto.VehicleCreateRequest;
import com.se.carrenting_backend.model.dto.VehicleDto;
import com.se.carrenting_backend.model.dto.VehicleResponse;
import com.se.carrenting_backend.model.enums.CarType;
import com.se.carrenting_backend.model.enums.Engine;
import com.se.carrenting_backend.model.enums.GearType;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class VehicleServiceTestSupport {
    public static VehicleCreateRequest generateVehicleRequest(String licencePlate) {
        return VehicleCreateRequest.builder()
                .licencePlate(licencePlate)
                .carType(CarType.Sedan)
                .gearType(GearType.Manual)
                .carModel("Polo")
                .engine(Engine.Placeholder1)
                .price(10)
                .seats(4)
                .img("")
                .build();
    }

    public static Car generateCar(String licencePlate) {
        return Car.builder()
                .licencePlate(licencePlate)
                .carType(CarType.Sedan)
                .gearType(GearType.Manual)
                .carModel("Polo")
                .engine(Engine.Placeholder1)
                .price(10)
                .seats(4)
                .isAvailable(true)
                .img("")
                .customerReservationList(new ArrayList<>())
                .guestReservationList(new ArrayList<>())
                .build();
    }

    public static VehicleDto generateVehicleDto(String licencePlate) {
        return VehicleDto.builder()
                .licencePlate(licencePlate)
                .carType(CarType.Sedan)
                .gearType(GearType.Manual)
                .carModel("Polo")
                .engine(Engine.Placeholder1)
                .price(10)
                .seats(4)
                .isAvailable(true)
                .img("")
                .build();
    }

    public static List<Car> generateCarList(Integer amount) {
        List<Car> carList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            carList.add(Car.builder()
                    .licencePlate("34ABC" + i + 1)
                    .carType(CarType.SUV)
                    .gearType(GearType.Automatic)
                    .carModel("G")
                    .engine(Engine.Placeholder2)
                    .price(20)
                    .seats(6)
                    .isAvailable(true)
                    .img("")
                    .build());
        }
        return carList;
    }

    public static List<VehicleDto> generateVehicleDtoList(Integer amount) {
        List<VehicleDto> vehicleDtoList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            vehicleDtoList.add(VehicleDto.builder()
                    .licencePlate("34ABC" + i + 1)
                    .carType(CarType.SUV)
                    .gearType(GearType.Automatic)
                    .carModel("G")
                    .engine(Engine.Placeholder2)
                    .price(20)
                    .seats(6)
                    .isAvailable(true)
                    .img("")
                    .build());
        }
        return vehicleDtoList;
    }

    public static VehicleResponse generateVehicleResponse(Integer amount) {
        return VehicleResponse.builder()
                .vehicleDtoList(generateVehicleDtoList(amount))
                .vehicleAmount(amount)
                .isSuccess(true)
                .message(HttpStatus.OK.toString())
                .build();
    }
}
