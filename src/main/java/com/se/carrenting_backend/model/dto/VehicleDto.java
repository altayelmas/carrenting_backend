package com.se.carrenting_backend.model.dto;

import com.se.carrenting_backend.model.enums.CarBrand;
import com.se.carrenting_backend.model.enums.CarType;
import com.se.carrenting_backend.model.enums.Engine;
import com.se.carrenting_backend.model.enums.GearType;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Builder
public class VehicleDto {
    private String licencePlate;
    private CarType carType;
    private CarBrand carBrand;
    private String carModel;
    private GearType gearType;
    private Engine engine;
    private Integer price;
    private Integer seats;
    private boolean isAvailable;
    private String img;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleDto that = (VehicleDto) o;
        return isAvailable == that.isAvailable && Objects.equals(licencePlate, that.licencePlate) && carType == that.carType && carBrand == that.carBrand && Objects.equals(carModel, that.carModel) && gearType == that.gearType && engine == that.engine && Objects.equals(price, that.price) && Objects.equals(seats, that.seats) && Objects.equals(img, that.img);
    }
}
