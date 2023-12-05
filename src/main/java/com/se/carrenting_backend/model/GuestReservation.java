package com.se.carrenting_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GuestReservation extends Reservation {
    private String email;
    private String idNumber;
    private String country;
    private String city;
    private String street;
    private String postCode;
    private String phoneNumber;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "licence_plate", referencedColumnName = "licencePlate")
    private Car car;
}

