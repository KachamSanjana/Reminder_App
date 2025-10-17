package com.healthcare.medicationreminder.model;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Data
@Table(name="medicine_catalog")
public class MedicineCatalog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicine_id")
    private Long medicineId;
    
    @Column(name = "medicine_name", nullable = false, length = 100)
    private String medicineName;
    
    @Column(name = "generic_name", length = 100)
    private String genericName;
    
    @Column(length = 50)
    private String category;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String purpose;
    
    @Column(name = "common_uses", nullable = false, columnDefinition = "TEXT")
    private String commonUses;
    
    @Column(name = "dosage_forms", length = 100)
    private String dosageForms;
    
    @Column(name = "side_effects", columnDefinition = "TEXT")
    private String sideEffects;
    
    @Column(columnDefinition = "TEXT")
    private String precautions;
    @Column(name="expiry_date")
    private LocalDate expiryDate;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
    
    
    

