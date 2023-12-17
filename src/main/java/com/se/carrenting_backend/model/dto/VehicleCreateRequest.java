package com.se.carrenting_backend.model.dto;

import com.se.carrenting_backend.model.enums.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleCreateRequest {
    private String licencePlate;
    private CarType carType;
    private GearType gearType;
    private CarBrand carBrand;
    private String carModel;
    private Engine engine;
    private Integer seats;
    private String img;
}
