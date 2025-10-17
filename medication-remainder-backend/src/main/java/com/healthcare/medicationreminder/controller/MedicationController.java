package com.healthcare.medicationreminder.controller;
import com.healthcare.medicationreminder.model.*;
import com.healthcare.medicationreminder.service.MedicationService;
import com.healthcare.medicationreminder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/medications")
@CrossOrigin(origins = "http://localhost:3000")
public class MedicationController {

	 @Autowired
	    private MedicationService medicationService;
	    
	    @Autowired
	    private UserService userService;
	
	    
//	    Add new medication
//	    POST /api/medications?userId=1
	    @PostMapping
	    public ResponseEntity<?> addMedication(@RequestBody UserMedication medication, 
	                                          @RequestParam Long userId) {
	        try {
	            // Get user
	            Optional<User> userOpt = userService.getUserById(userId);
	            if (userOpt.isEmpty()) {
	                Map<String, Object> response = new HashMap<>();
	                response.put("success", false);
	                response.put("message", "User not found");
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	            }
	            
	            medication.setUser(userOpt.get());
	            UserMedication savedMedication = medicationService.addMedication(medication);
	            
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", true);
	            response.put("message", "Medication added successfully!");
	            response.put("medication", savedMedication);
	            
	            return ResponseEntity.status(HttpStatus.CREATED).body(response);
	        } catch (Exception e) {
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", false);
	            response.put("message", "Failed to add medication: " + e.getMessage());
	            
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        }
	    }
	    @GetMapping("/user/{userId}")
	    public ResponseEntity<?> getUserMedications(@PathVariable Long userId) {
	        try {
	            Optional<User> userOpt = userService.getUserById(userId);
	            if (userOpt.isEmpty()) {
	                Map<String, Object> response = new HashMap<>();
	                response.put("success", false);
	                response.put("message", "User not found");
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	            }
	            
	            List<UserMedication> medications = medicationService.getUserMedications(userOpt.get());
	            return ResponseEntity.ok(medications);
	        } catch (Exception e) {
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", false);
	            response.put("message", "Failed to fetch medications");
	            
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        }
	    }
//	    GET /api/medications/user/{userId}/active
	    @GetMapping("/user/{userId}/active")
	    public ResponseEntity<?> getActiveMedications(@PathVariable Long userId) {
	        try {
	            Optional<User> userOpt = userService.getUserById(userId);
	            if (userOpt.isEmpty()) {
	                Map<String, Object> response = new HashMap<>();
	                response.put("success", false);
	                response.put("message", "User not found");
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	            }
	            
	            List<UserMedication> medications = medicationService.getActiveMedications(userOpt.get());
	            return ResponseEntity.ok(medications);
	        } catch (Exception e) {
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", false);
	            response.put("message", "Failed to fetch active medications");
	            
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        }
	    }
	    
