package com.se.carrenting_backend.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleCreateRequest {
    private String licencePlate;
}
