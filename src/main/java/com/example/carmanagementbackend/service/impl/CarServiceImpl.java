package com.example.carmanagementbackend.service.impl;

import com.example.carmanagementbackend.entity.dto.car.AddCarDTO;
import com.example.carmanagementbackend.entity.dto.car.ResponseCarDTO;
import com.example.carmanagementbackend.entity.dto.car.UpdateCarDTO;
import com.example.carmanagementbackend.entity.model.Car;
import com.example.carmanagementbackend.entity.model.Garage;
import com.example.carmanagementbackend.repository.CarRepository;
import com.example.carmanagementbackend.repository.GarageRepository;
import com.example.carmanagementbackend.service.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    private static final String CAR_NOT_FOUND = "Car not found!";
    private static final String CAR_CANNOT_BE_NULL = "Car cannot be null!";

    private final CarRepository carRepository;
    private final GarageRepository garageRepository;
    private final ModelMapper modelMapper;

    public CarServiceImpl(CarRepository carRepository, GarageRepository garageRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.garageRepository = garageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseCarDTO getCarById(Long id) {
        Car car = this.carRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(CAR_NOT_FOUND));

        return this.modelMapper.map(car, ResponseCarDTO.class);
    }

    @Override
    public List<ResponseCarDTO> getAllGarages(Optional<String> make, Optional<Integer> garageId,
                                              Optional<Integer> fromYear, Optional<Integer> toYear) {
        if (make.isPresent()) {
            return this.carRepository.findAllByMakeContainsIgnoreCase(make.get())
                    .stream().map(car -> this.modelMapper.map(car, ResponseCarDTO.class)).toList();
        }

        if (garageId.isPresent()) {
            Garage garage = this.garageRepository
                    .findById((long) garageId.get())
                    .orElseThrow();

            return this.carRepository.findAllByGaragesContains(List.of(garage))
                    .stream().map(car -> this.modelMapper.map(car, ResponseCarDTO.class)).toList();
        }

        if (fromYear.isPresent()) {
            return this.carRepository.findAllByYearGreaterThanEqual(fromYear.get())
                    .stream().map(car -> this.modelMapper.map(car, ResponseCarDTO.class)).toList();
        }

        if (toYear.isPresent()) {
            return this.carRepository.findAllByYearLessThanEqual(toYear.get())
                    .stream().map(car -> this.modelMapper.map(car, ResponseCarDTO.class)).toList();
        }

        return this.carRepository.findAll()
                .stream().map(car -> this.modelMapper.map(car, ResponseCarDTO.class)).toList();
    }

    @Override
    public ResponseCarDTO updateCar(Long id, UpdateCarDTO updateCarDTO) {
        Car carById = this.carRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(CAR_NOT_FOUND));

        setCarFields(updateCarDTO, carById);

        List<Garage> garages = this.garageRepository.findAllById(updateCarDTO.getGarageIds());
        carById.setGarages(garages);

        this.carRepository.save(carById);

        return this.modelMapper.map(carById, ResponseCarDTO.class);
    }

    private void setCarFields(UpdateCarDTO updateCarDTO, Car carById) {
        carById.setMake(updateCarDTO.getMake());
        carById.setModel(updateCarDTO.getModel());
        carById.setYear(updateCarDTO.getProductionYear());
        carById.setLicensePlate(updateCarDTO.getLicensePlate());
    }

    @Override
    public boolean deleteCar(Long id) {
        this.carRepository.deleteById(id);
        return this.carRepository.existsById(id);
    }

    @Override
    public ResponseCarDTO addCar(AddCarDTO addCarDTO) {
        if (addCarDTO == null) {
            throw new IllegalArgumentException(CAR_CANNOT_BE_NULL);
        }

        Car car = this.modelMapper.map(addCarDTO, Car.class);

        List<Garage> garages = this.garageRepository.findAllById(addCarDTO.getGarageIds());
        car.setGarages(garages);

        car = this.carRepository.save(car);
        return this.modelMapper.map(car, ResponseCarDTO.class);
    }
}
