package com.example.carmanagementbackend.repository;

import com.example.carmanagementbackend.entity.dto.garage.GarageDailyAvailabilityReportDTO;
import com.example.carmanagementbackend.entity.model.Garage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GarageRepository extends JpaRepository<Garage, Long> {

    List<Garage> findAllByCityContainsIgnoreCase(String city);

    @Query("SELECT new com.example.carmanagementbackend.entity.dto.garage.GarageDailyAvailabilityReportDTO" +
            " (m.date, COUNT(m))" +
            " FROM Garage g" +
            " INNER JOIN Maintenance m ON m.garage.id = g.id" +
            " WHERE g.id = :garageId AND m.date >= :startDate AND m.date <= :endDate" +
            " GROUP BY m.date")
    List<GarageDailyAvailabilityReportDTO> getGarageDailyAvailabilityReport(
            @Param("garageId") Long garageId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
