package com.se.carrenting_backend.model.dto;

import com.se.carrenting_backend.model.enums.CarBrand;
import com.se.carrenting_backend.model.enums.CarType;
import com.se.carrenting_backend.model.enums.GearType;
import lombok.*;

@Getter
@Setter
@Builder
public class VehicleDto {
    private String licencePlate;
    private CarType carType;
    private CarBrand carBrand;
    private String carModel;
    private GearType gearType;
    private Integer seats;
    private boolean isAvailable;
    private String img;
}
