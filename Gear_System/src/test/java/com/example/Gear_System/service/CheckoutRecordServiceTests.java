package com.example.Gear_System.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.Gear_System.model.CheckoutRecord;
import com.example.Gear_System.model.Gear;
import com.example.Gear_System.model.User;
import com.example.Gear_System.repository.CheckoutRecordRepository;
import com.example.Gear_System.repository.GearRepository;
import com.example.Gear_System.repository.UserRepository;

public class CheckoutRecordServiceTests {
     @Mock
    private CheckoutRecordRepository checkoutRecordRepository;

    @Mock
    private GearRepository gearRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CheckoutRecordService checkoutRecordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCheckoutRecord_Success() {
        User mockUser = new User(); 
        Gear mockGear = new Gear(); 
        mockGear.setAvailable(true);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));
        when(gearRepository.findById(anyLong())).thenReturn(Optional.of(mockGear));
        when(checkoutRecordRepository.save(any(CheckoutRecord.class))).thenAnswer(i -> i.getArguments()[0]);

        CheckoutRecord result = checkoutRecordService.createCheckoutRecord(1L, 1L, LocalDate.now().plusDays(7), null);

        assertNotNull(result);
        assertFalse(result.isReturned());
        verify(gearRepository).save(mockGear);
        verify(checkoutRecordRepository).save(any(CheckoutRecord.class));
    }
}
