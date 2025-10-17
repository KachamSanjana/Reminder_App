package com.healthcare.medicationreminder.model;

import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "user_medications")
@Data
public class UserMedication {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "medication_id")
	    private Long medicationId;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id")
    private MedicineCatalog medicine;
    
    @Column(name = "custom_medicine_name", length = 100)
    private String customMedicineName;
    
    @Column(nullable = false, length = 50)
    private String dosage;
    
    @Column(nullable = false, length = 50)
    private String frequency;
    
    @Column(name = "duration_days")
    private Integer durationDays;
    
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @Column(name = "end_date")
    private LocalDate endDate;
    
    @Column(name = "current_stock")
    private Integer currentStock;
    
    @Column(name = "refill_reminder_at")
    private Integer refillReminderAt;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
