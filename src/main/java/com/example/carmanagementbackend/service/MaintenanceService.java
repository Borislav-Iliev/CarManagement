package com.example.carmanagementbackend.service;

import com.example.carmanagementbackend.entity.dto.maintenance.MonthlyRequestsReportDTO;
import com.example.carmanagementbackend.entity.dto.maintenance.ResponseMaintenanceDTO;
import com.example.carmanagementbackend.entity.dto.maintenance.UpdateMaintenanceDTO;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface MaintenanceService {

    ResponseMaintenanceDTO getMaintenanceById(Long id);

    List<ResponseMaintenanceDTO> getAllMaintenances(Optional<Long> carId, Optional<Long> garageId, Optional<LocalDate> startDate, Optional<LocalDate> endDate);

    ResponseMaintenanceDTO updateMaintenance(Long id, UpdateMaintenanceDTO updateMaintenanceDTO);

    void deleteMaintenance(Long id);

    ResponseMaintenanceDTO addMaintenance(UpdateMaintenanceDTO updateMaintenanceDTO);

    List<MonthlyRequestsReportDTO> getMonthlyRequestsReport(Long garageId, YearMonth startMonth, YearMonth endMonth);
}
