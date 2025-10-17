import React from "react";
import { useNavigate } from "react-router-dom";
import "../styles/styling.css";

function MedicationRules() {
  const navigate = useNavigate();

  return (
    <div className="rules-page">
      <header className="header">
        <div className="logo">
          <span className="logo-icon">💊 </span>
          <span className="logo-text">  Medication Rules</span>
        </div>
        <button className="login-button-top" onClick={() => navigate("/")}>
          ⬅ Back
        </button>
      </header>

      <div className="rules-content">
        <h1>Before You Begin — Read Once Carefully</h1>
        <p className="intro-text">
          Follow these rules to stay safe and get the most benefit from your medications.
        </p>

        <ul className="rules-list">
          <li><strong>1️⃣ Right Medicine:</strong> Always double-check the medicine name before taking it.</li>
          <li><strong>2️⃣ Right Dose:</strong> Take the exact dose prescribed by your doctor.</li>
          <li><strong>3️⃣ Right Time:</strong> Follow your schedule strictly — HealMindr will remind you.</li>
          <li><strong>4️⃣ Right Route:</strong> Use your medicine in the proper way (oral, topical, injection, etc.).</li>
          <li><strong>5️⃣ Right Patient:</strong> Only take medicine that is prescribed for you.</li>
          <li><strong>6️⃣ Right Documentation:</strong> Track when you’ve taken or missed a dose.</li>
          <li><strong>⚠️ Disclaimer:</strong> This platform provides reminders only. Always follow your doctor’s medical advice.</li>
        </ul>
      </div>
    </div>
  );
}

export default MedicationRules;
