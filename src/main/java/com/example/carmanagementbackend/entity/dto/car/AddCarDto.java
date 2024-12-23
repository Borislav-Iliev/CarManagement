package com.example.carmanagementbackend.entity.dto.car;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class AddCarDto {

    @NotBlank(message = "Car make must be provided!")
    @Size(min = 2, max = 20, message = "Car make must be between 2 and 20 characters!")
    private String make;

    @NotBlank(message = "Car model must be provided!")
    @Size(min = 2, max = 20, message = "Car model must be between 2 and 20 characters!")
    private String model;

    @Positive(message = "Car production year must be positive!")
    private int productionYear;

    @NotBlank(message = "Car license plate must be provided!")
    @Size(min = 2, max = 20, message = "Car license plate must be between 2 and 20 characters!")
    private String licensePlate;

    @NotNull(message = "Car garage ids must be provided!")
    private List<Long> garageIds;

    public AddCarDto() {
        this.garageIds = new ArrayList<>();
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public List<Long> getGarageIds() {
        return garageIds;
    }
}
