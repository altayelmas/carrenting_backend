package com.se.carrenting_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReservation extends Reservation {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "idNumber")
    private User user;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "licence_plate", referencedColumnName = "licencePlate")
    private Car car;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerReservation that = (CustomerReservation) o;
        return Objects.equals(user, that.user) && Objects.equals(car, that.car) && Objects.equals(getReservationId(), that.getReservationId());
    }
}

