import api from './api';

export const medicationService = {
  // Add new medication
  addMedication: (medication, userId) => 
    api.post(`/medications?userId=${userId}`, medication),
  
  // Get user medications
  getUserMedications: (userId) => 
    api.get(`/medications/user/${userId}`),
  
  // Get active medications
  getActiveMedications: (userId) => 
    api.get(`/medications/user/${userId}/active`),
  
  // Update medication
  updateMedication: (medicationId, medication) => 
    api.put(`/medications/${medicationId}`, medication),
  
  // Delete medication
  deleteMedication: (medicationId) => 
    api.delete(`/medications/${medicationId}`),
  
  // Mark medication as taken
  markAsTaken: (medicationId, userId) => 
    api.post(`/medications/${medicationId}/taken?userId=${userId}`),
  
  // Add reminder schedule
  addReminder: (medicationId, schedule) => 
    api.post(`/medications/${medicationId}/reminders`, schedule),
  
  // Get medications needing refill
  getMedicationsNeedingRefill: (userId) => 
    api.get(`/medications/user/${userId}/refill-needed`),
  
  // Get adherence rate
  getAdherenceRate: (userId) => 
    api.get(`/medications/user/${userId}/adherence-rate`),


  
 // **Send email reminder**
  sendEmail: (medicationId) => 
    api.post(`/medications/${medicationId}/sendEmail`),
 
  // 

};