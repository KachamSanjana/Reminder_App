package com.healthcare.medicationreminder.repository;

import com.healthcare.medicationreminder.model.RemainderSchedule;
import com.healthcare.medicationreminder.model.UserMedication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalTime;
import java.util.List;
public interface ReminderScheduleRepository extends JpaRepository<RemainderSchedule, Long> {
	
List<RemainderSchedule> findByMedication(UserMedication medication);
    
    // Get all active reminders for a specific medication
    List<RemainderSchedule> findByMedicationAndIsActiveTrue(UserMedication medication);
    
    // Get all reminders for a specific time
    List<RemainderSchedule> findByReminderTimeAndIsActiveTrue(LocalTime time);
    
    // Get all active reminders
    List<RemainderSchedule> findByIsActiveTrue();
    
    // Delete all reminders for a medication
    void deleteByMedication(UserMedication medication);
    
    // Count active reminders for a medication
    Long countByMedicationAndIsActiveTrue(UserMedication medication);
	

}
