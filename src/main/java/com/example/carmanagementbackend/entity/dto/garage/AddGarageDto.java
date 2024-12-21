package com.example.carmanagementbackend.entity.dto.garage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class AddGarageDto {

    @NotBlank(message = "Garage name must be provided!")
    @Size(min = 2, max = 20, message = "Garage name must be between 2 and 20 characters!")
    private String name;

    @NotBlank(message = "Garage location must be provided!")
    @Size(min = 2, max = 30, message = "Garage location must be between 2 and 30 characters!")
    private String location;

    @NotBlank(message = "Garage city must be provided!")
    @Size(min = 2, max = 30, message = "Garage name must be between 2 and 30 characters!")
    private String city;

    @Positive(message = "Garage capacity must be positive!")
    private int capacity;

    public AddGarageDto() {
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getCity() {
        return city;
    }

    public int getCapacity() {
        return capacity;
    }
}
