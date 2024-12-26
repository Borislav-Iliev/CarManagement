package com.example.carmanagementbackend.web;

import com.example.carmanagementbackend.entity.dto.car.AddCarDTO;
import com.example.carmanagementbackend.entity.dto.car.ResponseCarDTO;
import com.example.carmanagementbackend.entity.dto.car.UpdateCarDTO;
import com.example.carmanagementbackend.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarController {
    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String port;

    private final String serverUrl = "http://" + serverAddress + ":/" + port;

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCarDTO> getCarById(@PathVariable Long id) {
        return ResponseEntity
                .ok(this.carService.getCarById(id));
    }

    @GetMapping()
    public ResponseEntity<List<ResponseCarDTO>> getAllCars(
            @RequestParam(required = false, name = "carMake") String make,
            @RequestParam(required = false, name = "garageId") Integer garageId,
            @RequestParam(required = false, name = "fromYear") Integer fromYear,
            @RequestParam(required = false, name = "toYear") Integer toYear
    ) {
        return ResponseEntity
                .ok(this.carService.getAllGarages(Optional.ofNullable(make), Optional.ofNullable(garageId),
                        Optional.ofNullable(fromYear), Optional.ofNullable(toYear)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseCarDTO> updateCar(@PathVariable Long id, @RequestBody @Valid UpdateCarDTO updateCarDTO) {
        return ResponseEntity
                .ok(this.carService.updateCar(id, updateCarDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCar(@PathVariable Long id) {
        return ResponseEntity
                .ok(this.carService.deleteCar(id));
    }

    @PostMapping()
    public ResponseEntity<ResponseCarDTO> addCar(@RequestBody @Valid AddCarDTO addCarDTO) {
        ResponseCarDTO car = this.carService.addCar(addCarDTO);

        return ResponseEntity
                .created(URI.create(serverUrl + "/cars" + car.getId()))
                .body(car);
    }
}
