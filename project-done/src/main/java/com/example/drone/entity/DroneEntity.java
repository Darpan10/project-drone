package com.example.drone.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;


@Getter
@Setter
@ToString
@Entity
@Table(name="drone_info")
public class DroneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serialNumber;
    private  String model;
    private Integer weightLimit;
    private Integer batteryCapacity;
    private String state;

}
