package com.example.carmanagementbackend.repository;

import com.example.carmanagementbackend.entity.model.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    @Query("SELECT m FROM Maintenance m" +
            " JOIN m.car c" +
            " JOIN m.garage g" +
            " WHERE (:carId IS NULL OR c.id = :carId)" +
            " AND (:garageId IS NULL OR g.id = :garageId)" +
            " AND (:startDate IS NULL OR m.date >= :startDate)" +
            " AND (:endDate IS NULL OR m.date <= :endDate)")
    List<Maintenance> getMaintenancesByFilters(
            @Param("carId") Optional<Long> carId,
            @Param("garageId") Optional<Long> garageId,
            @Param("startDate") Optional<LocalDate> startDate,
            @Param("endDate") Optional<LocalDate> endDate
    );

    @Query("SELECT COUNT(m) FROM Maintenance m WHERE m.garage.id = :garageId AND m.date = :date")
    int getCountOfMaintenanceForGarageAndDate(@Param("garageId") Long garageId, @Param("date") LocalDate date);

    @Query("SELECT m" +
            " FROM Maintenance m" +
            " WHERE m.garage.id = :garageId AND m.date >= :startDate AND m.date <= :endDate")
    List<Maintenance> getAllMaintenancesForGarageForPeriod(
            @Param("garageId") Long garageId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
