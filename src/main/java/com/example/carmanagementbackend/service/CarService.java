package com.example.carmanagementbackend.service;

import com.example.carmanagementbackend.entity.dto.car.AddCarDTO;
import com.example.carmanagementbackend.entity.dto.car.ResponseCarDTO;
import com.example.carmanagementbackend.entity.dto.car.UpdateCarDTO;

import java.util.List;
import java.util.Optional;

public interface CarService {

    ResponseCarDTO getCarById(Long id);

    List<ResponseCarDTO> getAllGarages(Optional<String> make, Optional<Integer> garageId, Optional<Integer> fromYear, Optional<Integer> toYear);

    ResponseCarDTO updateCar(Long id, UpdateCarDTO updateCarDTO);

    boolean deleteCar(Long id);

    ResponseCarDTO addCar(AddCarDTO addCarDTO);
}
