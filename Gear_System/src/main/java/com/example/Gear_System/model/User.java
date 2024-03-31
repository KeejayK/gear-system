package com.example.Gear_System.model;


import java.time.LocalDate;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "`user`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private boolean isOfficer;
    private boolean isVerified;
    private LocalDate joinDate;
    private LocalDate expirationDate;

    public User() {}


    public User(String username, String password, String email, boolean isOfficer, boolean isVerified, LocalDate joinDate, LocalDate expirationDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.isOfficer = isOfficer;
        this.isVerified = isVerified;
        this.joinDate = joinDate;
        this.expirationDate = expirationDate;
    }

    // So many getters

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean isOfficer() {
        return isOfficer;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }
    
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    // So many setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOfficer(boolean officer) {
        this.isOfficer = officer;
    }

    public void setVerified(boolean verified) {
        this.isVerified = verified;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", isOfficer=" + isOfficer + '\'' +
                ", isVerified=" + isVerified +
                ", joinDate=" + joinDate + '\'' +
                ", expirationDate=" + expirationDate + 
                '}';
    }
    
}
