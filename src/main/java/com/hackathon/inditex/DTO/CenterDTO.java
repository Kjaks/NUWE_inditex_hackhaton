package com.hackathon.inditex.DTO;

import lombok.Data;
import com.hackathon.inditex.Entities.Coordinates;

@Data
public class CenterDTO {
    private String name;
    private String capacity;
    private String status;
    private Integer currentLoad;
    private Integer maxCapacity;
    
    private Coordinates coordinates;

    public CenterDTO() {}

    public CenterDTO(String name, String capacity, String status, Integer currentLoad, Integer maxCapacity, Coordinates coordinates) {
        this.name = name;
        this.capacity = capacity;
        this.status = status;
        this.currentLoad = currentLoad;
        this.maxCapacity = maxCapacity;
        this.coordinates = coordinates;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCapacity() { return capacity; }
    public void setCapacity(String capacity) { this.capacity = capacity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getCurrentLoad() { return currentLoad; }
    public void setCurrentLoad(Integer currentLoad) { this.currentLoad = currentLoad; }

    public Integer getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(Integer maxCapacity) { this.maxCapacity = maxCapacity; }

    public Coordinates getCoordinates() { return this.coordinates; }
    public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }
}