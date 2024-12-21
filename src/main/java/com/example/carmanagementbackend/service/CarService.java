package com.example.carmanagementbackend.service;

import com.example.carmanagementbackend.entity.dto.car.AddCarDto;
import com.example.carmanagementbackend.entity.dto.car.ResponseCarDto;
import com.example.carmanagementbackend.entity.dto.car.UpdateCarDto;

import java.util.List;
import java.util.Optional;

public interface CarService {

    ResponseCarDto getCarById(Long id);

    List<ResponseCarDto> getAllGarages(Optional<String> make, Optional<Integer> garageId, Optional<Integer> fromYear, Optional<Integer> toYear);

    ResponseCarDto updateCar(Long id, UpdateCarDto updateCarDto);

    boolean deleteCar(Long id);

    ResponseCarDto addCar(AddCarDto addCarDto);
}
