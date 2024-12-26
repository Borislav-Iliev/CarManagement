package com.example.carmanagementbackend.entity.dto.maintenance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ResponseMaintenanceDTO {
    private Long id;
    private Long carId;
    @JsonIgnore
    private String carMake;
    @JsonIgnore
    private String carModel;
    private String carName;
    private String serviceType;
    @JsonProperty(value = "scheduledDate")
    private LocalDate date;
    private Long garageId;
    private String garageName;

    public String getCarName() {
        return this.getCarMake() + " " + this.getCarModel();
    }
}
