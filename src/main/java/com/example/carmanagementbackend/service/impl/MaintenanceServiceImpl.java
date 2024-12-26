package com.example.carmanagementbackend.service.impl;

import com.example.carmanagementbackend.entity.dto.maintenance.ResponseMaintenanceDTO;
import com.example.carmanagementbackend.entity.dto.maintenance.UpdateMaintenanceDTO;
import com.example.carmanagementbackend.entity.model.Car;
import com.example.carmanagementbackend.entity.model.Garage;
import com.example.carmanagementbackend.entity.model.Maintenance;
import com.example.carmanagementbackend.repository.CarRepository;
import com.example.carmanagementbackend.repository.GarageRepository;
import com.example.carmanagementbackend.repository.MaintenanceRepository;
import com.example.carmanagementbackend.service.MaintenanceService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final CarRepository carRepository;
    private final GarageRepository garageRepository;
    private final ModelMapper modelMapper;

    public MaintenanceServiceImpl(MaintenanceRepository maintenanceRepository, CarRepository carRepository, GarageRepository garageRepository, ModelMapper modelMapper) {
        this.maintenanceRepository = maintenanceRepository;
        this.carRepository = carRepository;
        this.garageRepository = garageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseMaintenanceDTO getMaintenanceById(Long id) {
        Maintenance maintenance = this.maintenanceRepository
                .findById(id)
                .orElseThrow();

        return this.modelMapper.map(maintenance, ResponseMaintenanceDTO.class);
    }

    @Override
    public List<ResponseMaintenanceDTO> getAllMaintenances(Optional<Long> carId, Optional<Long> garageId, Optional<LocalDate> startDate, Optional<LocalDate> endDate) {
        if (carId.isPresent()) {
            return this.maintenanceRepository.getAllByCarId(carId.get())
                    .stream().map(m -> this.modelMapper.map(m, ResponseMaintenanceDTO.class)).toList();
        }

        if (garageId.isPresent()) {
            return this.maintenanceRepository.getAllByGarageId(garageId.get())
                    .stream().map(m -> this.modelMapper.map(m, ResponseMaintenanceDTO.class)).toList();
        }

        if (startDate.isPresent()) {
            return this.maintenanceRepository.getAllByDateGreaterThanEqual(startDate.get())
                    .stream().map(m -> this.modelMapper.map(m, ResponseMaintenanceDTO.class)).toList();
        }

        if (endDate.isPresent()) {
            return this.maintenanceRepository.getAllByDateLessThanEqual(endDate.get())
                    .stream().map(m -> this.modelMapper.map(m, ResponseMaintenanceDTO.class)).toList();
        }

        return this.maintenanceRepository.findAll()
                .stream().map(m -> this.modelMapper.map(m, ResponseMaintenanceDTO.class)).toList();
    }

    @Override
    public ResponseMaintenanceDTO updateMaintenance(Long id, UpdateMaintenanceDTO updateMaintenanceDTO) {
        if (updateMaintenanceDTO == null) {
            throw new IllegalArgumentException("Invalid maintenance entity!");
        }

        Maintenance maintenance = this.maintenanceRepository
                .findById(id)
                .orElseThrow();

        maintenance.setServiceType(updateMaintenanceDTO.getServiceType());
        maintenance.setDate(updateMaintenanceDTO.getScheduledDate());

        Car car = this.carRepository.findById(updateMaintenanceDTO.getCarId()).orElseThrow();
        maintenance.setCar(car);

        Garage garage = this.garageRepository.findById(updateMaintenanceDTO.getGarageId()).orElseThrow();
        maintenance.setGarage(garage);

        maintenance = this.maintenanceRepository.save(maintenance);

        return this.modelMapper.map(maintenance, ResponseMaintenanceDTO.class);
    }

    @Override
    public void deleteMaintenance(Long id) {
        this.maintenanceRepository.deleteById(id);
    }

    @Override
    public ResponseMaintenanceDTO addMaintenance(UpdateMaintenanceDTO updateMaintenanceDTO) {
        if (updateMaintenanceDTO == null) {
            throw new IllegalArgumentException("Invalid maintenance entity!");
        }

        int currentCapacity = this.maintenanceRepository
                .getCountOfMaintenanceForGarageAndDate(updateMaintenanceDTO.getGarageId(), updateMaintenanceDTO.getScheduledDate());
        Garage garage = this.garageRepository
                .findById(updateMaintenanceDTO.getGarageId())
                .orElseThrow();

        if (currentCapacity >= garage.getCapacity()) {
            throw new IllegalArgumentException("Not enough space in garage!");
        }

        Maintenance maintenance = new Maintenance();
        maintenance.setServiceType(updateMaintenanceDTO.getServiceType());
        maintenance.setDate(updateMaintenanceDTO.getScheduledDate());
        maintenance.setGarage(garage);

        Car car = this.carRepository.findById(updateMaintenanceDTO.getCarId()).orElseThrow();
        maintenance.setCar(car);

        maintenance = this.maintenanceRepository.save(maintenance);

        return this.modelMapper.map(maintenance, ResponseMaintenanceDTO.class);
    }
}
