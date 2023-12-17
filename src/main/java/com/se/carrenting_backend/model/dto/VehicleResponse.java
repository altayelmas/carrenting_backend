package com.se.carrenting_backend.model.dto;

import jdk.jfr.Name;
import lombok.*;

import java.util.List;

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
}
