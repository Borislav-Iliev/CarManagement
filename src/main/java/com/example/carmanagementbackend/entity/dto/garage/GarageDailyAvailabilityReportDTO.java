package com.example.carmanagementbackend.entity.dto.garage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class GarageDailyAvailabilityReportDTO {
    private LocalDate date;
    private long requests;
    private long availableCapacity;

    public GarageDailyAvailabilityReportDTO(LocalDate date, long requests) {
        this.date = date;
        this.requests = requests;
    }
}
