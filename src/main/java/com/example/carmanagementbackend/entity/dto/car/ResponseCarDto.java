package com.example.carmanagementbackend.entity.dto.car;

import com.example.carmanagementbackend.entity.dto.garage.ResponseGarageDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ResponseCarDto {
    private long id;
    private String make;
    private String model;
    @JsonProperty(value = "productionYear")
    private int year;
    private String licensePlate;
    private List<ResponseGarageDto> garages;

    public ResponseCarDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public List<ResponseGarageDto> getGarages() {
        return garages;
    }

    public void setGarages(List<ResponseGarageDto> garages) {
        this.garages = garages;
    }
}
