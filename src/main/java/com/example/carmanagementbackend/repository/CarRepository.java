package com.example.carmanagementbackend.repository;

import com.example.carmanagementbackend.entity.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT c FROM Car c" +
            " JOIN c.garages g" +
            " WHERE (:make IS NULL OR c.make = :make)" +
            " AND (:garageId IS NULL OR g.id = :garageId)" +
            " AND (:startYear IS NULL OR c.year >= :startYear)" +
            " AND (:endYear IS NULL OR c.year <= :endYear)")
    List<Car> getCarsByFilters(
            @Param("make") Optional<String> make,
            @Param("garageId") Optional<Integer> garageId,
            @Param("startYear") Optional<Integer> startYear,
            @Param("endYear") Optional<Integer> endYear
    );
}
