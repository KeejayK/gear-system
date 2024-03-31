package com.example.Gear_System.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Gear_System.model.Gear;
import com.example.Gear_System.service.GearService;


@RestController
@RequestMapping("/api/gear")
public class GearController {
    private final GearService gearService;

    @Autowired
    public GearController(GearService gearService) {
        this.gearService = gearService;
    }

    // Add new gear
    @PostMapping
    public ResponseEntity<Gear> addGear(@RequestBody Gear gear) {
        try {
            Gear newGear = gearService.addGear(gear);
            return new ResponseEntity<>(newGear, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Update gear info
    @PutMapping("/{id}")
    public ResponseEntity<Gear> updateGear(@PathVariable Long id, @RequestBody Gear gear) {
        try {
            Gear updatedGear = gearService.updateGear(id, gear);
            return new ResponseEntity<>(updatedGear, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // List all gear
    @GetMapping
    public ResponseEntity<List<Gear>> getAllGear() {
        List<Gear> gear = gearService.listAllGear();
        if (gear.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(gear, HttpStatus.OK);
    }

    // Delete gear
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGear(@PathVariable Long id) {
        try {
            gearService.deleteGear(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}