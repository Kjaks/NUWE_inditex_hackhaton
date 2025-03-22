package com.hackathon.inditex.Controllers;

import com.hackathon.inditex.Services.CenterService;
import com.hackathon.inditex.DTO.CenterDTO;
import com.hackathon.inditex.Entities.Center;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CenterController {

    @Autowired
    private CenterService centerService;

    @GetMapping("/centers")
    public ResponseEntity<List<Center>> listAllCenters() {
        List<Center> centers = centerService.listAllCenters();
        return ResponseEntity.ok(centers);
    }

    @PostMapping("/centers")
    public ResponseEntity<?> createCenter(@RequestBody CenterDTO dto) {
        return centerService.saveCenter(dto);
    }

    @PatchMapping("/centers/{id}")
    public ResponseEntity<?> updateCenter(@PathVariable Long id, @RequestBody CenterDTO dto) {
        return centerService.updateCenter(id, dto);
    }

    @DeleteMapping("/centers/{id}")
    public ResponseEntity<?> deleteCenter(@PathVariable Long id) {
        return centerService.deleteCenter(id);
    }
}