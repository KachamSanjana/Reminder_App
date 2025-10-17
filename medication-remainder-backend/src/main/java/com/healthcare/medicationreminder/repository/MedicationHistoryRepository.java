package com.healthcare.medicationreminder.repository;
import com.healthcare.medicationreminder.model.MedicationHistory;
import com.healthcare.medicationreminder.model.User;
import com.healthcare.medicationreminder.model.UserMedication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

public interface MedicationHistoryRepository extends JpaRepository<MedicationHistory, Long> {

	List<MedicationHistory> findByUserOrderByTakenAtDesc(User user);
	List<MedicationHistory> findByMedicationOrderByTakenAtDesc(UserMedication medication);
	 List<MedicationHistory> findByUserAndTakenAtBetween(
		        User user,
		        LocalDateTime startDate,
		        LocalDateTime endDate
		    );
	 // Get history by status
	    List<MedicationHistory> findByUserAndStatus(
	        User user,
	        MedicationHistory.MedicationStatus status
	    );
	 
	 Long countByUserAndStatus(User user, MedicationHistory.MedicationStatus status);
	    
	    // Get today's history for a user
	    @Query("SELECT h FROM MedicationHistory h WHERE h.user = :user AND " +
	           "DATE(h.takenAt) = CURRENT_DATE ORDER BY h.takenAt DESC")
	    List<MedicationHistory> findTodayHistoryForUser(@Param("user") User user);
	    
	    // Calculate adherence rate (percentage of medications taken)
	    @Query("SELECT " +
	           "(COUNT(CASE WHEN h.status = 'TAKEN' THEN 1 END) * 100.0 / COUNT(*)) " +
	           "FROM MedicationHistory h WHERE h.user = :user")
	    Double calculateAdherenceRate(@Param("user") User user);


}
