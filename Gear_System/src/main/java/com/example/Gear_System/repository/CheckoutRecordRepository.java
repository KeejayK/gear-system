package com.example.Gear_System.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.Gear_System.model.CheckoutRecord;
import com.example.Gear_System.model.Gear;
import com.example.Gear_System.model.User;

public interface CheckoutRecordRepository extends JpaRepository<CheckoutRecord, Long> {
    List<CheckoutRecord> findByUser(User user);
    List<CheckoutRecord> findByGear(Gear gear);
    List<CheckoutRecord> findByReturnDate(LocalDate returnDate);
    List<CheckoutRecord> findByIsReturned(boolean isReturned);
    @Query("SELECT c FROM CheckoutRecord c WHERE c.dueDate < :date")
    List<CheckoutRecord> findOverdueRecords(LocalDate date);
    List<CheckoutRecord> findByCheckoutDateBetween(LocalDate start, LocalDate end);
}