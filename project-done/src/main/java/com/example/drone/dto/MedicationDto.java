package com.example.drone.dto;

import com.example.drone.entity.MedicationEntity;

public class MedicationDto extends MedicationEntity {

    public MedicationDto(String name,
             int weight,
             String code,
             String image)
    {
        this.name=name;
        this.weight=weight;
        this.image=image;
        this.code=code;
    }
}
