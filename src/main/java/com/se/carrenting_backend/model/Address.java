package com.se.carrenting_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;
    private String country;
    private String city;
    private String street;
    private String postCode;
    @OneToOne
    @JoinColumn(name = "id_number", referencedColumnName = "idNumber")
    private User user;
}
