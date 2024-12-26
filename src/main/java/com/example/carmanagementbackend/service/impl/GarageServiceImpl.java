package com.example.carmanagementbackend.service.impl;

import com.example.carmanagementbackend.entity.dto.garage.AddGarageDTO;
import com.example.carmanagementbackend.entity.dto.garage.GarageDailyAvailabilityReportDTO;
import com.example.carmanagementbackend.entity.dto.garage.ResponseGarageDTO;
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
        Garage garage = this.garageRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(GARAGE_NOT_FOUND));

        return this.modelMapper.map(garage, ResponseGarageDTO.class);
    }

    @Override
    public List<ResponseGarageDTO> getAllGarages(Optional<String> city) {
        return city.map(s -> this.garageRepository.findAllByCityContainsIgnoreCase(s)
                .stream()
                .map(garage -> this.modelMapper.map(garage, ResponseGarageDTO.class))
                .toList())
                .orElseGet(() -> this.garageRepository.findAll()
                .stream()
                .map(garage -> this.modelMapper.map(garage, ResponseGarageDTO.class))
                .toList());
    }

    @Override
    public ResponseGarageDTO updateGarage(Long id, AddGarageDTO addGarageDTO) {
        Garage garageById = this.garageRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(GARAGE_NOT_FOUND));

        setGarageFields(addGarageDTO, garageById);

        return this.modelMapper.map(this.garageRepository.save(garageById), ResponseGarageDTO.class);
    }

    private void setGarageFields(AddGarageDTO addGarageDTO, Garage garageById) {
        garageById.setName(addGarageDTO.getName());
        garageById.setLocation(addGarageDTO.getLocation());
        garageById.setCity(addGarageDTO.getCity());
        garageById.setCapacity(addGarageDTO.getCapacity());
    }

    @Override
    public boolean deleteGarage(Long id) {
        this.garageRepository.deleteById(id);
        return this.garageRepository.existsById(id);
    }

    @Override
    public ResponseGarageDTO addGarage(AddGarageDTO addGarageDTO) {
        if (addGarageDTO == null) {
            throw new IllegalArgumentException(GARAGE_CANNOT_BE_NULL);
        }

        Garage garage = this.modelMapper.map(addGarageDTO, Garage.class);

        return this.modelMapper.map(this.garageRepository.save(garage), ResponseGarageDTO.class);
    }

    @Override
    public List<GarageDailyAvailabilityReportDTO> getGarageDailyAvailabilityReport(Long garageId, LocalDate startDate, LocalDate endDate) {
        Garage garage = this.garageRepository.findById(garageId).orElseThrow();

        List<GarageDailyAvailabilityReportDTO> garageDailyAvailabilityReport = this.garageRepository
                .getGarageDailyAvailabilityReport(garageId, startDate, endDate);

        garageDailyAvailabilityReport.forEach(e -> e.setAvailableCapacity(garage.getCapacity() - e.getRequests()));

        return garageDailyAvailabilityReport;
    }
}