	    @GetMapping("/{id}")
	    public ResponseEntity<?> getMedicationById(@PathVariable Long id) {
	        Optional<UserMedication> medication = medicationService.getMedicationById(id);
	        
	        if (medication.isPresent()) {
	            return ResponseEntity.ok(medication.get());
	        } else {
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", false);
	            response.put("message", "Medication not found");
	            
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	    }
	    @PutMapping("/{id}")
	    public ResponseEntity<?> updateMedication(@PathVariable Long id, 
	                                             @RequestBody UserMedication medication) {
	        try {
	        	medication.setMedicationId(id);

	            UserMedication updatedMedication = medicationService.updateMedication(medication);
	            
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", true);
	            response.put("message", "Medication updated successfully!");
	            response.put("medication", updatedMedication);
	            
	            return ResponseEntity.ok(response);
	        } catch (Exception e) {
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", false);
	            response.put("message", "Failed to update medication");
	            
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        }
	    }
	
	    @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteMedication(@PathVariable Long id) {
	        try {
	            medicationService.deleteMedication(id);
	            
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", true);
	            response.put("message", "Medication deleted successfully!");
	            
	            return ResponseEntity.ok(response);
	        } catch (Exception e) {
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", false);
	            response.put("message", "Failed to delete medication");
	            
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        }
	    }
	    
	    @PutMapping("/{id}/deactivate")
	    public ResponseEntity<?> deactivateMedication(@PathVariable Long id) {
	        try {
	            UserMedication medication = medicationService.deactivateMedication(id);
	            
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", true);
	            response.put("message", "Medication deactivated successfully!");
	            response.put("medication", medication);
	            
	            return ResponseEntity.ok(response);
	        } catch (Exception e) {
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", false);
	            response.put("message", "Failed to deactivate medication");
	            
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        }
	    }
	        @PostMapping("/{medicationId}/reminders")
	        public ResponseEntity<?> addReminderSchedule(@PathVariable Long medicationId,
	                                                     @RequestBody RemainderSchedule schedule) {
	            try {
	                Optional<UserMedication> medicationOpt = medicationService.getMedicationById(medicationId);
	                if (medicationOpt.isEmpty()) {
	                    Map<String, Object> response = new HashMap<>();
	                    response.put("success", false);
	                    response.put("message", "Medication not found");
	                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	                }
	                
	                schedule.setMedication(medicationOpt.get());
	                RemainderSchedule savedSchedule = medicationService.addRemainderSchedule(schedule);
	                
	                Map<String, Object> response = new HashMap<>();
	                response.put("success", true);
	                response.put("message", "Reminder added successfully!");
	                response.put("schedule", savedSchedule);
	                
	                return ResponseEntity.status(HttpStatus.CREATED).body(response);
	            } catch (Exception e) {
	                Map<String, Object> response = new HashMap<>();
	                response.put("success", false);
	                response.put("message", "Failed to add reminder");
	                
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	            }
	        }
	        
//	        GET /api/medications/{medicationId}/reminders
	        @GetMapping("/{medicationId}/reminders")
	        public ResponseEntity<?> getMedicationReminders(@PathVariable Long medicationId) {
	            try {
	                Optional<UserMedication> medicationOpt = medicationService.getMedicationById(medicationId);
	                if (medicationOpt.isEmpty()) {
	                    Map<String, Object> response = new HashMap<>();
	                    response.put("success", false);
	                    response.put("message", "Medication not found");
	                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	                }
	                
	                List<RemainderSchedule> reminders = medicationService.getMedicationReminders(medicationOpt.get());
	                return ResponseEntity.ok(reminders);
	            } catch (Exception e) {
	                Map<String, Object> response = new HashMap<>();
	                response.put("success", false);
	                response.put("message", "Failed to fetch reminders");
	                
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	            }
	        }
	        
//	        POST /api/medications/{medicationId}/taken?userId=1
	        @PostMapping("/{medicationId}/taken")
	        public ResponseEntity<?> markAsTaken(@PathVariable Long medicationId,
	                                            @RequestParam Long userId) {
	            try {
	                Optional<UserMedication> medicationOpt = medicationService.getMedicationById(medicationId);
	                Optional<User> userOpt = userService.getUserById(userId);
	                
	                if (medicationOpt.isEmpty() || userOpt.isEmpty()) {
	                    Map<String, Object> response = new HashMap<>();
	                    response.put("success", false);
	                    response.put("message", "Medication or user not found");
	                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	                }
	                
	                MedicationHistory history = medicationService.markAs(medicationOpt.get(), userOpt.get());
	                
	                Map<String, Object> response = new HashMap<>();
	                response.put("success", true);
	                response.put("message", "Medication marked as taken!");
	                response.put("history", history);
	                
	                return ResponseEntity.ok(response);
	            } catch (Exception e) {
	                Map<String, Object> response = new HashMap<>();
	                response.put("success", false);
	                response.put("message", "Failed to mark as taken");
	                
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	            }
	        }
	        
	        @GetMapping("/history/user/{userId}")
	        public ResponseEntity<?> getUserMedicationHistory(@PathVariable Long userId) {
	            try {
	                Optional<User> userOpt = userService.getUserById(userId);
	                if (userOpt.isEmpty()) {
	                    Map<String, Object> response = new HashMap<>();
	                    response.put("success", false);
	                    response.put("message", "User not found");
	                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	                }
	                
	                List<MedicationHistory> history = medicationService.getUserMedicationHistory(userOpt.get());
	                return ResponseEntity.ok(history);
	            } catch (Exception e) {
	                Map<String, Object> response = new HashMap<>();
	                response.put("success", false);
	                response.put("message", "Failed to fetch history");
	                
	                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	            }
	        } 
	        
//	        GET /api/medications/user/{userId}/refill-needed
	        
	       @GetMapping("/user/{userId}/refill-needed")
	       public ResponseEntity<?> getMedicationsNeedingRefill(@PathVariable Long userId) {
	           try {
	               Optional<User> userOpt = userService.getUserById(userId);
	               if (userOpt.isEmpty()) {
	                   Map<String, Object> response = new HashMap<>();
	                   response.put("success", false);
	                   response.put("message", "User not found");
	                   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	               }
	               
	               List<UserMedication> medications = medicationService.getMedicationsNeedingRefill(userOpt.get());
	               return ResponseEntity.ok(medications);
	           } catch (Exception e) {
	               Map<String, Object> response = new HashMap<>();
	               response.put("success", false);
	               response.put("message", "Failed to fetch medications needing refill");
	               
	               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	           }
	       }
	       
	       
	       @GetMapping("/user/{userId}/ending-soon")
	       public ResponseEntity<?> getMedicationsEndingSoon(@PathVariable Long userId) {
	           try {
	               Optional<User> userOpt = userService.getUserById(userId);
	               if (userOpt.isEmpty()) {
	                   Map<String, Object> response = new HashMap<>();
	                   response.put("success", false);
	                   response.put("message", "User not found");
	                   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	               }
	               
	               List<UserMedication> medications = medicationService.getMedicationsEndingSoon(userOpt.get());
	               return ResponseEntity.ok(medications);
	           } catch (Exception e) {
	               Map<String, Object> response = new HashMap<>();
	               response.put("success", false);
	               response.put("message", "Failed to fetch medications ending soon");
	               
	               return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	           }
	       }
//	       GET /api/medications/user/{userId}/adherence-rate
	       
	      @GetMapping("/user/{userId}/adherence-rate")
	      public ResponseEntity<?> getAdherenceRate(@PathVariable Long userId) {
	          try {
	              Optional<User> userOpt = userService.getUserById(userId);
	              if (userOpt.isEmpty()) {
	                  Map<String, Object> response = new HashMap<>();
	                  response.put("success", false);
	                  response.put("message", "User not found");
	                  return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	              }
	              
	              Double adherenceRate = medicationService.calculateAdherenceRate(userOpt.get());
	              
	              Map<String, Object> response = new HashMap<>();
	              response.put("adherenceRate", adherenceRate);
	              response.put("percentage", String.format("%.2f%%", adherenceRate));
	              
	              return ResponseEntity.ok(response);
	          } catch (Exception e) {
	              Map<String, Object> response = new HashMap<>();
	              response.put("success", false);
	              response.put("message", "Failed to calculate adherence rate");
	              
	              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	          }
	      }
}
