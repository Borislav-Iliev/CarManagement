package com.example.carmanagementbackend.service.impl;

import com.example.carmanagementbackend.entity.dto.car.AddCarDto;
import com.example.carmanagementbackend.entity.dto.car.ResponseCarDto;
import com.example.carmanagementbackend.entity.dto.car.UpdateCarDto;
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

    private final CarRepository carRepository;
    private final GarageRepository garageRepository;
    private final ModelMapper modelMapper;

    public CarServiceImpl(CarRepository carRepository, GarageRepository garageRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.garageRepository = garageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseCarDto getCarById(Long id) {
        Car car = this.carRepository
                .findById(id)
                .orElseThrow();

        return this.modelMapper.map(car, ResponseCarDto.class);
    }

    @Override
    public List<ResponseCarDto> getAllGarages(Optional<String> make, Optional<Integer> garageId,
                                   Optional<Integer> fromYear, Optional<Integer> toYear) {
        if (make.isPresent()) {
            return this.carRepository.findAllByMakeContainsIgnoreCase(make.get())
                    .stream().map(car -> this.modelMapper.map(car, ResponseCarDto.class)).toList();
        }

        if (garageId.isPresent()) {
            Garage garage = this.garageRepository
                    .findById((long) garageId.get())
                    .orElseThrow();

            return this.carRepository.findAllByGaragesContains(List.of(garage))
                    .stream().map(car -> this.modelMapper.map(car, ResponseCarDto.class)).toList();
        }

        if (fromYear.isPresent()) {
            return this.carRepository.findAllByYearGreaterThanEqual(fromYear.get())
                    .stream().map(car -> this.modelMapper.map(car, ResponseCarDto.class)).toList();
        }

        if (toYear.isPresent()) {
            return this.carRepository.findAllByYearLessThanEqual(toYear.get())
                    .stream().map(car -> this.modelMapper.map(car, ResponseCarDto.class)).toList();
        }

        return this.carRepository.findAll()
                .stream().map(car -> this.modelMapper.map(car, ResponseCarDto.class)).toList();
    }

    @Override
    public ResponseCarDto updateCar(Long id, UpdateCarDto updateCarDto) {
        Car carById = this.carRepository
                .findById(id)
                .orElseThrow();

        carById.setMake(updateCarDto.getMake());
        carById.setModel(updateCarDto.getModel());
        carById.setYear(updateCarDto.getProductionYear());
        carById.setLicensePlate(updateCarDto.getLicensePlate());

        List<Garage> garages = this.garageRepository.findAllById(updateCarDto.getGarageIds());
        carById.setGarages(garages);

        this.carRepository.save(carById);

        return this.modelMapper.map(carById, ResponseCarDto.class);
    }

    @Override
    public boolean deleteCar(Long id) {
        this.carRepository.deleteById(id);
        return this.carRepository.existsById(id);
    }

    @Override
    public ResponseCarDto addCar(AddCarDto addCarDto) {
        if (addCarDto == null) {
            throw new IllegalArgumentException("Invalid car entity!");
        }

        Car car = this.modelMapper.map(addCarDto, Car.class);

        List<Garage> garages = this.garageRepository.findAllById(addCarDto.getGarageIds());
        car.setGarages(garages);

        car = this.carRepository.save(car);
        return this.modelMapper.map(car, ResponseCarDto.class);
    }
}
