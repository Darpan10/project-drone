package com.example.drone.repository;


import com.example.drone.entity.DroneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneRepository extends JpaRepository<DroneEntity, Long> {
    DroneEntity findBySerialNumber(String serialNumber);
}
