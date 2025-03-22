package com.hackathon.inditex.Controllers;

import com.hackathon.inditex.Services.OrderService;
import com.hackathon.inditex.DTO.OrderDTO;
import com.hackathon.inditex.DTO.ProcessedOrdersDTO;
import com.hackathon.inditex.Entities.Order;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> listAllOrders() {
        List<Order> orders = orderService.listAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO dto) {
        return orderService.saveOrder(dto);
    }

    @PostMapping("/orders/order-assignations")
    public ResponseEntity<Map<String, List<ProcessedOrdersDTO>>> assignAllOrders() {
        Map<String, List<ProcessedOrdersDTO>> orders = orderService.assignOrders();
    
        return ResponseEntity.ok(orders);
    }
    
}