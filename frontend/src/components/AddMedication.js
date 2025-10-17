import React, { useState } from 'react';
import { medicationService } from '../services/medicationService';
import { medicineService } from '../services/medicineService';
import '../styles/AddMedication.css';

function AddMedication({ onSuccess }) {
  const userId = localStorage.getItem('userId');
  
  const [formData, setFormData] = useState({
    customMedicineName: '',
    dosage: '',
    frequency: 'Daily',
    durationDays: '',
    startDate: new Date().toISOString().split('T')[0],
    currentStock: '',
    refillReminderAt: '',
    notes: '',
  });

  const [reminderTimes, setReminderTimes] = useState(['08:00']);
  const [searchQuery, setSearchQuery] = useState('');
  const [suggestions, setSuggestions] = useState([]);
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleMedicineSearch = async (query) => {
    setSearchQuery(query);
    setFormData({ ...formData, customMedicineName: query });

    if (query.length > 1) {
      try {
        const response = await medicineService.searchByName(query);
        setSuggestions(response.data);
      } catch (error) {
        console.error('Error searching medicines:', error);
      }
    } else {
      setSuggestions([]);
    }
  };

  const handleSelectMedicine = (medicine) => {
    setFormData({
      ...formData,
      customMedicineName: medicine.medicineName
    });
    setSearchQuery(medicine.medicineName);
    setSuggestions([]);
  };

  const addReminderTime = () => {
    setReminderTimes([...reminderTimes, '08:00']);
  };

  const updateReminderTime = (index, value) => {
    const updated = [...reminderTimes];
    updated[index] = value;
    setReminderTimes(updated);
  };

  const removeReminderTime = (index) => {
    setReminderTimes(reminderTimes.filter((_, i) => i !== index));
  };

  // const handleSubmit = async (e) => {
  //   e.preventDefault();
  //   setLoading(true);

  //   try {
  //     const medication = {
  //       customMedicineName: formData.customMedicineName,
  //       dosage: formData.dosage,
  //       frequency: formData.frequency,
  //       durationDays: parseInt(formData.durationDays) || null,
  //       startDate: formData.startDate,
  //       currentStock: parseInt(formData.currentStock) || null,
  //       refillReminderAt: parseInt(formData.refillReminderAt) || null,
  //       notes: formData.notes,
  //     };

  //     const response = await medicationService.addMedication(medication, userId);

  //     if (response.data.success) {
  //       // Add reminders
  //       for (const time of reminderTimes) {
  //         await medicationService.addReminder(response.data.medication.medicationId, {
  //           reminderTime: time,
  //           reminderDays: 'DAILY',
  //         });
  //       }

  //       alert('✅ Medication added successfully!');
        
  //       // Reset form
  //       setFormData({
  //         customMedicineName: '',
  //         dosage: '',
  //         frequency: 'Daily',
  //         durationDays: '',
  //         startDate: new Date().toISOString().split('T')[0],
  //         currentStock: '',
  //         refillReminderAt: '',
  //         notes: '',
  //       });
  //       setSearchQuery('');
  //       setReminderTimes(['08:00']);
        
  //       if (onSuccess) onSuccess();
  //     }
  //   } catch (error) {
  //     console.error('Error adding medication:', error);
  //     alert('❌ Failed to add medication. Please try again.');
  //   } finally {
  //     setLoading(false);
  //   }
  // };
// const handleSubmit = async (e) => {
//   e.preventDefault();
//   setLoading(true);

//   try {
//     const medication = {
//       customMedicineName: formData.customMedicineName,
//       dosage: formData.dosage,
//       frequency: formData.frequency,
//       durationDays: parseInt(formData.durationDays) || null,
//       startDate: formData.startDate,
//       currentStock: parseInt(formData.currentStock) || null,
//       refillReminderAt: parseInt(formData.refillReminderAt) || null,
//       notes: formData.notes,
//     };

//     console.log('Sending medication data:', medication);
//     const response = await medicationService.addMedication(medication, userId);
//     console.log('Response received:', response.data);

//     // Check if medication was added (check multiple possible response formats)
//     const medicationId = response.data.medication?.medicationId || 
//                          response.data.medicationId ||
//                          response.data?.medication?.medication_id;

//     if (response.data && medicationId) {
//       // Try to add reminders (don't fail if reminders fail)
//       try {
//         for (const time of reminderTimes) {
//           await medicationService.addReminder(medicationId, {
//             reminderTime: time,
//             reminderDays: 'DAILY',
//           });
//         }


//          // Trigger email after adding medication & reminders
//       try {
//         await medicationService.sendEmail(medicationId);
//         console.log('✅ Email triggered successfully');
//       } catch (emailError) {
//         console.error('❌ Failed to send email:', emailError);
//       }


//         console.log('Reminders added successfully');
//       } catch (reminderError) {
//         console.error('Error adding reminders (non-critical):', reminderError);
//       }

//       alert('✅ Medication added successfully!');
      
//       // Reset form
//       setFormData({
//         customMedicineName: '',
//         dosage: '',
//         frequency: 'Daily',
//         durationDays: '',
//         startDate: new Date().toISOString().split('T')[0],
//         currentStock: '',
//         refillReminderAt: '',
//         notes: '',
//       });
//       setSearchQuery('');
//       setReminderTimes(['08:00']);
      
//       if (onSuccess) onSuccess();
//     } else {
//       // Medication might still be added, just response format is different
//       console.warn('Unexpected response format:', response.data);
//       alert('⚠️ Medication added! Please check "My Medications" tab.');
//       if (onSuccess) onSuccess();
//     }
//   } catch (error) {
//     console.error('Error adding medication:', error);
//     console.error('Error response:', error.response?.data);
    
//     // Check if it's just a response format issue
//     if (error.response?.status === 201 || error.response?.status === 200) {
//       alert('⚠️ Medication might be added. Please check "My Medications" tab.');
//       if (onSuccess) onSuccess();
//     } else {
//       alert('❌ Failed to add medication. Please try again.');
//     }
//   } finally {
//     setLoading(false);
//   }
// };

const handleSubmit = async (e) => {
  e.preventDefault();
  setLoading(true);

  try {
    const medication = {
      customMedicineName: formData.customMedicineName,
      dosage: formData.dosage,
      frequency: formData.frequency,
      durationDays: parseInt(formData.durationDays) || null,
      startDate: formData.startDate,
      currentStock: parseInt(formData.currentStock) || null,
      refillReminderAt: parseInt(formData.refillReminderAt) || null,
      notes: formData.notes,
    };

    const response = await medicationService.addMedication(medication, userId);

    const medicationId = response.data.medication?.medicationId || 
                         response.data.medicationId ||
                         response.data?.medication?.medication_id;

    if (response.data && medicationId) {
      // Add reminders
      for (const time of reminderTimes) {
        await medicationService.addReminder(medicationId, {
          reminderTime: time,
          reminderDays: 'DAILY',
        });
      }

      // Trigger email after adding medication & reminders
      try {
        await medicationService.sendEmail(medicationId);
        console.log('✅ Email triggered successfully');
      } catch (emailError) {
        console.error('❌ Failed to send email:', emailError);
      }

      alert('✅ Medication added successfully!');

      // Reset form
      setFormData({
        customMedicineName: '',
        dosage: '',
        frequency: 'Daily',
        durationDays: '',
        startDate: new Date().toISOString().split('T')[0],
        currentStock: '',
        refillReminderAt: '',
        notes: '',
      });
      setSearchQuery('');
      setReminderTimes(['08:00']);

      if (onSuccess) onSuccess();
    } else {
      console.warn('Unexpected response format:', response.data);
      alert('⚠️ Medication added! Please check "My Medications" tab.');
      if (onSuccess) onSuccess();
    }
  } catch (error) {
    console.error('Error adding medication:', error);
    alert('❌ Failed to add medication. Please try again.');
  } finally {
    setLoading(false);
  }
};

  return (
    <div className="add-medication-container">
      <div className="add-medication-header">
        <h2 className="section-title">
          <span className="title-icon">💊</span>
          Add New Medication
        </h2>
        <p className="section-subtitle">Fill in the details to track your medication</p>
      </div>

      <form onSubmit={handleSubmit} className="medication-form">
        {/* Medicine Name */}
        <div className="form-section">
          <h3 className="section-heading">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <rect x="3" y="11" width="18" height="11" rx="2" ry="2"/>
              <path d="M7 11V7a5 5 0 0 1 10 0v4"/>
            </svg>
            Medicine Information
          </h3>

          <div className="form-group">
            <label>Medicine Name *</label>
            <div className="autocomplete-wrapper">
              <input
                type="text"
                value={searchQuery}
                onChange={(e) => handleMedicineSearch(e.target.value)}
                placeholder="Search or enter medicine name..."
                required
                className="form-input"
              />
              {suggestions.length > 0 && (
                <div className="suggestions-dropdown">
                  {suggestions.map((medicine) => (
                    <div
                      key={medicine.medicineId}
                      className="suggestion-item"
                      onClick={() => handleSelectMedicine(medicine)}
                    >
                      <div className="suggestion-name">{medicine.medicineName}</div>
                      <div className="suggestion-purpose">{medicine.purpose}</div>
                    </div>
                  ))}
                </div>
              )}
            </div>
          </div>
        </div>

        {/* Dosage & Frequency */}
        <div className="form-section">
          <h3 className="section-heading">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <circle cx="12" cy="12" r="10"/>
              <polyline points="12 6 12 12 16 14"/>
            </svg>
            Dosage & Schedule
          </h3>

          <div className="form-row">
            <div className="form-group">
              <label>Dosage *</label>
              <input
                type="text"
                name="dosage"
                value={formData.dosage}
                onChange={handleChange}
                placeholder="e.g., 500mg, 1 tablet"
                required
                className="form-input"
              />
            </div>

            <div className="form-group">
              <label>Frequency *</label>
              <select
                name="frequency"
                value={formData.frequency}
                onChange={handleChange}
                className="form-select"
              >
                <option value="Daily">Daily</option>
                <option value="Twice Daily">Twice Daily</option>
                <option value="Thrice Daily">Thrice Daily</option>
                <option value="Weekly">Weekly</option>
                <option value="As Needed">As Needed</option>
              </select>
            </div>
          </div>
        </div>

        {/* Duration & Dates */}
        <div className="form-section">
          <h3 className="section-heading">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <rect x="3" y="4" width="18" height="18" rx="2" ry="2"/>
              <line x1="16" y1="2" x2="16" y2="6"/>
              <line x1="8" y1="2" x2="8" y2="6"/>
              <line x1="3" y1="10" x2="21" y2="10"/>
            </svg>
            Duration & Timeline
          </h3>

          <div className="form-row">
            <div className="form-group">
              <label>Start Date *</label>
              <input
                type="date"
                name="startDate"
                value={formData.startDate}
                onChange={handleChange}
                required
                className="form-input"
              />
            </div>

            <div className="form-group">
              <label>Duration (Days)</label>
              <input
                type="number"
                name="durationDays"
                value={formData.durationDays}
                onChange={handleChange}
                placeholder="e.g., 7, 30"
                className="form-input"
              />
            </div>
          </div>
        </div>

        {/* Stock Management */}
        <div className="form-section">
          <h3 className="section-heading">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z"/>
            </svg>
            Stock Management
          </h3>

          <div className="form-row">
            <div className="form-group">
              <label>Current Stock (Pills/Doses)</label>
              <input
                type="number"
                name="currentStock"
                value={formData.currentStock}
                onChange={handleChange}
                placeholder="Number of pills"
                className="form-input"
              />
            </div>

            <div className="form-group">
              <label>Refill Reminder At</label>
              <input
                type="number"
                name="refillReminderAt"
                value={formData.refillReminderAt}
                onChange={handleChange}
                placeholder="Pills remaining"
                className="form-input"
              />
            </div>
          </div>
        </div>

        {/* Reminder Times */}
        <div className="form-section">
          <h3 className="section-heading">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <circle cx="12" cy="12" r="10"/>
              <path d="M12 6v6l4 2"/>
            </svg>
            Reminder Times
          </h3>

          <div className="reminder-times-list">
            {reminderTimes.map((time, index) => (
              <div key={index} className="reminder-time-row">
                <input
                  type="time"
                  value={time}
                  onChange={(e) => updateReminderTime(index, e.target.value)}
                  className="time-input"
                />
                {reminderTimes.length > 1 && (
                  <button
                    type="button"
                    onClick={() => removeReminderTime(index)}
                    className="remove-time-btn"
                  >
                    <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                      <circle cx="12" cy="12" r="10"/>
                      <line x1="15" y1="9" x2="9" y2="15"/>
                      <line x1="9" y1="9" x2="15" y2="15"/>
                    </svg>
                  </button>
                )}
              </div>
            ))}
          </div>

          <button type="button" onClick={addReminderTime} className="add-time-btn">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <circle cx="12" cy="12" r="10"/>
              <line x1="12" y1="8" x2="12" y2="16"/>
              <line x1="8" y1="12" x2="16" y2="12"/>
            </svg>
            Add Another Reminder
          </button>
        </div>

        {/* Notes */}
        <div className="form-section">
          <h3 className="section-heading">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/>
              <polyline points="14 2 14 8 20 8"/>
              <line x1="16" y1="13" x2="8" y2="13"/>
              <line x1="16" y1="17" x2="8" y2="17"/>
              <polyline points="10 9 9 9 8 9"/>
            </svg>
            Additional Notes
          </h3>

          <div className="form-group">
            <textarea
              name="notes"
              value={formData.notes}
              onChange={handleChange}
              placeholder="Any special instructions or notes..."
              rows="4"
              className="form-textarea"
            />
          </div>
        </div>

        {/* Submit Button */}
        <button type="submit" className="submit-btn" disabled={loading}>
          {loading ? (
            <>
              <span className="loading-spinner"></span>
              Adding Medication...
            </>
          ) : (
            <>
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <polyline points="20 6 9 17 4 12"/>
              </svg>
              Add Medication
            </>
          )}
        </button>
      </form>
    </div>
  );
}

export default AddMedication;