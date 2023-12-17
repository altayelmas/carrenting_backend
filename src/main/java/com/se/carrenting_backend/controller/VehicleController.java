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

    /*@GetMapping("/getByAmount/{amount}")
    public ResponseEntity<List<VehicleDto>> getVehiclesByAmount(@PathVariable("amount") Integer amount) {
        return new ResponseEntity<>(vehicleService.getVehiclesByAmount(amount), HttpStatus.OK);
    }
*/
    @GetMapping("/getAllAvailableCars/{page}/{size}")
    public ResponseEntity<VehicleResponse> getAllAvailableCars(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        List<VehicleDto> vehicleDtoList = vehicleService.getAllAvailableCarsWithPage(page, size);
        VehicleResponse vehicleResponse = VehicleResponse.builder()
                .vehicleDtoList(vehicleDtoList)
                .vehicleAmount(vehicleService.getAvailableVehicleAmount())
                .isSuccess(true)
                .message(HttpStatus.OK.toString())
                .build();
        return new ResponseEntity<>(vehicleResponse, HttpStatus.OK);
    }

    @GetMapping("/getAllAvailableCars/{page}/{size}/{model}")
    public ResponseEntity<VehicleResponse> getAllAvailableCarsWithModel(@PathVariable("page") Integer page, @PathVariable("size") Integer size,
                                                                        @PathVariable("model") String model) {

        List<VehicleDto> vehicleDtoList = vehicleService.getAllAvailableCarsWithModel(page, size, model);
        VehicleResponse vehicleResponse = VehicleResponse.builder()
                .vehicleDtoList(vehicleDtoList)
                .vehicleAmount(vehicleService.getAmountOfAvailableCarsWithModel(model))
                .isSuccess(true)
                .message(HttpStatus.OK.toString())
                .build();
        return new ResponseEntity<>(vehicleResponse, HttpStatus.OK);
    }

    @GetMapping("/getAllCarsWithBrand/{page}/{size}/{brand}")
    public ResponseEntity<VehicleResponse> getAllAvailableCarsWithBrand (@PathVariable("page") Integer page, @PathVariable("size") Integer size,
                                                                         @PathVariable("brand") String brand) {
        List<VehicleDto> vehicleDtoList = vehicleService.getAllAvailableCarsWithBrand(page, size, brand);
        VehicleResponse vehicleResponse = VehicleResponse.builder()
                .vehicleDtoList(vehicleDtoList)
                .vehicleAmount(vehicleService.getAmountOfAvailableCarsWithBrand(brand))
                .isSuccess(true)
                .message(HttpStatus.OK.toString())
                .build();
        return new ResponseEntity<>(vehicleResponse, HttpStatus.OK);
    }

    @GetMapping("/getAllAvailableCars/{page}/{size}/{model}/{brand}")
    public ResponseEntity<VehicleResponse> getAllAvailableCarsWithModelAndBrand(@PathVariable("page") Integer page, @PathVariable("size") Integer size,
                                                                        @PathVariable("model") String model, @PathVariable("brand") String brand) {

        List<VehicleDto> vehicleDtoList = vehicleService.getAllAvailableCarsWithModelAndBrand(page, size, model, brand);
        VehicleResponse vehicleResponse = VehicleResponse.builder()
                .vehicleDtoList(vehicleDtoList)
                .vehicleAmount(vehicleService.getAmountOfAvailableCarsWithModelAndBrand(model, brand))
                .isSuccess(true)
                .message(HttpStatus.OK.toString())
                .build();
        return new ResponseEntity<>(vehicleResponse, HttpStatus.OK);
    }



}
