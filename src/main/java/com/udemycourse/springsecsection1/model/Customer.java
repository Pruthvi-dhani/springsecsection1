package com.udemycourse.springsecsection1.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    @Column(nullable = false)
    private String name;

    @Nonnull
    @Column(nullable = false)
    private String email;

    @Nonnull
    @Column(nullable = false)
    private String role;
}
