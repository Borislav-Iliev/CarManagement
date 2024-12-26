package com.example.carmanagementbackend.web;

import com.example.carmanagementbackend.entity.dto.maintenance.ResponseMaintenanceDTO;
import com.example.carmanagementbackend.entity.dto.maintenance.UpdateMaintenanceDTO;
import com.example.carmanagementbackend.service.MaintenanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceController {
    @Value("${server.address}")
    private static String serverAddress;

    @Value("${server.port}")
    private static String port;

    private static final String SERVER_URL = "http://" + serverAddress + ":/" + port;

    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMaintenanceDTO> getMaintenanceById(@PathVariable Long id) {
        return ResponseEntity
                .ok(this.maintenanceService.getMaintenanceById(id));
    }

    @GetMapping()
    public ResponseEntity<List<ResponseMaintenanceDTO>> getAllMaintenances(
            @RequestParam(name = "carId", required = false) Long carId,
            @RequestParam(name = "garageId", required = false) Long garageId,
            @RequestParam(name = "startDate", required = false) LocalDate startDate,
            @RequestParam(name = "endDate", required = false) LocalDate endDate
    ) {
        return ResponseEntity
                .ok(this.maintenanceService.getAllMaintenances(Optional.ofNullable(carId), Optional.ofNullable(garageId),
                        Optional.ofNullable(startDate), Optional.ofNullable(endDate)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMaintenanceDTO> updateMaintenance(
            @PathVariable Long id,
            @RequestBody @Valid UpdateMaintenanceDTO updateMaintenanceDTO
    ) {
        return ResponseEntity
                .ok(this.maintenanceService.updateMaintenance(id, updateMaintenanceDTO));
    }

    @DeleteMapping("/{id}")
    public void deleteMaintenance(@PathVariable Long id) {
        this.maintenanceService.deleteMaintenance(id);
    }

    @PostMapping()
    public ResponseEntity<ResponseMaintenanceDTO> addMaintenance(@RequestBody @Valid UpdateMaintenanceDTO updateMaintenanceDTO) {
        ResponseMaintenanceDTO responseMaintenanceDTO = this.maintenanceService.addMaintenance(updateMaintenanceDTO);

        return ResponseEntity
                .created(URI.create(SERVER_URL + "/maintenance/" + responseMaintenanceDTO.getId()))
                .body(responseMaintenanceDTO);
    }
}
