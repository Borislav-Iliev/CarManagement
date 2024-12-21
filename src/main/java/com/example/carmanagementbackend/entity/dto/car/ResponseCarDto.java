package com.example.carmanagementbackend.entity.dto.car;

import com.example.carmanagementbackend.entity.dto.garage.ResponseGarageDto;

import java.util.List;

public class ResponseCarDto {
    private long id;
    private String make;
    private String model;
    private int productionYear;
    private String licensePlate;
    private List<ResponseGarageDto> garages;

    public ResponseCarDto() {
    }

    public long getId() {
        return id;
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

    public List<ResponseGarageDto> getGarages() {
        return garages;
    }
}
