package com.example.carmanagementbackend.entity.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "maintenances")
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_type", nullable = false)
    private String serviceType;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(targetEntity = Car.class, fetch = FetchType.EAGER)
    private Car car;

    @ManyToOne(targetEntity = Garage.class, fetch = FetchType.EAGER)
    private Garage garage;
}
