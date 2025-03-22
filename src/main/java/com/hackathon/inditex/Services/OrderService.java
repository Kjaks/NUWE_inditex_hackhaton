package com.hackathon.inditex.Services;

import com.hackathon.inditex.Entities.Order;
import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.Entities.Coordinates;
import com.hackathon.inditex.Repositories.OrderRepository;
import com.hackathon.inditex.Repositories.CenterRepository;
import com.hackathon.inditex.DTO.OrderDTO;
import com.hackathon.inditex.DTO.ProcessedOrdersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CenterRepository centerRepository;
    
    public ResponseEntity<?> saveOrder(OrderDTO dto) {
        Order order = new Order();
        order.setCustomerId(dto.getCustomerId());
        order.setSize(dto.getSize());
        order.setStatus("PENDING");
        order.setCoordinates(new Coordinates(dto.getCoordinates().getLatitude(), dto.getCoordinates().getLongitude()));

        try {
            Order savedOrder = orderRepository.save(order);

            OrderDTO response = new OrderDTO();
            response.setId(savedOrder.getId());
            response.setCustomerId(savedOrder.getCustomerId());
            response.setSize(savedOrder.getSize());
            response.setCoordinates(savedOrder.getCoordinates());
            response.setStatus("PENDING");
            response.setMessage("Order created successfully in PENDING status.");

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected Error.");
        }
    }

    public List<Order> listAllOrders() {
        return orderRepository.findAll();
    }

    public Map<String, List<ProcessedOrdersDTO>> assignOrders() {
        List<Order> pendingOrders = orderRepository.findAllByStatus("PENDING");
        pendingOrders.sort((o1, o2) -> Long.compare(o1.getId(), o2.getId()));

        List<Center> availableCenters = centerRepository.findAll();
        
        List<ProcessedOrdersDTO> processedOrders = new ArrayList<>();
        
        for (Order order : pendingOrders) {
            Center closestCenter = findClosestCenter(order, availableCenters);
            
            ProcessedOrdersDTO processedOrder = new ProcessedOrdersDTO();
            processedOrder.setOrderId(order.getId());
    
            if (closestCenter != null && canHandleOrder(closestCenter, order)) {
                double distance = calculateDistance(order.getCoordinates(), closestCenter.getCoordinates());
                
                assignOrderToCenter(order, closestCenter);
                
                processedOrder.setDistance(distance);
                processedOrder.setAssignedLogisticsCenter(closestCenter.getName());
                processedOrder.setStatus("ASSIGNED");
            } else {
                processedOrder.setStatus("PENDING");
                processedOrder.setMessage(getErrorMessage(closestCenter, order));
            }
            
            processedOrders.add(processedOrder);
        }
        
        Map<String, List<ProcessedOrdersDTO>> response = new HashMap<>();
        response.put("processed-orders", processedOrders);
        
        return response;
    }
    
    private Center findClosestCenter(Order order, List<Center> centers) {
        return centers.stream()
                .min(Comparator.comparingDouble(c -> calculateDistance(order.getCoordinates(), c.getCoordinates())))
                .orElse(null);
    }
    
    private boolean canHandleOrder(Center center, Order order) {
        return center.getCapacity().contains(order.getSize()) && !(center.getCurrentLoad() >= center.getMaxCapacity());
    }
    
    private String getErrorMessage(Center closestCenter, Order order) {
        if (closestCenter != null && closestCenter.getCapacity().contains(order.getSize())) {
            return "All centers are at maximum capacity.";
        }
        return "No available centers support the order type.";
    }
    
    private void assignOrderToCenter(Order order, Center center) {
        order.setStatus("ASSIGNED");
        order.setAssignedCenter(center.getName());
        center.setCurrentLoad(center.getCurrentLoad() + 1);
        if (center.getCurrentLoad() >= center.getMaxCapacity()) {
            center.setStatus("OCCUPIED");
        }
        orderRepository.save(order);
        centerRepository.save(center);
    }
    
    public static double calculateDistance(Coordinates coordinates1, Coordinates coordinates2) {
        final double EARTH_RADIUS = 6371.0;
        
        double lat1 = Math.toRadians(coordinates1.getLatitude());
        double lon1 = Math.toRadians(coordinates1.getLongitude());
        double lat2 = Math.toRadians(coordinates2.getLatitude());
        double lon2 = Math.toRadians(coordinates2.getLongitude());
        
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;
        
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(lat1) * Math.cos(lat2) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return EARTH_RADIUS * c;
    }
}