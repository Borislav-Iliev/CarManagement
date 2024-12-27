package com.example.carmanagementbackend.service.impl;

import com.example.carmanagementbackend.entity.dto.maintenance.MonthlyRequestsReportDTO;
import com.example.carmanagementbackend.entity.dto.maintenance.ResponseMaintenanceDTO;
import com.example.carmanagementbackend.entity.dto.maintenance.UpdateMaintenanceDTO;
import com.example.carmanagementbackend.entity.excpetion.CarNotFoundException;
import com.example.carmanagementbackend.entity.excpetion.ClientException;
import com.example.carmanagementbackend.entity.excpetion.GarageNotFoundException;
import com.example.carmanagementbackend.entity.excpetion.MaintenanceNotFoundException;
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
import java.time.Month;
import java.time.YearMonth;
import java.util.*;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {
    private static final String MAINTENANCE_NOT_FOUND = "Maintenance not found!";
    private static final String MAINTENANCE_CANNOT_BE_NULL = "Maintenance cannot be null!";
    private static final String CAR_NOT_FOUND = "Car not found!";
    private static final String GARAGE_NOT_FOUND = "Garage not found!";
    private static final String NOT_ENOUGH_SPACE_IN_GARAGE = "Not enough space in garage!";

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
        return this.modelMapper.map(getMaintenanceEntity(id), ResponseMaintenanceDTO.class);
    }

    @Override
    public List<ResponseMaintenanceDTO> getAllMaintenances(Optional<Long> carId, Optional<Long> garageId,
                                                           Optional<LocalDate> startDate, Optional<LocalDate> endDate) {
        return this.maintenanceRepository
                .getMaintenancesByFilters(carId, garageId, startDate, endDate)
                .stream()
                .map(m -> this.modelMapper.map(m, ResponseMaintenanceDTO.class))
                .toList();
    }

    @Override
    public ResponseMaintenanceDTO updateMaintenance(Long id, UpdateMaintenanceDTO updateMaintenanceDTO) {
        if (updateMaintenanceDTO == null) {
            throw new IllegalArgumentException(MAINTENANCE_CANNOT_BE_NULL);
        }

        Maintenance maintenance = mapToMaintenanceEntity(updateMaintenanceDTO, getMaintenanceEntity(id),
                getGarageEntity(updateMaintenanceDTO), getCarEntity(updateMaintenanceDTO));

        maintenance = this.maintenanceRepository.save(maintenance);

        return this.modelMapper.map(maintenance, ResponseMaintenanceDTO.class);
    }

    @Override
    public void deleteMaintenance(Long id) {
        this.maintenanceRepository.delete(this.getMaintenanceEntity(id));
    }

    @Override
    public ResponseMaintenanceDTO addMaintenance(UpdateMaintenanceDTO updateMaintenanceDTO) {
        if (updateMaintenanceDTO == null) {
            throw new IllegalArgumentException(MAINTENANCE_CANNOT_BE_NULL);
        }

        int currentCapacity = this.maintenanceRepository
                .getCountOfMaintenanceForGarageAndDate(updateMaintenanceDTO.getGarageId(), updateMaintenanceDTO.getScheduledDate());
        Garage garage = getGarageEntity(updateMaintenanceDTO);

        if (currentCapacity >= garage.getCapacity()) {
            throw new ClientException(NOT_ENOUGH_SPACE_IN_GARAGE);
        }

        Maintenance maintenance = mapToMaintenanceEntity(updateMaintenanceDTO, new Maintenance(),
                garage, getCarEntity(updateMaintenanceDTO));

        maintenance = this.maintenanceRepository.save(maintenance);

        return this.modelMapper.map(maintenance, ResponseMaintenanceDTO.class);
    }

    @Override
    public List<MonthlyRequestsReportDTO> getMonthlyRequestsReport(Long garageId, YearMonth startMonth, YearMonth endMonth) {
        List<Maintenance> maintenances = this.maintenanceRepository
                .getAllMaintenancesForGarageForPeriod(garageId, startMonth.atDay(1), endMonth.atEndOfMonth());

        Map<Month, List<Maintenance>> maintenancesPerMonth = new HashMap<>();
        maintenances.forEach(m -> maintenancesPerMonth.computeIfAbsent(m.getDate().getMonth(), key -> new ArrayList<>()).add(m));

        List<MonthlyRequestsReportDTO> result = new ArrayList<>();
        while (!startMonth.isAfter(endMonth)) {
            Month currentMonth = startMonth.getMonth();

            List<Maintenance> currentMonthMaintenances = maintenancesPerMonth.get(currentMonth);
            MonthlyRequestsReportDTO monthlyRequestsReportDTO =
                    createMonthlyRequestsReportDTO(startMonth, currentMonthMaintenances);
            result.add(monthlyRequestsReportDTO);

            startMonth = startMonth.plusMonths(1);
        }

        return result;
    }

    private MonthlyRequestsReportDTO createMonthlyRequestsReportDTO(YearMonth startMonth, List<Maintenance> currentMonthMaintenances) {
        MonthlyRequestsReportDTO monthlyRequestsReportDTO = new MonthlyRequestsReportDTO();

        if (currentMonthMaintenances != null) {
            LocalDate date = currentMonthMaintenances.get(0).getDate();
            monthlyRequestsReportDTO.setYearMonth(YearMonth.of(date.getYear(), date.getMonth()));
            monthlyRequestsReportDTO.setRequests(currentMonthMaintenances.size());
        } else {
            monthlyRequestsReportDTO.setYearMonth(startMonth);
            monthlyRequestsReportDTO.setRequests(0);
        }

        return monthlyRequestsReportDTO;
    }

    public Maintenance getMaintenanceEntity(Long id) {
        return this.maintenanceRepository
                .findById(id)
                .orElseThrow(() -> new MaintenanceNotFoundException(MAINTENANCE_NOT_FOUND));
    }

    private Garage getGarageEntity(UpdateMaintenanceDTO updateMaintenanceDTO) {
        return this.garageRepository
                .findById(updateMaintenanceDTO.getGarageId())
                .orElseThrow(() -> new GarageNotFoundException(GARAGE_NOT_FOUND));
    }

    private Car getCarEntity(UpdateMaintenanceDTO updateMaintenanceDTO) {
        return this.carRepository
                .findById(updateMaintenanceDTO.getCarId())
                .orElseThrow(() -> new CarNotFoundException(CAR_NOT_FOUND));
    }

    private Maintenance mapToMaintenanceEntity(UpdateMaintenanceDTO updateMaintenanceDTO, Maintenance maintenance, Garage garage, Car car) {
        maintenance.setServiceType(updateMaintenanceDTO.getServiceType());
        maintenance.setDate(updateMaintenanceDTO.getScheduledDate());
        maintenance.setGarage(garage);
        maintenance.setCar(car);

        return maintenance;
    }
}
