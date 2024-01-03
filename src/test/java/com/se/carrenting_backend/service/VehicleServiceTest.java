package com.se.carrenting_backend.service;

import com.se.carrenting_backend.Support.VehicleServiceTestSupport;
import com.se.carrenting_backend.mapper.VehicleMapper;
import com.se.carrenting_backend.model.Car;
import com.se.carrenting_backend.model.dto.VehicleCreateRequest;
import com.se.carrenting_backend.model.dto.VehicleDto;
import com.se.carrenting_backend.model.dto.VehicleResponse;
import com.se.carrenting_backend.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.InputMismatchException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VehicleServiceTest {
    private VehicleService vehicleService;
    private VehicleRepository vehicleRepository;
    private VehicleMapper vehicleMapper;

    @BeforeEach
    public void setUp() {
        vehicleRepository = mock(VehicleRepository.class);
        vehicleMapper = mock(VehicleMapper.class);

        vehicleService = new VehicleService(vehicleRepository, vehicleMapper);
    }

    @Test
    public void createVehicleTest() {
        VehicleCreateRequest vehicleCreateRequest = VehicleServiceTestSupport.generateVehicleRequest("34ABC1");
        Car car = VehicleServiceTestSupport.generateCar("34ABC1");
        VehicleDto vehicleDto = VehicleServiceTestSupport.generateVehicleDto("34ABC1");
        when(vehicleRepository.save(any())).thenReturn(car);
        when(vehicleMapper.convertToDto(car)).thenReturn(vehicleDto);

        VehicleDto result = vehicleService.createVehicle(vehicleCreateRequest).getVehicleDtoList().get(0);
        assertEquals(vehicleDto, result);
    }

    @Test
    public void createVehicleTest_whenLicencePlateIsShorterThanSixCharacters() {
        VehicleCreateRequest vehicleCreateRequest = VehicleServiceTestSupport.generateVehicleRequest("34AB1");
        assertThrows(InputMismatchException.class, () -> vehicleService.createVehicle(vehicleCreateRequest));
        verify(vehicleRepository).existsById("34AB1");
        verifyNoInteractions(vehicleMapper);
    }

    @Test
    public void createVehicleTest_whenLicencePlateIsNotValid() {
        VehicleCreateRequest vehicleCreateRequest = VehicleServiceTestSupport.generateVehicleRequest("82ABC1");
        assertThrows(InputMismatchException.class, () -> vehicleService.createVehicle(vehicleCreateRequest));
        verify(vehicleRepository).existsById("82ABC1");
        verifyNoInteractions(vehicleMapper);
    }

    @Test
    public void createVehicleTest_whenLicencePlateStartIsNotValid() {
        VehicleCreateRequest vehicleCreateRequest = VehicleServiceTestSupport.generateVehicleRequest("82ABC123");
        assertThrows(InputMismatchException.class, () -> vehicleService.createVehicle(vehicleCreateRequest));
        verify(vehicleRepository).existsById("82ABC123");
        verifyNoInteractions(vehicleMapper);
    }

    @Test
    public void getAllCars() {
        List<Car> carList = VehicleServiceTestSupport.generateCarList(3);
        List<VehicleDto> vehicleDtoList = VehicleServiceTestSupport.generateVehicleDtoList(3);
        when(vehicleRepository.findAllByCarModelContaining("", PageRequest.of(0, 3)))
                .thenReturn(new PageImpl<>(carList, PageRequest.of(0, 3), carList.size()));
        when(vehicleMapper.carListToDtoList(carList)).thenReturn(vehicleDtoList);
        VehicleResponse vehicleResponse = VehicleServiceTestSupport.generateVehicleResponse(3);

        VehicleResponse result = vehicleService.getAllCars(0, 3, "", "");
        assertEquals(vehicleResponse.getVehicleDtoList(), result.getVehicleDtoList());
        verify(vehicleRepository).findAllByCarModelContaining("", PageRequest.of(0, 3));
        verify(vehicleMapper).carListToDtoList(carList);
    }
}
