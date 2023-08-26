package com.example.drone.service;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.drone.dto.MedicationDto;
import com.example.drone.entity.DroneEntity;
import com.example.drone.entity.MedicationEntity;
import com.example.drone.enums.DroneState;
import com.example.drone.repository.DroneRepository;
import com.example.drone.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

@Service
public class MedicationService {
    @Autowired
    DroneService droneService;

    @Autowired
    MedicationRepository medicationRepository;

    @Autowired
    DroneRepository droneRepository;

    @Autowired
    Cloudinary cloudinary;

    @Autowired
    NamedParameterJdbcTemplate JdbcTemplate;


    public WeakHashMap<String, String> loadMedication(String serialNumber,
                                                      MedicationDto medicationDto, MultipartFile file) throws IOException {
        WeakHashMap<String, String> weakHashMap = new WeakHashMap<>();
        DroneEntity drone = droneRepository.findBySerialNumber(serialNumber);


        if (drone == null) {
            weakHashMap.put("Status", "drone couldn't be found please register the drone first");
            return weakHashMap;
        } else if (!(drone.getState().equalsIgnoreCase("IDLE")) && drone.getBatteryCapacity() >= 25) {
            weakHashMap.put("Status", "Couldn't load the drone please check drone availability ");
            return weakHashMap;
        }

        drone.setState(DroneState.LOADING.toString());
        droneRepository.save(drone);
        droneService.setDroneModel(drone, serialNumber, medicationDto.getWeight());
        MedicationEntity medicationEntity = new MedicationEntity();
        medicationEntity.setCode(medicationDto.getCode());
        medicationEntity.setWeight(medicationDto.getWeight());
        medicationEntity.setName(medicationDto.getName());
        medicationEntity.setImage(cloudinaryImageUpload(file));
        medicationRepository.save(medicationEntity);
        drone.setState(DroneState.LOADED.toString());
        droneRepository.save(drone);


        weakHashMap.put("Status", "Medicine Loaded in the drone");
        return weakHashMap;
    }


    public String cloudinaryImageUpload(MultipartFile file) throws IOException {

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return uploadResult.get("url").toString();
    }


    public List<MedicationEntity> checkLoadedMedication(String serialNumber) {
        String sqlQuery = " SELECT * FROM medication_info minfo " +
                "Inner join drone_info dinfo on dinfo.id =  minfo.id " +
                "WHERE serial_number=:sn";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("sn", serialNumber);
        return JdbcTemplate.query(sqlQuery, paramMap, BeanPropertyRowMapper.newInstance(MedicationEntity.class));
    }

    public DroneEntity checkDroneBatteryLevel(String serialNumber) {
        String sqlQuery = " SELECT * FROM drone_info where serial_number=:sn ";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("sn", serialNumber);
        return JdbcTemplate.queryForObject(sqlQuery, paramMap, BeanPropertyRowMapper.newInstance(DroneEntity.class));
    }


    public List<DroneEntity> checkDroneAvailability() {

        String sqlQuery = " SELECT * FROM drone_info where state=:state and battery_capacity>15 ";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("state", "IDLE");
        paramMap.put("battery", 15);
        return JdbcTemplate.query(sqlQuery, paramMap, BeanPropertyRowMapper.newInstance(DroneEntity.class));
    }
}
