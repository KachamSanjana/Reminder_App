package com.healthcare.medicationreminder.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcare.medicationreminder.model.MedicineCatalog;
import com.healthcare.medicationreminder.repository.MedicineCatalogRepository;

@Service
public class MedicineCatalogService {
	@Autowired
	private MedicineCatalogRepository catalogRepository;
	
	   public List<MedicineCatalog> getAllMedicines() {
	        return catalogRepository.findAllByOrderByMedicineNameAsc();
	    }
	   public Optional<MedicineCatalog> getMedicineById(Long id) {
	        return catalogRepository.findById(id);
	    }
	   public List<MedicineCatalog> searchMedicineByName(String name) {
	        return catalogRepository.findByMedicineName(name);
	    }
	   
	   public List<MedicineCatalog> searchByGenericName(String genericName) {
	        return catalogRepository.findByGenericName(genericName);
	    }
	   public List<MedicineCatalog> getMedicinesByCategory(String category) {
	        return catalogRepository.findByCategory(category);
	    }
	   public List<MedicineCatalog> searchBySymptom(String symptom) {
	        return catalogRepository.SearchBypurpose(symptom);
	    }
	   public MedicineCatalog addMedicine(MedicineCatalog medicine) {
	        return catalogRepository.save(medicine);
	    }
	   public MedicineCatalog updateMedicine(MedicineCatalog medicine) {
	        return catalogRepository.save(medicine);
	    }
	   public void deleteMedicine(Long medicineId) {
	        catalogRepository.deleteById(medicineId);
	    }
	   public Long getTotalMedicineCount() {
	        return catalogRepository.count();
	    }
	   

}
