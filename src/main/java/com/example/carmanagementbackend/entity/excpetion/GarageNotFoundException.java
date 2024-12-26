package com.example.carmanagementbackend.entity.excpetion;

public class GarageNotFoundException extends RuntimeException {
    public GarageNotFoundException(String message) {
        super(message);
    }
}
