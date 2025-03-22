package com.hackathon.inditex.Services;

import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.Entities.Coordinates;
import com.hackathon.inditex.Repositories.CenterRepository;
import com.hackathon.inditex.DTO.CenterDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class CenterService {

    @Autowired
    private CenterRepository centerRepository;
    private CenterDTO centerDTO;

    public ResponseEntity<?> saveCenter(CenterDTO dto) {
        List<Center> existingCenters = centerRepository.findByCoordinates(dto.getCoordinates());
        if (!existingCenters.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message("There is already a logistics center in that position."));
        }

        if (dto.getCurrentLoad() > dto.getMaxCapacity()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message("Current load cannot exceed max capacity."));
        }

        Center center = new Center();
        center.setName(dto.getName());
        center.setCapacity(dto.getCapacity());
        center.setStatus(dto.getStatus());
        center.setCurrentLoad(dto.getCurrentLoad());
        center.setMaxCapacity(dto.getMaxCapacity());
        
        center.setCoordinates(new Coordinates(
            dto.getCoordinates().getLatitude(),
            dto.getCoordinates().getLongitude()
        ));

        try {
            Center savedCenter = centerRepository.save(center);

            return ResponseEntity.status(HttpStatus.CREATED).body(new Message("Logistics center created successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message("Unexpected Error."));
        }
    }

    public List<Center> listAllCenters() {
        return centerRepository.findAll();
    }

    public ResponseEntity<?> updateCenter(Long id, CenterDTO dto) {
        Optional<Center> Center = centerRepository.findById(id);
        
        if (Center.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message("Center not found."));
        }

        Center center = Center.get();

        if (dto.getMaxCapacity() != null && center.getCurrentLoad() > dto.getMaxCapacity()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message("Current load cannot exceed max capacity."));
        }

        if (dto.getCurrentLoad() != null && dto.getCurrentLoad() > center.getMaxCapacity()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message("Current load cannot exceed max capacity."));
        }

        if (dto.getName() != null && !dto.getName().isEmpty()) {
            center.setName(dto.getName());
        }
        
        if (dto.getCapacity() != null) {
            center.setCapacity(dto.getCapacity());
        }
        
        if (dto.getStatus() != null) {
            center.setStatus(dto.getStatus());
        }
        
        if (dto.getCurrentLoad() != null) {
            center.setCurrentLoad(dto.getCurrentLoad());
        }
        
        if (dto.getMaxCapacity() != null) {
            center.setMaxCapacity(dto.getMaxCapacity());
        }
        
        if (dto.getCoordinates() != null) {
            center.setCoordinates(new Coordinates(
                dto.getCoordinates().getLatitude(),
                dto.getCoordinates().getLongitude()
            ));
        }

        try {
            Center updatedCenter = centerRepository.save(center);
            
            return ResponseEntity.ok(new Message("Logistics center updated successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message("Unexpected Error: " + e.getMessage()));
        }
    }

    public ResponseEntity<?> deleteCenter(Long id) {
        try {
            centerRepository.deleteById(id);
            return ResponseEntity.ok(new Message("Logistics center deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message("Error deleting center: " + e.getMessage()));
        }
    }

    private class Message {
        private String message;

        public Message(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

}