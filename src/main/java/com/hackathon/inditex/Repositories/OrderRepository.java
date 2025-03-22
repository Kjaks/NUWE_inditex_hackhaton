package com.hackathon.inditex.Repositories;

import com.hackathon.inditex.Entities.Order;
import com.hackathon.inditex.Entities.Coordinates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.status = :status")
    List<Order> findAllByStatus(@Param("status") String status);    

    List<Order> findAll();    
}