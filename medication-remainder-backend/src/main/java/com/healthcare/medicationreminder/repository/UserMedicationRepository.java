package com.healthcare.medicationreminder.repository;
import com.healthcare.medicationreminder.model.User;
import com.healthcare.medicationreminder.model.UserMedication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserMedicationRepository extends JpaRepository<UserMedication, Long>{
	
	List<UserMedication> findByUser(User user);
	
	 List<UserMedication> findByUserAndIsActiveTrue(User user);
	 
	   List<UserMedication> findByUserOrderByStartDateDesc(User user);
	   
//	   @Query("select m from UserMedication m where m.user=:user AND "+" m.isActive=true AND m.currentStock<=m.refillRemainderAt")
	 
	   @Query("SELECT m FROM UserMedication m WHERE m.user = :user AND " +
	           "m.isActive = true AND m.currentStock <= m.refillReminderAt")
	   
	   List<UserMedication> findMedicationRefill(@Param("user") User user);
	   @Query("SELECT m FROM UserMedication m WHERE m.user = :user AND " +
	           "m.isActive = true AND m.endDate BETWEEN :today AND :endDate")
	    List<UserMedication> findMedicationsEndingSoon(
	        @Param("user") User user,
	        @Param("today") LocalDate today,
	        @Param("endDate") LocalDate endDate
	    );
	   Long countByUserAndIsActiveTrue(User user);
}
