package com.hackathon.inditex.DTO;

import lombok.Data;
import com.hackathon.inditex.Entities.Coordinates;

@Data
public class OrderDTO {
    private Long id;
    private Long customerId;
    private String size;
    private String assignedCenter;
    private Coordinates coordinates;
    private String status;
    private String message;

    public OrderDTO() {}

    public OrderDTO(Long id, Long customerId, String size, String status, String assignedCenter, Coordinates coordinates) {
        this.id = id;
        this.customerId = customerId;
        this.size = size;
        this.status = status;
        this.coordinates = coordinates;
        this.assignedCenter = assignedCenter;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
        
    public String getassignedCenter() { return assignedCenter; }
    public void setassignedCenter(String assignedCenter) { this.assignedCenter = assignedCenter; }
    
    public Coordinates getCoordinates() { return coordinates; }
    public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public void setMessage(String message) { this.message = message; }

    }