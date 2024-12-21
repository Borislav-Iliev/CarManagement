package com.example.carmanagementbackend.service;

import com.example.carmanagementbackend.entity.dto.garage.AddGarageDto;
import com.example.carmanagementbackend.entity.dto.garage.ResponseGarageDto;

import java.util.List;
import java.util.Optional;

public interface GarageService {

    ResponseGarageDto getGarageById(Long id);

    List<ResponseGarageDto> getAllGarages(Optional<String> city);

    ResponseGarageDto updateGarage(Long id, AddGarageDto addGarageDto);

    boolean deleteGarage(Long id);

    ResponseGarageDto addGarage(AddGarageDto addGarageDto);
}
