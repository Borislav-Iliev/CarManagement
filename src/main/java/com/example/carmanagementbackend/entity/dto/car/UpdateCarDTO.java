package com.example.carmanagementbackend.entity.dto.car;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCarDTO {

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
}
