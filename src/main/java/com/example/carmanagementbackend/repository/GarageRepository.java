package com.example.carmanagementbackend.repository;

import com.example.carmanagementbackend.entity.model.Garage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarageRepository extends JpaRepository<Garage, Long> {
}
