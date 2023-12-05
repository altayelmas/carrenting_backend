package com.se.carrenting_backend.controller;

import com.se.carrenting_backend.model.dto.VehicleCreateRequest;
import com.se.carrenting_backend.model.dto.VehicleDto;
import com.se.carrenting_backend.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/create")
    public ResponseEntity<VehicleDto> createVehicle(@RequestBody VehicleCreateRequest request) {
        return ResponseEntity.ok(vehicleService.createVehicle(new VehicleCreateRequest()));
    }

    @GetMapping("/getAll")
    public List<ResponseEntity<VehicleDto>> getAllVehicles() {
        return null;
    }

    @GetMapping("/get/{licencePlate}")
    public ResponseEntity<VehicleDto> getVehicle(@PathVariable("licencePlate") String licencePlate) {
        return null;
    }
}
