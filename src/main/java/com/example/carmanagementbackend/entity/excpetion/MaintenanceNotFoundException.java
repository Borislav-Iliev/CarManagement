package com.example.carmanagementbackend.entity.excpetion;

public class MaintenanceNotFoundException extends RuntimeException {

    public MaintenanceNotFoundException(String message) {
        super(message);
    }
}
