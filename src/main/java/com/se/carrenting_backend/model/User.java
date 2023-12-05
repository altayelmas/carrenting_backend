package com.se.carrenting_backend.model;

import jakarta.persistence.*;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
    @Id
    private String idNumber;
    private String email;
    private String password;

}
