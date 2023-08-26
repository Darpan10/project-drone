package com.example.drone.service;

import com.example.drone.entity.DroneEntity;
import com.example.drone.enums.DroneModel;
import com.example.drone.enums.DroneState;
import com.example.drone.repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DroneService {

    @Autowired
    DroneRepository droneRepository;


    public String registerDrones() {
        DroneEntity drone = new DroneEntity();
       List<DroneEntity> drones= droneRepository.findAll();
       if(drones.size()<=10) {

            drone.setSerialNumber(generateSerialNumber());
            drone.setModel(DroneModel.UNIDENTIFIED.toString());
            drone.setWeightLimit(500);
            drone.setState(DroneState.IDLE.toString());
            drone.setBatteryCapacity(100);

            return droneRepository.save(drone).getSerialNumber();
        }

        return "Serial Number cannot be found because register drone exceeded the limit ";

  }



    public static String generateSerialNumber() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();

    }

    public void setDroneModel(DroneEntity drone, String serialNumber, int weight) {

           if (weight <= 125) {
                drone.setModel(DroneModel.Lightweight.toString());
            } else if (weight <= 250) {
                drone.setModel(DroneModel.Middleweight.toString());
            } else if (weight <= 375) {
                drone.setModel(DroneModel.Cruiserweight.toString());
            } else if (weight <= 500) {
                drone.setModel(DroneModel.Heavyweight.toString());
            }
            droneRepository.save(drone);




    }

}
