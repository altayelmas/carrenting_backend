package com.se.carrenting_backend.model;

import com.se.carrenting_backend.model.enums.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    @Id
    private String licencePlate;
    private CarType carType;
    private GearType gearType;
    private CarBrand carBrand;
    private Engine engine;
    private Integer seats;
    private boolean isAvailable;
    private String img;
    @OneToMany
    private List<CustomerReservation> customerReservationList;
    @OneToMany
    private List<GuestReservation> guestReservationList;
}
