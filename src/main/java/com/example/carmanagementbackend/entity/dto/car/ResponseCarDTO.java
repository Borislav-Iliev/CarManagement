package com.example.carmanagementbackend.entity.dto.car;

import com.example.carmanagementbackend.entity.dto.garage.ResponseGarageDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseCarDTO {
    private long id;
    private String make;
    private String model;
    @JsonProperty(value = "productionYear")
    private int year;
    private String licensePlate;
    private List<ResponseGarageDTO> garages;
}
