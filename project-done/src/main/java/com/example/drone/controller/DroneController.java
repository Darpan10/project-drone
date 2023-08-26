package com.example.drone.controller;


import com.example.drone.dto.MedicationDto;
import com.example.drone.entity.DroneEntity;
import com.example.drone.entity.MedicationEntity;
import com.example.drone.service.DroneService;
import com.example.drone.service.MedicationService;
import com.example.drone.service.ScheduleTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

@RestController
@RequestMapping("/api/drone")
public class DroneController {

    @Autowired
    ScheduleTask checkDroneBattery;

    @Autowired
    DroneService droneService;

    @Autowired
    MedicationService medicationService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerDrone() {

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Collections.singletonMap(" Serial Number", droneService.registerDrones()));
    }

    @PostMapping(value = "/{serialNumber}/load",consumes ="multipart/form-data")
    public ResponseEntity <WeakHashMap<String, String>>loadMedication(@RequestBody MedicationDto medicationDto,
                                                                      @RequestParam("file") MultipartFile file,
                                                                      @PathVariable String serialNumber) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                medicationService.loadMedication(serialNumber, medicationDto,file));
    }


    @GetMapping("/{serialNumber}/medication")
    public ResponseEntity<List<MedicationEntity>> checkMedication(@PathVariable String serialNumber) {
        checkDroneBattery.checkDroneBattery();
        return ResponseEntity.status(HttpStatus.CREATED).body(
                medicationService.checkLoadedMedication(serialNumber));
    }


    @GetMapping("/{serialNumber}/battery")
    public ResponseEntity<Object> checkDroneBattery(@PathVariable String serialNumber) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Collections.singletonMap(" battery", medicationService.checkDroneBatteryLevel(serialNumber).getBatteryCapacity() +" %"));
    }


    @GetMapping("/available")
    public ResponseEntity<List<DroneEntity>> checkDroneAvailability() {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                medicationService.checkDroneAvailability());
    }




}
