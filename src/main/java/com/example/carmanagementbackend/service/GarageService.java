package com.example.carmanagementbackend.service;

import com.example.carmanagementbackend.entity.dto.garage.AddGarageDto;
import com.example.carmanagementbackend.entity.model.Garage;

import java.util.List;
import java.util.Optional;

public interface GarageService {

    Garage getGarageById(Long id);

    List<Garage> getAllGarages(Optional<String> city);

    Garage updateGarage(Long id, AddGarageDto addGarageDto);

    boolean deleteGarage(Long id);

    Garage addGarage(AddGarageDto addGarageDto);
}
