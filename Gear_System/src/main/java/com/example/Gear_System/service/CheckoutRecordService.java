package com.example.Gear_System.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Gear_System.model.CheckoutRecord;
import com.example.Gear_System.model.Gear;
import com.example.Gear_System.model.User;
import com.example.Gear_System.repository.CheckoutRecordRepository;
import com.example.Gear_System.repository.GearRepository;
import com.example.Gear_System.repository.UserRepository;

@Service
public class CheckoutRecordService {

    private final CheckoutRecordRepository checkoutRecordRepository;
    private final GearRepository gearRepository;
    private final UserRepository userRepository;

    @Autowired
    public CheckoutRecordService(CheckoutRecordRepository checkoutRecordRepository,
                                 GearRepository gearRepository,
                                 UserRepository userRepository) {
        this.checkoutRecordRepository = checkoutRecordRepository;
        this.gearRepository = gearRepository;
        this.userRepository = userRepository;
    }

    // Create a new checkout record
    public CheckoutRecord createCheckoutRecord(Long userId, Long gearId, LocalDate dueDate, LocalDate returnDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        Gear gear = gearRepository.findById(gearId)
                .orElseThrow(() -> new IllegalArgumentException("Gear not found with ID: " + gearId));
        
        if (!gear.isAvailable()) {
            throw new IllegalStateException("Gear is currently not available for checkout.");
        }

        CheckoutRecord checkoutRecord = new CheckoutRecord(user, gear, LocalDate.now(), dueDate, returnDate);
        gear.setAvailable(false); // Gear is now checked out
        gearRepository.save(gear);
        return checkoutRecordRepository.save(checkoutRecord);
    }

    // Mark a gear item as returned
    public CheckoutRecord returnGear(Long checkoutRecordId) {
        CheckoutRecord checkoutRecord = checkoutRecordRepository.findById(checkoutRecordId)
                .orElseThrow(() -> new IllegalArgumentException("Checkout record not found with ID: " + checkoutRecordId));

        checkoutRecord.setReturned(true);
        Gear gear = checkoutRecord.getGear();
        gear.setAvailable(true); // Mark gear as available again
        gearRepository.save(gear);
        return checkoutRecordRepository.save(checkoutRecord);
    }

    // List all checkout records for a user
    public List<CheckoutRecord> findAllCheckoutsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        return checkoutRecordRepository.findByUser(user);
    }


}