package com.example.carmanagementbackend.web;

import com.example.carmanagementbackend.entity.dto.garage.AddGarageDTO;
import com.example.carmanagementbackend.entity.dto.garage.GarageDailyAvailabilityReportDTO;
import com.example.carmanagementbackend.entity.dto.garage.ResponseGarageDTO;
import com.example.carmanagementbackend.service.GarageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/garages")
public class GarageController {
    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String port;

    private final String serverUrl = "http://" + serverAddress + ":/" + port;

    private final GarageService garageService;

    public GarageController(GarageService garageService) {
        this.garageService = garageService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseGarageDTO> getGarage(@PathVariable Long id) {
        return ResponseEntity
                .ok(this.garageService.getGarageById(id));
    }

    @GetMapping()
    public ResponseEntity<List<ResponseGarageDTO>> getAllGarages(@RequestParam(required = false, name = "city") String city) {
        return ResponseEntity
                .ok(this.garageService.getAllGarages(Optional.ofNullable(city)));
    }

    @GetMapping("/dailyAvailabilityReport")
    public ResponseEntity<List<GarageDailyAvailabilityReportDTO>> getGarageDailyAvailabilityReport(
            @RequestParam(name = "garageId") Long garageId,
            @RequestParam(name = "startDate") String startDate,
            @RequestParam(name = "endDate") String endDate
    ) {
        return ResponseEntity
                .ok(this.garageService.getGarageDailyAvailabilityReport(garageId,
                        LocalDate.parse(startDate), LocalDate.parse(endDate)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseGarageDTO> updateGarage(@PathVariable Long id, @RequestBody @Valid AddGarageDTO addGarageDto) {
        return ResponseEntity
                .ok(this.garageService.updateGarage(id, addGarageDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteGarage(@PathVariable Long id) {
        return ResponseEntity
                .ok(this.garageService.deleteGarage(id));
    }

    @PostMapping()
    public ResponseEntity<ResponseGarageDTO> addGarage(@RequestBody @Valid AddGarageDTO addGarageDto) {
        ResponseGarageDTO garage = this.garageService.addGarage(addGarageDto);

        return ResponseEntity
                .created(URI.create(serverUrl + "/garages/" + garage.getId()))
                .body(garage);
    }
}
