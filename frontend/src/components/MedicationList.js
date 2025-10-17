import React, { useState, useEffect } from 'react';
import { medicationService } from '../services/medicationService';
import '../styles/MedicationList.css';

function MedicationList() {
  const userId = localStorage.getItem('userId');
  const [medications, setMedications] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadMedications();
  }, []);

  const loadMedications = async () => {
    try {
      const response = await medicationService.getActiveMedications(userId);
      setMedications(response.data);
    } catch (error) {
      console.error('Error loading medications:', error);
    } finally {
      setLoading(false);
    }
  };

  // const handleMarkAsTaken = async (medicationId) => {
  //   try {
  //     await medicationService.markAsTaken(medicationId, userId);
  //     alert('✅ Medication marked as taken!');
  //     loadMedications();
  //   } catch (error) {
  //     console.error('Error marking medication as taken:', error);
  //     alert('❌ Failed to mark as taken');
  //   }
  // };

  const handleMarkAsTaken = async (medicationId) => {
  try {
    const response = await medicationService.markAsTaken(medicationId, userId);
    console.log('Mark as taken response:', response.data);
    
    // Success if we got a response (status 200/201)
    if (response && response.status >= 200 && response.status < 300) {
      alert('✅ Medication marked as taken!');
      loadMedications();
    } else {
      throw new Error('Unexpected response');
    }
  } catch (error) {
    console.error('Error marking medication as taken:', error);
    console.error('Error details:', error.response?.data);
    
    // Check if it actually worked despite error
    if (error.response?.status === 200 || error.response?.status === 201) {
      alert('✅ Medication marked as taken!');
      loadMedications();
    } else {
      alert('❌ Failed to mark as taken. Please try again.');
    }
  }
};

  const handleDelete = async (medicationId) => {
    if (window.confirm('Are you sure you want to delete this medication?')) {
      try {
        await medicationService.deleteMedication(medicationId);
        alert('✅ Medication deleted successfully!');
        loadMedications();
      } catch (error) {
        console.error('Error deleting medication:', error);
        alert('❌ Failed to delete medication');
      }
    }
  };

  if (loading) {
    return (
      <div className="loading-container">
        <div className="loading-spinner-large"></div>
        <p>Loading your medications...</p>
      </div>
    );
  }

  return (
    <div className="medication-list-container">
      <div className="list-header">
        <h2 className="list-title">
          <span className="title-icon">💊</span>
          My Active Medications
        </h2>
        <div className="medication-count">
          <span className="count-number">{medications.length}</span>
          <span className="count-label">Active</span>
        </div>
      </div>

      {medications.length > 0 ? (
        <div className="medications-grid">
          {medications.map((med) => (
            <div key={med.medicationId} className="medication-card">
              <div className="card-header">
                <div className="medicine-icon-wrapper">
                  <div className="medicine-icon">💊</div>
                </div>
                <span className="status-badge active">Active</span>
              </div>

              <div className="card-body">
                <h3 className="medicine-name">{med.customMedicineName}</h3>
                
                <div className="medicine-details">
                  <div className="detail-item">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                      <circle cx="12" cy="12" r="10"/>
                      <polyline points="12 6 12 12 16 14"/>
                    </svg>
                    <span><strong>Dosage:</strong> {med.dosage}</span>
                  </div>

                  <div className="detail-item">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                      <path d="M22 12h-4l-3 9L9 3l-3 9H2"/>
                    </svg>
                    <span><strong>Frequency:</strong> {med.frequency}</span>
                  </div>

                  <div className="detail-item">
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                      <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
                      <line x1="16" y1="2" x2="16" y2="6"/>
                      <line x1="8" y1="2" x2="8" y2="6"/>
                      <line x1="3" y1="10" x2="21" y2="10"/>
                    </svg>
                    <span><strong>Started:</strong> {new Date(med.startDate).toLocaleDateString()}</span>
                  </div>

                  {med.currentStock && (
                    <div className={`detail-item ${med.currentStock < 10 ? 'low-stock' : ''}`}>
                      <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                        <path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z"/>
                      </svg>
                      <span><strong>Stock:</strong> {med.currentStock} doses</span>
                      {med.currentStock < 10 && <span className="low-stock-badge">⚠️ Low</span>}
                    </div>
                  )}

                  {med.notes && (
                    <div className="notes-section">
                      <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                        <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/>
                        <polyline points="14 2 14 8 20 8"/>
                      </svg>
                      <span>{med.notes}</span>
                    </div>
                  )}
                </div>
              </div>

              <div className="card-footer">
                <button
                  className="action-btn btn-taken"
                  onClick={() => handleMarkAsTaken(med.medicationId)}
                >
                  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                    <polyline points="20 6 9 17 4 12"/>
                  </svg>
                  Mark as Taken
                </button>
                
                <button
                  className="action-btn btn-delete"
                  onClick={() => handleDelete(med.medicationId)}
                >
                  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                    <polyline points="3 6 5 6 21 6"/>
                    <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"/>
                  </svg>
                </button>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <div className="empty-state">
          <div className="empty-icon">📭</div>
          <h3>No Active Medications</h3>
          <p>You haven't added any medications yet. Click "Add Medication" to get started!</p>
        </div>
      )}
    </div>
  );
}

export default MedicationList;