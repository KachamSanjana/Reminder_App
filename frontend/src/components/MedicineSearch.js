import React, { useState } from 'react';
import { medicineService } from '../services/medicineService'; 
import '../styles/MedicineSearch.css';

function MedicineSearch() {
  const [searchType, setSearchType] = useState('name');
  const [searchQuery, setSearchQuery] = useState('');
  const [medicines, setMedicines] = useState([]);
  const [selectedMedicine, setSelectedMedicine] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleSearch = async () => {
    if (!searchQuery.trim()) {
      alert('Please enter a search term');
      return;
    }

    setLoading(true);
    try {
      // 
      const response = await medicineService.searchMedicines(searchQuery, searchType);

      if (!response.data || !response.data.data || response.data.data.length === 0) {
        setMedicines([]);
        alert('No medicines found for the given search.');
        return;
      }

      // Map OpenFDA results to your frontend fields
      const mappedMedicines = response.data.data.map((med) => ({
        medicineName: med.openfda?.brand_name?.[0] || 'Unknown',
        genericName: med.openfda?.generic_name?.[0] || 'Unknown',
        purpose: med.purpose?.[0] || 'Not available',
        commonUses: med.indications_and_usage?.[0] || 'Not available',
        dosageForms: med.dosage_form?.[0] || 'Not available',
        sideEffects: med.adverse_reactions?.[0] || 'Not available',
        precautions: med.warnings?.[0] || 'Not available',
        category: med.pharm_class?.[0] || 'Not available',
        medicineId: med.id || Math.random(), // fallback ID
      }));

      setMedicines(mappedMedicines);
    } catch (error) {
      console.error('Error searching medicines:', error);
      alert('Failed to search medicines. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleKeyPress = (e) => {
    if (e.key === 'Enter') handleSearch();
  };

  return (
    <div className="medicine-search-container">
      <div className="search-header">
        <h2 className="search-title">
          <span className="title-icon">🔍</span>
          Discover Medicines
        </h2>
        <p className="search-subtitle">Search by medicine name or symptoms you're experiencing</p>
      </div>

      <div className="search-card">
        <div className="search-type-selector">
          <label className={`type-option ${searchType === 'name' ? 'selected' : ''}`}>
            <input
              type="radio"
              value="name"
              checked={searchType === 'name'}
              onChange={(e) => setSearchType(e.target.value)}
            />
            <span className="option-content">Medicine Name</span>
          </label>

          <label className={`type-option ${searchType === 'symptom' ? 'selected' : ''}`}>
            <input
              type="radio"
              value="symptom"
              checked={searchType === 'symptom'}
              onChange={(e) => setSearchType(e.target.value)}
            />
            <span className="option-content">Symptom/Condition</span>
          </label>
        </div>

        <div className="search-input-group">
          <input
            type="text"
            className="search-input"
            placeholder={searchType === 'name' ? 'e.g., Paracetamol, Aspirin...' : 'e.g., fever, headache, allergy...'}
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            onKeyPress={handleKeyPress}
          />
          <button className="search-btn" onClick={handleSearch} disabled={loading}>
            {loading ? 'Searching...' : 'Search'}
          </button>
        </div>
      </div>

      {/* Results */}
      <div className="results-section">
        {medicines.length > 0 ? (
          <div className="medicine-grid">
            {medicines.map((medicine) => (
              <div key={medicine.medicineId} className="medicine-card">
                <h3>{medicine.medicineName}</h3>
                <p>{medicine.genericName}</p>
                <p>{medicine.purpose}</p>
                <button onClick={() => setSelectedMedicine(medicine)}>View Full Details</button>
              </div>
            ))}
          </div>
        ) : searchQuery && !loading ? (
          <div>No medicines found</div>
        ) : null}
      </div>

      {/* Modal */}
      {selectedMedicine && (
        <div className="modal-overlay" onClick={() => setSelectedMedicine(null)}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <button onClick={() => setSelectedMedicine(null)}>Close</button>
            <h2>{selectedMedicine.medicineName}</h2>
            <p>Generic: {selectedMedicine.genericName}</p>
            <p>Purpose: {selectedMedicine.purpose}</p>
            <p>Common Uses: {selectedMedicine.commonUses}</p>
            <p>Forms: {selectedMedicine.dosageForms}</p>
            <p>Side Effects: {selectedMedicine.sideEffects}</p>
            <p>Precautions: {selectedMedicine.precautions}</p>
            <p>Category: {selectedMedicine.category}</p>
          </div>
        </div>
      )}
    </div>
  );
}

export default MedicineSearch;






































































































// import React, { useState } from 'react';
// import { medicineService } from '../services/medicineService';
// import '../styles/MedicineSearch.css';

// function MedicineSearch() {
//   const [searchType, setSearchType] = useState('name');
//   const [searchQuery, setSearchQuery] = useState('');
//   const [medicines, setMedicines] = useState([]);
//   const [selectedMedicine, setSelectedMedicine] = useState(null);
//   const [loading, setLoading] = useState(false);

//   const handleSearch = async () => {
//     if (!searchQuery.trim()) {
//       alert('Please enter a search term');
//       return;
//     }

//     setLoading(true);
//     try {
//       let response;
//       if (searchType === 'name') {
//         response = await medicineService.searchByName(searchQuery);
//       } else {
//         response = await medicineService.searchBySymptom(searchQuery);
//       }
//       setMedicines(response.data);
//     } catch (error) {
//       console.error('Error searching medicines:', error);
//       alert('Failed to search medicines. Please try again.');
//     } finally {
//       setLoading(false);
//     }
//   };

//   const handleKeyPress = (e) => {
//     if (e.key === 'Enter') {
//       handleSearch();
//     }
//   };

//   return (
//     <div className="medicine-search-container">
//       <div className="search-header">
//         <h2 className="search-title">
//           <span className="title-icon">🔍</span>
//           Discover Medicines
//         </h2>
//         <p className="search-subtitle">Search by medicine name or symptoms you're experiencing</p>
//       </div>

//       <div className="search-card">
//         <div className="search-type-selector">
//           <label className={`type-option ${searchType === 'name' ? 'selected' : ''}`}>
//             <input
//               type="radio"
//               value="name"
//               checked={searchType === 'name'}
//               onChange={(e) => setSearchType(e.target.value)}
//             />
//             <span className="option-content">
//               <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor">
//                 <path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/>
//                 <circle cx="12" cy="7" r="4"/>
//               </svg>
//               Medicine Name
//             </span>
//           </label>

//           <label className={`type-option ${searchType === 'symptom' ? 'selected' : ''}`}>
//             <input
//               type="radio"
//               value="symptom"
//               checked={searchType === 'symptom'}
//               onChange={(e) => setSearchType(e.target.value)}
//             />
//             <span className="option-content">
//               <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor">
//                 <path d="M22 12h-4l-3 9L9 3l-3 9H2"/>
//               </svg>
//               Symptom/Condition
//             </span>
//           </label>
//         </div>

//         <div className="search-input-group">
//           <div className="search-input-wrapper">
//             <svg className="search-icon" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor">
//               <circle cx="11" cy="11" r="8"/>
//               <path d="m21 21-4.35-4.35"/>
//             </svg>
//             <input
//               type="text"
//               className="search-input"
//               placeholder={searchType === 'name' ? 'e.g., Paracetamol, Aspirin...' : 'e.g., fever, headache, allergy...'}
//               value={searchQuery}
//               onChange={(e) => setSearchQuery(e.target.value)}
//               onKeyPress={handleKeyPress}
//             />
//           </div>
//           <button className="search-btn" onClick={handleSearch} disabled={loading}>
//             {loading ? (
//               <span className="loading-spinner"></span>
//             ) : (
//               <>
//                 <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor">
//                   <circle cx="11" cy="11" r="8"/>
//                   <path d="m21 21-4.35-4.35"/>
//                 </svg>
//                 Search
//               </>
//             )}
//           </button>
//         </div>
//       </div>

//       {/* Results */}
//       <div className="results-section">
//         {medicines.length > 0 ? (
//           <>
//             <div className="results-header">
//               <h3>Found {medicines.length} medicine(s)</h3>
//             </div>
//             <div className="medicine-grid">
//               {medicines.map((medicine) => (
//                 <div key={medicine.medicineId} className="medicine-card">
//                   <div className="medicine-card-header">
//                     <div className="medicine-icon">💊</div>
//                     <span className="medicine-category">{medicine.category}</span>
//                   </div>
                  
//                   <h3 className="medicine-name">{medicine.medicineName}</h3>
//                   <p className="medicine-generic">{medicine.genericName}</p>
                  
//                   <div className="medicine-purpose">
//                     <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor">
//                       <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/>
//                     </svg>
//                     <span>{medicine.purpose}</span>
//                   </div>

//                   <button 
//                     className="view-details-btn"
//                     onClick={() => setSelectedMedicine(medicine)}
//                   >
//                     View Full Details
//                     <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor">
//                       <path d="M5 12h14M12 5l7 7-7 7"/>
//                     </svg>
//                   </button>
//                 </div>
//               ))}
//             </div>
//           </>
//         ) : searchQuery && !loading ? (
//           <div className="no-results">
//             <div className="no-results-icon">🔍</div>
//             <h3>No medicines found</h3>
//             <p>Try a different search term or check your spelling</p>
//           </div>
//         ) : null}
//       </div>

//       {/* Medicine Details Modal */}
//       {selectedMedicine && (
//         <div className="modal-overlay" onClick={() => setSelectedMedicine(null)}>
//           <div className="modal-content" onClick={(e) => e.stopPropagation()}>
//             <button className="modal-close" onClick={() => setSelectedMedicine(null)}>
//               <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor">
//                 <line x1="18" y1="6" x2="6" y2="18"/>
//                 <line x1="6" y1="6" x2="18" y2="18"/>
//               </svg>
//             </button>

//             <div className="modal-header">
//               <div className="modal-icon">💊</div>
//               <div>
//                 <h2>{selectedMedicine.medicineName}</h2>
//                 <p className="modal-generic">{selectedMedicine.genericName}</p>
//               </div>
//             </div>

//             <div className="modal-body">
//               <div className="info-section">
//                 <h4>📋 Category</h4>
//                 <p>{selectedMedicine.category}</p>
//               </div>

//               <div className="info-section">
//                 <h4>🎯 Purpose</h4>
//                 <p>{selectedMedicine.purpose}</p>
//               </div>

//               <div className="info-section">
//                 <h4>💊 Common Uses</h4>
//                 <p>{selectedMedicine.commonUses}</p>
//               </div>

//               <div className="info-section">
//                 <h4>📦 Available Forms</h4>
//                 <p>{selectedMedicine.dosageForms}</p>
//               </div>

//               <div className="info-section warning">
//                 <h4>⚠️ Side Effects</h4>
//                 <p>{selectedMedicine.sideEffects}</p>
//               </div>

//               <div className="info-section caution">
//                 <h4>🛡️ Precautions</h4>
//                 <p>{selectedMedicine.precautions}</p>
//               </div>
//             </div>
//           </div>
//         </div>
//       )}
//     </div>
//   );
// }

// export default MedicineSearch;