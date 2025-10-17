package com.healthcare.medicationreminder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.repository.query.Param;
import com.healthcare.medicationreminder.model.MedicineCatalog;
import java.util.List;
@Repository
public interface MedicineCatalogRepository extends JpaRepository<MedicineCatalog, Long> {
	List<MedicineCatalog> findByMedicineName(String name);
	List<MedicineCatalog> findByGenericName(String genericName);
	List<MedicineCatalog> findByCategory(String category);
	
	 @Query("SELECT m FROM MedicineCatalog m WHERE " +
	           "LOWER(m.purpose) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(m.commonUses) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<MedicineCatalog> SearchBypurpose(@Param("keyword") String keyword);
	
	// Get all medicines ordered by name
    List<MedicineCatalog> findAllByOrderByMedicineNameAsc();
	
	

}
