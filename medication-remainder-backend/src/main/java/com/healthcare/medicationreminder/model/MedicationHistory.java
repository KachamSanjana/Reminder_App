package com.healthcare.medicationreminder.model;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "medication_history")
@Data

public class MedicationHistory {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "history_id")
	    private Long historyId;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "medication_id", nullable = false)
	    private UserMedication medication;
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "user_id", nullable = false)
	    private User user;
	    
	    @Column(name = "taken_at", nullable = false)
	    private LocalDateTime takenAt;
	    
	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private MedicationStatus status;
	    
	    @Column(columnDefinition = "TEXT")
	    private String notes;
	    
	    @Column(name = "created_at", nullable = false, updatable = false)
	    private LocalDateTime createdAt;
	    
	    @PrePersist
	    protected void onCreate() {
	        createdAt = LocalDateTime.now();
	    }
	    
	    public enum MedicationStatus {
	        TAKEN, MISSED, SKIPPED
	    }
	}

