package com.example.Gear_System.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Gear_System.model.Gear;

@Repository
public interface GearRepository extends JpaRepository<Gear, Long> {
    List<Gear> findByIsAvailable(boolean isAvailable);
    List<Gear> findByNameContainingIgnoreCase(String name);
    Optional<Gear> findById(Long id);

}
