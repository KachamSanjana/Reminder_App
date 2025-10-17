package com.healthcare.medicationreminder.service;

import com.healthcare.medicationreminder.model.MedicationHistory;
import com.healthcare.medicationreminder.model.MedicationHistory.MedicationStatus;
import com.healthcare.medicationreminder.model.RemainderSchedule;
//import com.healthcare.medicationreminder.model.ReminderSchedule;
import com.healthcare.medicationreminder.model.User;
import com.healthcare.medicationreminder.model.UserMedication;
import com.healthcare.medicationreminder.repository.MedicationHistoryRepository;
import com.healthcare.medicationreminder.repository.ReminderScheduleRepository;
import com.healthcare.medicationreminder.repository.UserMedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class MedicationService {

	 @Autowired
	    private UserMedicationRepository medicationRepository;
	    
	    @Autowired
	    private ReminderScheduleRepository scheduleRepository;
	    
	    @Autowired
	    private MedicationHistoryRepository historyRepository;
	    
	   
	    public UserMedication addMedication(UserMedication medication) {
	        // Calculate end date if duration is provided
	        if (medication.getDurationDays() != null && medication.getDurationDays() > 0) {
	            LocalDate endDate = medication.getStartDate().plusDays(medication.getDurationDays());
	            medication.setEndDate(endDate);
	        }
	        
	        medication.setIsActive(true);
	        
	        return medicationRepository.save(medication);
	    }
	    public List<UserMedication> getUserMedications(User user) {
	        return medicationRepository.findByUser(user);
	    }
	    public List<UserMedication> getActiveMedications(User user) {
	        return medicationRepository.findByUserAndIsActiveTrue(user);
	    }
	    public Optional<UserMedication> getMedicationById(Long medicationId) {
	        return medicationRepository.findById(medicationId);
	    }
	    
	    public UserMedication updateMedication(UserMedication medication) {
	        return medicationRepository.save(medication);
	    }
	    
//	    Delete any record
	    @Transactional
	    public void deleteMedication(Long medicationId)
	    {
	    	UserMedication medication=medicationRepository.findById(medicationId).orElseThrow(()->new RuntimeException("Medication is not found"));
	    	
	    	scheduleRepository.deleteByMedication(medication);
	    	historyRepository.deleteById(medicationId);
	    	   medicationRepository.deleteById(medicationId);
	    }
	    
	    public UserMedication deactivateMedication(Long medicationId) {
	    	
	    	UserMedication medication=medicationRepository.findById(medicationId).orElseThrow(()->new RuntimeException("Medication is not found"));
	    	medication.setIsActive(false);
	    	return medicationRepository.save(medication);
	    }
	    
//	Add remainder to medication
	    public RemainderSchedule addRemainderSchedule(RemainderSchedule schedule)
	    		{
	    	return scheduleRepository.save(schedule);
	    		}
	    public List<RemainderSchedule> getMedicationReminders(UserMedication medication) {
	        return scheduleRepository.findByMedicationAndIsActiveTrue(medication);
	    }
	    public void deleteReminderSchedule(Long scheduleId) {
	        scheduleRepository.deleteById(scheduleId);
	    }
	    
//	    Mark medication as taken
	    public MedicationHistory markAs(UserMedication medication,User user)
	    {
	    	MedicationHistory history=new MedicationHistory();
	    	history.setMedication(medication);
	    	history.setUser(user);
	    	history.setTakenAt(LocalDateTime.now());
	    	history.setStatus(MedicationHistory.MedicationStatus.TAKEN);
	    	
	     if(medication.getCurrentStock()!=null && medication.getCurrentStock()>0)
	     {
	    	 medication.setCurrentStock(medication.getCurrentStock()-1);
	    	 medicationRepository.save(medication);
	     }
	    	return historyRepository.save(history);
	    }
	    public MedicationHistory markAsMissed(UserMedication medication, User user) {
	        MedicationHistory history = new MedicationHistory();
	        history.setMedication(medication);
	        history.setUser(user);
	        history.setTakenAt(LocalDateTime.now());
	        history.setStatus(MedicationHistory.MedicationStatus.MISSED);
	        
	        return historyRepository.save(history);
	    }
	    public List<MedicationHistory> getUserMedicationHistory(User user) {
	        return historyRepository.findByUserOrderByTakenAtDesc(user);
	    }
//	    Get today's medication history
	    public List<MedicationHistory> getTodayHistory(User user) {
	        return historyRepository.findTodayHistoryForUser(user);
	    }
	    public List<UserMedication> getMedicationsNeedingRefill(User user) {
	        return medicationRepository.findMedicationRefill(user);
	    }
	    
//	    medication ending 7 days
	    public List<UserMedication> getMedicationsEndingSoon(User user) {
	        LocalDate today = LocalDate.now();
	        LocalDate endDate = today.plusDays(7);
	        return medicationRepository.findMedicationsEndingSoon(user, today, endDate);
	    } 
	    
	    public Double calculateAdherenceRate(User user) {
	        Double rate = historyRepository.calculateAdherenceRate(user);
	        return rate != null ? rate : 0.0;
	    }
	    
	    public Long getActiveMedicationCount(User user) {
	        return medicationRepository.countByUserAndIsActiveTrue(user);
	    }
}
