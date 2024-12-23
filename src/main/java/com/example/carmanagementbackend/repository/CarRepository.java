package com.example.carmanagementbackend.repository;

import com.example.carmanagementbackend.entity.model.Car;
import com.example.carmanagementbackend.entity.model.Garage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findAllByMakeContainsIgnoreCase(String make);

    List<Car> findAllByGaragesContains(List<Garage> garages);

    List<Car> findAllByYearGreaterThanEqual(int year);

    List<Car> findAllByYearLessThanEqual(int year);
}
