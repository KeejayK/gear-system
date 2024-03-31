package com.example.Gear_System.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Gear_System.model.Gear;
import com.example.Gear_System.repository.GearRepository;

@Service
public class GearService {

    private final GearRepository gearRepository;

    @Autowired
    public GearService(GearRepository gearRepository) {
        this.gearRepository = gearRepository;
    }

    // Add new gear to the inventory
    public Gear addGear(Gear gear) {
        return gearRepository.save(gear);
    }

    // Update existing gear information
    public Gear updateGear(Long id, Gear updatedGear) {
        return gearRepository.findById(id)
                .map(gear -> {
                    gear.setName(updatedGear.getName());
                    gear.setDescription(updatedGear.getDescription());
                    gear.setAvailable(updatedGear.isAvailable());
                    return gearRepository.save(gear);
                })
                .orElseThrow(() -> new IllegalStateException("Gear with ID " + id + " does not exist."));
    }

    // Find gear by ID
    public Optional<Gear> findGearById(Long id) {
        return gearRepository.findById(id);
    }

    // List all gear 
    public List<Gear> listAllGear() {
        return gearRepository.findAll();
    }

    // Delete gear
    public void deleteGear(Long id) {
        boolean exists = gearRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("Gear with ID " + id + " does not exist.");
        }
        gearRepository.deleteById(id);
    }

}