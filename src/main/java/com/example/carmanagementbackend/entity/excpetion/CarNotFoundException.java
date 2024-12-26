package com.example.carmanagementbackend.entity.excpetion;

public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException(String message) {
        super(message);
    }
}
