package com.example.Gear_System.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class CheckoutRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Gear gear;

    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private boolean isReturned = false;

    public CheckoutRecord() {}

    public CheckoutRecord(User user, Gear gear, LocalDate checkoutDate, LocalDate dueDate, LocalDate returnDate) {
        this.user = user;
        this.gear = gear;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.isReturned = false; 
    }

    public Long getId() {
        return id;
    }

  
    public User getUser() {
        return user;
    }

    

    public Gear getGear() {
        return gear;
    }

    
    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
    

    public LocalDate getReturnDate() {
        return returnDate;
    }

    

    public boolean isReturned() {
        return isReturned;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setGear(Gear gear) {
        this.gear = gear;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }


    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }


   
   
    @Override
    public String toString() {
        return "CheckoutRecord{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", gear='" + gear + '\'' +
                ", checkoutDate='" + checkoutDate + '\'' +
                ", returnDate=" + returnDate + '\'' +
                ", isReturned=" + isReturned +
                '}';
    }
    

}
