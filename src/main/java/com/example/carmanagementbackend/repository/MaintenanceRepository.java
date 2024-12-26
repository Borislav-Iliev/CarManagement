package com.example.carmanagementbackend.repository;

import com.example.carmanagementbackend.entity.model.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    @Query("SELECT m FROM Maintenance m WHERE m.car.id = :carId")
    List<Maintenance> getAllByCarId(@Param("carId") Long carId);

    @Query("SELECT m FROM Maintenance m WHERE m.garage.id = :garageId")
    List<Maintenance> getAllByGarageId(@Param("garageId") Long garageId);

    List<Maintenance> getAllByDateGreaterThanEqual(LocalDate date);

    List<Maintenance> getAllByDateLessThanEqual(LocalDate date);

    @Query("SELECT COUNT(m) FROM Maintenance m WHERE m.garage.id = :garageId AND m.date = :date")
    int getCountOfMaintenanceForGarageAndDate(@Param("garageId") Long garageId, @Param("date") LocalDate date);
}
