import api from './api';

export const medicineService = {
  // Get all medicines
  getAllMedicines: () => api.get('/medicines'),
  
  // Get medicine by ID
  getMedicineById: (id) => api.get(`/medicines/${id}`),
  
  // Search medicine by name
  searchByName: (name) => api.get(`/medicines/search?name=${name}`),
  
    searchMedicines: (query, type = 'name') => api.get(`/medications/search?query=${encodeURIComponent(query)}&type=${type}`),
  // // Search medicine by symptom (UNIQUE FEATURE!)
  // searchBySymptom: (symptom) => api.get(`/medicines/search-symptom?symptom=${symptom}`),
  
  // // Get medicines by category
  // getByCategory: (category) => api.get(`/medicines/category?category=${category}`),
};
