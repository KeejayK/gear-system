package com.example.Gear_System.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Gear_System.model.Gear;
import com.example.Gear_System.repository.GearRepository;

@ExtendWith(MockitoExtension.class)
public class GearServiceTests {

    @Mock
    private GearRepository gearRepository;

    @InjectMocks
    private GearService gearService;

    private Gear gear;

    @BeforeEach
    void setUp() {
        gear = new Gear("Medium Harness", "Black Diamond Momentum Harness - Medium", true);
    }

    @Test
    void addGear() {
        when(gearRepository.save(any(Gear.class))).thenReturn(gear);

        Gear savedGear = gearService.addGear(gear);

        assertNotNull(savedGear);
        assertEquals("Medium Harness", savedGear.getName());
        verify(gearRepository).save(gear);
    }

    @Test
    void updateGear() {
        Long gearId = 1L;
        Gear existingGear = new Gear("Medium Harness", "Black Diamond Momentum Harness - Medium", true);
        existingGear.setId(gearId); 

        Gear updatedGear = new Gear("Large Harness", "Black Diamond Momentum Harness - Large", true);
        updatedGear.setId(gearId); 

        when(gearRepository.findById(gearId)).thenReturn(Optional.of(existingGear));
        when(gearRepository.save(any(Gear.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Gear result = gearService.updateGear(gearId, updatedGear);

        assertNotNull(result);
        assertEquals(gearId, result.getId());
        assertEquals("Large Harness", result.getName());
        assertEquals("Black Diamond Momentum Harness - Large", result.getDescription());
        assertTrue(result.isAvailable());

        verify(gearRepository).findById(gearId);
        verify(gearRepository).save(any(Gear.class)); 
    }
    

    @Test
    void findGearById() {
        Long gearId = 1L;
        when(gearRepository.findById(gearId)).thenReturn(Optional.of(gear));

        Optional<Gear> foundGear = gearService.findGearById(gearId);

        assertTrue(foundGear.isPresent());
        assertEquals("Medium Harness", foundGear.get().getName());
        verify(gearRepository).findById(gearId);
    }

    @Test
    void deleteGear() {
        Long gearId = 1L;
        doNothing().when(gearRepository).deleteById(gearId);
        when(gearRepository.existsById(gearId)).thenReturn(true);

        gearService.deleteGear(gearId);

        verify(gearRepository).deleteById(gearId);
        verify(gearRepository).existsById(gearId);
    }
}
