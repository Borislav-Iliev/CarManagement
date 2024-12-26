package com.example.carmanagementbackend.service;

import com.example.carmanagementbackend.entity.dto.garage.AddGarageDTO;
import com.example.carmanagementbackend.entity.dto.garage.GarageDailyAvailabilityReportDTO;
import com.example.carmanagementbackend.entity.dto.garage.ResponseGarageDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GarageService {

    ResponseGarageDTO getGarageById(Long id);

    List<ResponseGarageDTO> getAllGarages(Optional<String> city);

    ResponseGarageDTO updateGarage(Long id, AddGarageDTO addGarageDTO);

    boolean deleteGarage(Long id);

    ResponseGarageDTO addGarage(AddGarageDTO addGarageDTO);

    List<GarageDailyAvailabilityReportDTO> getGarageDailyAvailabilityReport(Long garageId, LocalDate startDate, LocalDate endDate);
}
