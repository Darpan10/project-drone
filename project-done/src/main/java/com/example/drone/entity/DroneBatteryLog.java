package com.example.drone.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Entity
@Table(name="drone_battery_log")
public class DroneBatteryLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int batteryCapacity;
    private String serialNumber;
    private Timestamp time;



}
