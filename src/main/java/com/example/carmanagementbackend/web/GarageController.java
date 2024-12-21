package com.example.carmanagementbackend.web;

import com.example.carmanagementbackend.entity.dto.garage.AddGarageDto;
import com.example.carmanagementbackend.entity.dto.garage.ResponseGarageDto;
import com.example.carmanagementbackend.service.GarageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/garages")
public class GarageController {

    private final GarageService garageService;

    public GarageController(GarageService garageService) {
        this.garageService = garageService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseGarageDto> getGarage(@PathVariable Long id) {
        return ResponseEntity
                .ok(this.garageService.getGarageById(id));
    }

    @GetMapping()
    public ResponseEntity<List<ResponseGarageDto>> getAllGarages(@RequestParam(required = false, name = "city") String city) {
        return ResponseEntity
                .ok(this.garageService.getAllGarages(Optional.ofNullable(city)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseGarageDto> updateGarage(@PathVariable Long id, @RequestBody @Valid AddGarageDto addGarageDto) {
        return ResponseEntity
                .ok(this.garageService.updateGarage(id, addGarageDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteGarage(@PathVariable Long id) {
        return ResponseEntity
                .ok(this.garageService.deleteGarage(id));
    }

    @PostMapping()
    public ResponseEntity<ResponseGarageDto> addGarage(@RequestBody @Valid AddGarageDto addGarageDto) {
        ResponseGarageDto garage = this.garageService.addGarage(addGarageDto);

        return ResponseEntity
                .created(URI.create("/garages" + garage.getId()))
                .body(garage);
    }
}
