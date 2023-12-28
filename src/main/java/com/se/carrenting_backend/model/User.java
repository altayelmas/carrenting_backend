package com.se.carrenting_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idNumber;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;
    private String password;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Address address;
    @OneToMany
    private List<CustomerReservation> reservationList;
    private String roles;
}
