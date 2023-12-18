package com.se.carrenting_backend.controller;

import com.se.carrenting_backend.model.dto.VehicleCreateRequest;
import com.se.carrenting_backend.model.dto.VehicleDto;
import com.se.carrenting_backend.model.dto.VehicleResponse;
import com.se.carrenting_backend.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
@CrossOrigin("http://localhost:3000")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/create")
    public ResponseEntity<VehicleDto> createVehicle(@RequestBody VehicleCreateRequest request) {
        return ResponseEntity.ok(vehicleService.createVehicle(request));
    }

    @GetMapping("/getAll/{page}/{size}")
    public ResponseEntity<List<VehicleDto>> getAllVehicles(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        return new ResponseEntity<>(vehicleService.getAll(page, size), HttpStatus.OK);
    }

    @GetMapping("/get/{licencePlate}")
    public ResponseEntity<VehicleDto> getVehicle(@PathVariable("licencePlate") String licencePlate) {
        return null;
    }

    @GetMapping("/getAllAvailableCars")
    public ResponseEntity<VehicleResponse> getAllAvailableCars(@RequestParam Integer page,
                                                 @RequestParam Integer size,
                                                 @RequestParam String carBrand,
                                                 @RequestParam String carModel) {

        VehicleResponse vehicleResponse = vehicleService.getAllAvailableCars(page, size, carModel, carBrand);
        return new ResponseEntity<>(vehicleResponse, HttpStatus.OK);
    }


}
