package com.healthcare.medicationreminder.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "reminder_schedules")
@Data

public class RemainderSchedule {

	 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication_id", nullable = false)
    private UserMedication medication;
    
    @Column(name = "reminder_time", nullable = false)
    private LocalTime reminderTime;
    
    @Column(name = "reminder_days", nullable = false, length = 50)
    private String reminderDays;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

