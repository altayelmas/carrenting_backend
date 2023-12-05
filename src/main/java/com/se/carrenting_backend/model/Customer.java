package com.se.carrenting_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends User {
    @OneToOne
    private Address address;
    @OneToMany
    private List<CustomerReservation> reservationList;
}
