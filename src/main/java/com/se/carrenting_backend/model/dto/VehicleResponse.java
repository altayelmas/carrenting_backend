package com.se.carrenting_backend.model.dto;

import jdk.jfr.Name;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponse {
    private List<VehicleDto> vehicleDtoList;
    private Integer vehicleAmount;
    private boolean isSuccess;
    private String message;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleResponse that = (VehicleResponse) o;
        return isSuccess == that.isSuccess && Objects.equals(vehicleDtoList, that.vehicleDtoList) && Objects.equals(vehicleAmount, that.vehicleAmount) && Objects.equals(message, that.message);
    }

}
