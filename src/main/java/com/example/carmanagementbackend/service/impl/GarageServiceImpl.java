package com.example.carmanagementbackend.service.impl;

import com.example.carmanagementbackend.entity.dto.garage.AddGarageDTO;
import com.example.carmanagementbackend.entity.dto.garage.GarageDailyAvailabilityReportDTO;
import com.example.carmanagementbackend.entity.dto.garage.ResponseGarageDTO;
import com.example.carmanagementbackend.entity.excpetion.ClientException;
import com.example.carmanagementbackend.entity.excpetion.GarageNotFoundException;
import com.example.carmanagementbackend.entity.model.Garage;
import com.example.carmanagementbackend.repository.GarageRepository;
import com.example.carmanagementbackend.service.GarageService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GarageServiceImpl implements GarageService {
    private static final String GARAGE_NOT_FOUND = "Garage not found!";
    private static final String GARAGE_CANNOT_BE_NULL = "Garage cannot be null!";

    private final GarageRepository garageRepository;
    private final ModelMapper modelMapper;

    public GarageServiceImpl(GarageRepository garageRepository, ModelMapper modelMapper) {
        this.garageRepository = garageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseGarageDTO getGarageById(Long id) {
        return this.modelMapper.map(getGarageEntity(id), ResponseGarageDTO.class);
    }

    @Override
    public List<ResponseGarageDTO> getAllGarages(Optional<String> city) {
        if (city.isPresent()) {
            return this.garageRepository.findAllByCityContainsIgnoreCase(city.get())
                    .stream()
                    .map(garage -> this.modelMapper.map(garage, ResponseGarageDTO.class))
                    .toList();
        }

        return this.garageRepository.findAll()
                .stream()
                .map(garage -> this.modelMapper.map(garage, ResponseGarageDTO.class))
                .toList();
    }

    @Override
    public ResponseGarageDTO updateGarage(Long id, AddGarageDTO addGarageDTO) {
        if (addGarageDTO == null) {
            throw new ClientException(GARAGE_CANNOT_BE_NULL);
        }

        Garage garageById = getGarageEntity(id);
        mapToGarageEntity(addGarageDTO, garageById);

        return this.modelMapper.map(this.garageRepository.save(garageById), ResponseGarageDTO.class);
    }

    @Override
    public boolean deleteGarage(Long id) {
        Garage garageEntity = this.getGarageEntity(id);
        this.garageRepository.delete(garageEntity);
        return true;
    }

    @Override
    public ResponseGarageDTO addGarage(AddGarageDTO addGarageDTO) {
        if (addGarageDTO == null) {
            throw new ClientException(GARAGE_CANNOT_BE_NULL);
        }

        Garage garage = this.modelMapper.map(addGarageDTO, Garage.class);

        return this.modelMapper.map(this.garageRepository.save(garage), ResponseGarageDTO.class);
    }

    @Override
    public List<GarageDailyAvailabilityReportDTO> getGarageDailyAvailabilityReport(Long garageId, LocalDate startDate, LocalDate endDate) {
        Garage garage = getGarageEntity(garageId);

        List<GarageDailyAvailabilityReportDTO> reportDTOs = this.garageRepository
                .getGarageDailyAvailabilityReport(garageId, startDate, endDate);

        long allRequestsCount = reportDTOs
                .stream()
                .mapToLong(GarageDailyAvailabilityReportDTO::getRequests)
                .sum();

        reportDTOs.forEach(e -> e.setAvailableCapacity(garage.getCapacity() - allRequestsCount));

        return reportDTOs;
    }

    public Garage getGarageEntity(Long id) {
        return this.garageRepository
                .findById(id)
                .orElseThrow(() -> new GarageNotFoundException(GARAGE_NOT_FOUND));
    }

    private void mapToGarageEntity(AddGarageDTO addGarageDTO, Garage garage) {
        garage.setName(addGarageDTO.getName());
        garage.setLocation(addGarageDTO.getLocation());
        garage.setCity(addGarageDTO.getCity());
        garage.setCapacity(addGarageDTO.getCapacity());
    }
}
