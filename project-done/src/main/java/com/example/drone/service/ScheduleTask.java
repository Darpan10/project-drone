package com.example.drone.service;


import com.example.drone.repository.DroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
public class ScheduleTask {

    @Autowired
    DroneRepository droneRepository;

    @Autowired
    NamedParameterJdbcTemplate JdbcTemplate;



    // This method will be executed at a specific time, in this example every day at 2:00 PM


   // @Scheduled(fixedRate = 1000)
     @Scheduled(cron = "0 0 14 * * ?")
       public void checkDroneBattery() {
        String sqlQuery = "insert into drone_battery_log (serial_number, battery_capacity,time) " +
                "(SELECT serial_number,battery_Capacity,:Current_time from drone_info)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("Current_time", new Timestamp(System.currentTimeMillis()));

         JdbcTemplate.update(sqlQuery, paramMap);




    }
}
