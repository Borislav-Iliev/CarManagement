package com.example.carmanagementbackend.entity.dto.garage;

public class ResponseGarageDto {
    private long id;
    private String name;
    private String location;
    private String city;
    private int capacity;

    public ResponseGarageDto() {
    }

    public long getId() {
        return id;
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
