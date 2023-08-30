package com.vega.project.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SensorDTO {
    @NotEmpty
    @Size(min = 3, max = 30, message = "Name must be 3 - 30")
    private String name;

    public SensorDTO(){}

    public SensorDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
