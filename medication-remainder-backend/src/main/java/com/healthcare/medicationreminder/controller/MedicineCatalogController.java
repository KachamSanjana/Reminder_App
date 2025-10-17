package com.healthcare.medicationreminder.controller;
import com.healthcare.medicationreminder.model.MedicineCatalog;
import com.healthcare.medicationreminder.service.MedicineCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicines")
@CrossOrigin(origins = "http://localhost:3000")
public class MedicineCatalogController {
	@Autowired
    private MedicineCatalogService catalogService;
	
	 @GetMapping
	    public ResponseEntity<List<MedicineCatalog>> getAllMedicines() {
	        List<MedicineCatalog> medicines = catalogService.getAllMedicines();
	        return ResponseEntity.ok(medicines);
	    }
	
	 @GetMapping("/{id}")
	    public ResponseEntity<?> getMedicineById(@PathVariable Long id) {
	        Optional<MedicineCatalog> medicine = catalogService.getMedicineById(id);
	        
	        if (medicine.isPresent()) {
	            return ResponseEntity.ok(medicine.get());
	        } else {
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", false);
	            response.put("message", "Medicine not found");
	            
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	    }
	 
	 
	 @GetMapping("/search")
	    public ResponseEntity<List<MedicineCatalog>> searchMedicineByName(@RequestParam String name) {
	        List<MedicineCatalog> medicines = catalogService.searchMedicineByName(name);
	        return ResponseEntity.ok(medicines);
	    }
	    
	 @GetMapping("/search-generic")
	    public ResponseEntity<List<MedicineCatalog>> searchByGenericName(@RequestParam String genericName) {
	        List<MedicineCatalog> medicines = catalogService.searchByGenericName(genericName);
	        return ResponseEntity.ok(medicines);
	    }
	 @GetMapping("/search-symptom")
	    public ResponseEntity<List<MedicineCatalog>> searchBySymptom(@RequestParam String symptom) {
	        List<MedicineCatalog> medicines = catalogService.searchBySymptom(symptom);
	        return ResponseEntity.ok(medicines);
	    }
	 
	 @GetMapping("/category")
	    public ResponseEntity<List<MedicineCatalog>> getMedicinesByCategory(@RequestParam String category) {
	        List<MedicineCatalog> medicines = catalogService.getMedicinesByCategory(category);
	        return ResponseEntity.ok(medicines);
	    }
	 
//	 POST /api/medicines
	 @PostMapping
	    public ResponseEntity<?> addMedicine(@RequestBody MedicineCatalog medicine) {
	        try {
	            MedicineCatalog savedMedicine = catalogService.addMedicine(medicine);
	            
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", true);
	            response.put("message", "Medicine added successfully!");
	            response.put("medicine", savedMedicine);
	            
	            return ResponseEntity.status(HttpStatus.CREATED).body(response);
	        } catch (Exception e) {
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", false);
	            response.put("message", "Failed to add medicine");
	            
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        }
	    }
	 
//	 PUT /api/medicines/{id}
	 
	 @PutMapping("/{id}")
	    public ResponseEntity<?> updateMedicine(@PathVariable Long id, @RequestBody MedicineCatalog medicine) {
	        try {
	            medicine.setMedicineId(id);
	            MedicineCatalog updatedMedicine = catalogService.updateMedicine(medicine);
	            
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", true);
	            response.put("message", "Medicine updated successfully!");
	            response.put("medicine", updatedMedicine);
	            
	            return ResponseEntity.ok(response);
	        } catch (Exception e) {
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", false);
	            response.put("message", "Failed to update medicine");
	            
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        }
	    }
//	 DELETE /api/medicines/{id}
	 
	 @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteMedicine(@PathVariable Long id) {
	        try {
	            catalogService.deleteMedicine(id);
	            
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", true);
	            response.put("message", "Medicine deleted successfully!");
	            
	            return ResponseEntity.ok(response);
	        } catch (Exception e) {
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", false);
	            response.put("message", "Failed to delete medicine");
	            
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        }
	    }
//	 GET /api/medicines/count
    
    @GetMapping("/count")
    public ResponseEntity<?> getTotalMedicineCount() {
        Long count = catalogService.getTotalMedicineCount();
        
        Map<String, Object> response = new HashMap<>();
        response.put("totalMedicines", count);
        
        return ResponseEntity.ok(response);
    }
	 
	 
	 
}
