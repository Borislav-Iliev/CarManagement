package com.example.carmanagementbackend.entity.dto.maintenance;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class UpdateMaintenanceDTO {
    @NotNull(message = "Maintenance car id must be provided!")
    private Long carId;

    @NotBlank(message = "Maintenance service type must be provided!")
    @Size(min = 2, max = 20, message = "Maintenance service type must be between 2 and 20 characters!")
    private String serviceType;

    @NotNull(message = "Maintenance scheduled date must be provided!")
    @FutureOrPresent(message = "Maintenance scheduled date cannot be in the past!")
    private LocalDate scheduledDate;

    @NotNull(message = "Maintenance garage id must be provided!")
    private Long garageId;

    public UpdateMaintenanceDTO() {
    }

    public Long getCarId() {
        return carId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public Long getGarageId() {
        return garageId;
    }
}
