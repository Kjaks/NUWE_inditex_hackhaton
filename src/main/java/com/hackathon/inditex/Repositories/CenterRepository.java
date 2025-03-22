package com.hackathon.inditex.Repositories;

import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.Entities.Coordinates;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CenterRepository extends JpaRepository<Center, Long> {
    Optional<Center> findById(Long id);

    List<Center> findAll();
    
    List<Center> findByCoordinates(Coordinates coordinates);
}