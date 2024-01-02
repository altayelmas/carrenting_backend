package com.se.carrenting_backend.controller;

import com.se.carrenting_backend.exception.NotFoundException;
import com.se.carrenting_backend.model.dto.VehicleCreateRequest;
import com.se.carrenting_backend.model.dto.VehicleDto;
import com.se.carrenting_backend.model.dto.VehicleResponse;
import com.se.carrenting_backend.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.InputMismatchException;

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
        try {
            return ResponseEntity.ok(vehicleService.createVehicle(request));
        } catch (InputMismatchException | NumberFormatException exception) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/getAllCars")
    public ResponseEntity<VehicleResponse> getAllVehicles(@RequestParam Integer page,
                                                           @RequestParam Integer size,
                                                           @RequestParam String carBrand,
                                                           @RequestParam String carModel) {
        return new ResponseEntity<>(vehicleService.getAllCars(page, size, carBrand, carModel), HttpStatus.OK);
    }

    @GetMapping("/get/{licencePlate}")
    public ResponseEntity<VehicleResponse> getVehicle(@PathVariable("licencePlate") String licencePlate) {
        try {
            VehicleResponse vehicleResponse = vehicleService.getVehicle(licencePlate);
            vehicleResponse.setMessage(HttpStatus.OK.toString());
            vehicleResponse.setSuccess(true);
            return new ResponseEntity<>(vehicleResponse, HttpStatus.OK);
        } catch (NotFoundException notFoundException) {
            VehicleResponse vehicleResponse = new VehicleResponse();
            vehicleResponse.setVehicleDtoList(new ArrayList<>());
            vehicleResponse.setVehicleAmount(0);
            vehicleResponse.setMessage(notFoundException.getMessage());
            vehicleResponse.setSuccess(false);
            return new ResponseEntity<>(vehicleResponse, HttpStatus.NOT_FOUND);
        }
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
