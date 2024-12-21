package com.example.carmanagementbackend.service.impl;

import com.example.carmanagementbackend.entity.dto.garage.AddGarageDto;
import com.example.carmanagementbackend.entity.dto.garage.ResponseGarageDto;
import com.example.carmanagementbackend.entity.model.Garage;
import com.example.carmanagementbackend.repository.GarageRepository;
import com.example.carmanagementbackend.service.GarageService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GarageServiceImpl implements GarageService {

    private final GarageRepository garageRepository;
    private final ModelMapper modelMapper;

    public GarageServiceImpl(GarageRepository garageRepository, ModelMapper modelMapper) {
        this.garageRepository = garageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseGarageDto getGarageById(Long id) {
        Garage garage = this.garageRepository
                .findById(id)
                .orElseThrow();

        return this.modelMapper.map(garage, ResponseGarageDto.class);
    }

    @Override
    public List<ResponseGarageDto> getAllGarages(Optional<String> city) {
        if (city.isPresent()) {
            return this.garageRepository
                    .findAllByCityContainsIgnoreCase(city.get())
                    .stream()
                    .map(garage -> this.modelMapper.map(garage, ResponseGarageDto.class))
                    .toList();
        }

        return this.garageRepository
                .findAll()
                .stream()
                .map(garage -> this.modelMapper.map(garage, ResponseGarageDto.class))
                .toList();
    }

    @Override
    public ResponseGarageDto updateGarage(Long id, AddGarageDto addGarageDto) {
        Garage garageById = this.garageRepository
                .findById(id)
                .orElseThrow();

        garageById.setName(addGarageDto.getName());
        garageById.setLocation(addGarageDto.getLocation());
        garageById.setCity(addGarageDto.getCity());
        garageById.setCapacity(addGarageDto.getCapacity());

        return this.modelMapper.map(this.garageRepository.save(garageById), ResponseGarageDto.class);
    }

    @Override
    public boolean deleteGarage(Long id) {
        this.garageRepository.deleteById(id);
        return this.garageRepository.existsById(id);
    }

    @Override
    public ResponseGarageDto addGarage(AddGarageDto addGarageDto) {
        if (addGarageDto == null) {
            throw new IllegalArgumentException("Invalid garage entity!");
        }

        Garage garage = this.modelMapper.map(addGarageDto, Garage.class);

        return this.modelMapper.map(this.garageRepository.save(garage), ResponseGarageDto.class);
    }
}
