package com.example.Gear_System.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Gear_System.model.CheckoutRecord;
import com.example.Gear_System.service.CheckoutRecordService;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutRecordController {
    
    private final CheckoutRecordService checkoutRecordService;

    @Autowired
    public CheckoutRecordController(CheckoutRecordService checkoutRecordService) {
        this.checkoutRecordService = checkoutRecordService;
    }

    // Create new checkout record
    @PostMapping
    public ResponseEntity<CheckoutRecord> createCheckoutRecord(@RequestBody CheckoutRecord checkoutRecord) {
        try {
            CheckoutRecord newCheckoutRecord = checkoutRecordService.createCheckoutRecord(
                    checkoutRecord.getUser().getId(), 
                    checkoutRecord.getGear().getId(), 
                    checkoutRecord.getDueDate(), 
                    checkoutRecord.getReturnDate());
            return new ResponseEntity<>(newCheckoutRecord, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Return gear
    @PostMapping("/return/{id}")
    public ResponseEntity<CheckoutRecord> returnGear(@PathVariable Long id) {
        try {
            CheckoutRecord updatedRecord = checkoutRecordService.returnGear(id);
            return new ResponseEntity<>(updatedRecord, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // List all records from user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CheckoutRecord>> getCheckoutsForUser(@PathVariable Long userId) {
        List<CheckoutRecord> records = checkoutRecordService.findAllCheckoutsForUser(userId);
        if (records.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(records, HttpStatus.OK);
    }
}
