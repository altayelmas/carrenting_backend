package com.se.carrenting_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReservation extends Reservation {

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "idNumber")
    private Customer customer;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "licence_plate", referencedColumnName = "licencePlate")
    private Car car;
}

