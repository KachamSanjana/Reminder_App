import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import MedicineSearch from './MedicineSearch';
import AddMedication from './AddMedication';
import MedicationList from './MedicationList';
import '../styles/Dashboard.css';

function Dashboard() {
  const navigate = useNavigate();
  const [activeTab, setActiveTab] = useState('my-medications');
  const [user, setUser] = useState(null);

  useEffect(() => {
    // Get user from localStorage
    const userData = localStorage.getItem('user');
    if (!userData) {
      navigate('/login');
    } else {
      setUser(JSON.parse(userData));
    }
  }, [navigate]);

  const handleLogout = () => {
    localStorage.removeItem('user');
    localStorage.removeItem('userId');
    navigate('/login');
  };

  if (!user) return null;

  return (
    <div className="dashboard-container">
      {/* Animated Background */}
      <div className="dashboard-background">
        <div className="bubble bubble-1"></div>
        <div className="bubble bubble-2"></div>
        <div className="bubble bubble-3"></div>
        <div className="bubble bubble-4"></div>
        <div className="bubble bubble-5"></div>
      </div>

      {/* Header */}
      <header className="dashboard-header">
        <div className="header-content">
          <div className="logo-section">
            <div className="logo-icon">💊</div>
            <div className="logo-text">
              <h1>HealMindr</h1>
              <p className="tagline">Your Personal Health Companion</p>
            </div>
          </div>
          
          <div className="user-section">
            <div className="user-info">
              <div className="user-avatar">{user.fullName.charAt(0).toUpperCase()}</div>
              <div className="user-details">
                <span className="user-name">{user.fullName}</span>
                <span className="user-email">{user.email}</span>
              </div>
            </div>
            <button className="logout-btn" onClick={handleLogout}>
              <span>Logout</span>
              <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor">
                <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4M16 17l5-5-5-5M21 12H9"/>
              </svg>
            </button>
          </div>
        </div>
      </header>

      {/* Navigation Tabs */}
      <nav className="dashboard-nav">
        <div className="nav-container">
          <button
            className={`nav-tab ${activeTab === 'my-medications' ? 'active' : ''}`}
            onClick={() => setActiveTab('my-medications')}
          >
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <rect x="3" y="3" width="7" height="7"/>
              <rect x="14" y="3" width="7" height="7"/>
              <rect x="14" y="14" width="7" height="7"/>
              <rect x="3" y="14" width="7" height="7"/>
            </svg>
            <span>My Medications</span>
          </button>

          <button
            className={`nav-tab ${activeTab === 'add-medication' ? 'active' : ''}`}
            onClick={() => setActiveTab('add-medication')}
          >
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <circle cx="12" cy="12" r="10"/>
              <line x1="12" y1="8" x2="12" y2="16"/>
              <line x1="8" y1="12" x2="16" y2="12"/>
            </svg>
            <span>Add Medication</span>
          </button>

          <button
            className={`nav-tab ${activeTab === 'search-medicines' ? 'active' : ''}`}
            onClick={() => setActiveTab('search-medicines')}
          >
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor">
              <circle cx="11" cy="11" r="8"/>
              <path d="m21 21-4.35-4.35"/>
            </svg>
            <span>Search Medicines</span>
          </button>
        </div>
      </nav>

      {/* Main Content */}
      <main className="dashboard-content">
        <div className="content-wrapper">
          {activeTab === 'my-medications' && <MedicationList />}
          {activeTab === 'add-medication' && <AddMedication onSuccess={() => setActiveTab('my-medications')} />}
          {activeTab === 'search-medicines' && <MedicineSearch />}
        </div>
      </main>
    </div>
  );
}

export default Dashboard;