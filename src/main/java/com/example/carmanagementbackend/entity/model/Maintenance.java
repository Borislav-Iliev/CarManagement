package com.example.carmanagementbackend.entity.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "maintenances")
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "service_type", nullable = false)
    private String serviceType;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(targetEntity = Car.class, fetch = FetchType.EAGER)
    private Car car;

    @ManyToOne(targetEntity = Garage.class, fetch = FetchType.EAGER)
    private Garage garage;

    public Maintenance() {
    }

    public Maintenance(String serviceType, String type, LocalDate date, Car car, Garage garage) {
        this.serviceType = serviceType;
        this.type = type;
        this.date = date;
        this.car = car;
        this.garage = garage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }
}
