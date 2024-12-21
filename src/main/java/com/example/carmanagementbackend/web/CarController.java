package com.example.carmanagementbackend.web;

import com.example.carmanagementbackend.entity.dto.car.AddCarDto;
import com.example.carmanagementbackend.entity.dto.car.ResponseCarDto;
import com.example.carmanagementbackend.entity.dto.car.UpdateCarDto;
import com.example.carmanagementbackend.service.CarService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCarDto> getCarById(@PathVariable Long id) {
        return ResponseEntity
                .ok(this.carService.getCarById(id));
    }

    @GetMapping()
    public ResponseEntity<List<ResponseCarDto>> getAllCars(
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
    public ResponseEntity<ResponseCarDto> updateCar(@PathVariable Long id, @RequestBody @Valid UpdateCarDto updateCarDto) {
        return ResponseEntity
                .ok(this.carService.updateCar(id, updateCarDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCar(@PathVariable Long id) {
        return ResponseEntity
                .ok(this.carService.deleteCar(id));
    }

    @PostMapping()
    public ResponseEntity<ResponseCarDto> addCar(@RequestBody @Valid AddCarDto addCarDto) {
        ResponseCarDto car = this.carService.addCar(addCarDto);

        return ResponseEntity
                .created(URI.create("/garages" + car.getId()))
                .body(car);
    }
}
