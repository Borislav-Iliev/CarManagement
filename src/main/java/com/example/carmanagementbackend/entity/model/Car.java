package com.example.carmanagementbackend.entity.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private int year;

    @Column(name = "license_plate", nullable = false)
    private String licensePlate;

    @OneToMany(targetEntity = Garage.class, fetch = FetchType.EAGER)
    private List<Garage> garages;

    @OneToMany(mappedBy = "car", targetEntity = Maintenance.class, fetch = FetchType.EAGER)
    private List<Maintenance> maintenances;

    public Car() {
    }

    public Car(String make, String model, int year, String licensePlate, List<Garage> garages, List<Maintenance> maintenances) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.garages = garages;
        this.maintenances = maintenances;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public List<Garage> getGarages() {
        return garages;
    }

    public void setGarages(List<Garage> garages) {
        this.garages = garages;
    }

    public List<Maintenance> getMaintenances() {
        return maintenances;
    }

    public void setMaintenances(List<Maintenance> maintenances) {
        this.maintenances = maintenances;
    }
}
