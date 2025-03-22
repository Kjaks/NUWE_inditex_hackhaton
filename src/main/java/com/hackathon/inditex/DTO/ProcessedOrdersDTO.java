package com.hackathon.inditex.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class ProcessedOrdersDTO {
    private Double distance;
    private Long orderId;
    private String assignedLogisticsCenter;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    
    private String status;
    
    public ProcessedOrdersDTO() {}

    public ProcessedOrdersDTO(Double distance, Long orderId, String assignedLogisticsCenter, String status, String message) {
         this.distance = distance;
         this.orderId = orderId;
         this.assignedLogisticsCenter = assignedLogisticsCenter;
         this.status = status;
         this.message = message;
    }

    public Double getDistance() {return distance;}
    public void setDistance(Double distance) {this.distance = distance;}

    public Long getOrderId() {return orderId;}
    public void setOrderId(Long orderId) {this.orderId = orderId;}

    public String getAssignedLogisticsCenter() {return assignedLogisticsCenter;}
    public void setAssignedLogisticsCenter(String assignedLogisticsCenter) {this.assignedLogisticsCenter = assignedLogisticsCenter;}

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}

    public String getMessage() {return message;}
    public void setMessage(String message) {this.message = message;}
}