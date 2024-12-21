package com.example.carmanagementbackend.service.impl;

import com.example.carmanagementbackend.entity.dto.garage.AddGarageDto;
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
    public Garage getGarageById(Long id) {
        return this.garageRepository
                .findById(id)
                .orElse(null);
    }

    @Override
    public List<Garage> getAllGarages(Optional<String> city) {
        if (city.isPresent()) {
            return this.garageRepository.findAllByCityContainsIgnoreCase(city.get());
        }

        return this.garageRepository.findAll();
    }

    @Override
    public Garage updateGarage(Long id, AddGarageDto addGarageDto) {
        Garage garageById = this.getGarageById(id);

        garageById.setName(addGarageDto.getName());
        garageById.setLocation(addGarageDto.getLocation());
        garageById.setCity(addGarageDto.getCity());
        garageById.setCapacity(addGarageDto.getCapacity());

        return this.garageRepository.save(garageById);
    }

    @Override
    public boolean deleteGarage(Long id) {
        this.garageRepository.deleteById(id);
        return this.garageRepository.existsById(id);
    }

    @Override
    public Garage addGarage(AddGarageDto addGarageDto) {
        if (addGarageDto == null) {
            throw new IllegalArgumentException("Invalid garage entity!");
        }

        Garage garage = this.modelMapper.map(addGarageDto, Garage.class);

        return this.garageRepository.save(garage);
    }
}
