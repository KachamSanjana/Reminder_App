// // // src/components/MedicalApp.js
// // import React from 'react';
// // import { useNavigate } from 'react-router-dom';
// // import '../styles/styling.css';

// // function MedicalApp() {
// //   const navigate = useNavigate();

// //   return (
// //     <div className="medical-home">
// //       {/* Header */}
// //       <header className="header">
// //         <div className="logo">
// //           <span className="logo-icon">❤️</span>
// //           <span className="logo-text">MediFlow</span>
// //         </div>
// //         <button className="login-button-top" onClick={() => navigate('/login')}>
// //           Login
// //         </button>
// //       </header>

// //       {/* Hero Section */}
// //       <section className="hero-section">
// //         <div className="hero-content">
// //           <h1 className="quote">
// //             Your Health,<br /><span className="redefined">Redefined</span>
// //           </h1>
// //           <p className="subtext">
// //             Experience healthcare like never before. Real-time monitoring, AI-powered insights, and seamless care management in one intuitive platform.
// //           </p>
// //           <button className="get-started-button" onClick={() => navigate('/login')}>
// //             Get Started →
// //           </button>
// //         </div>
// //       </section>

      
// //     </div>
// //   );
// // }

// // export default MedicalApp;

// // src/components/MedicalApp.js
// import React from 'react';
// import { useNavigate } from 'react-router-dom';
// import '../styles/styling.css';

// function MedicalApp() {
//   const navigate = useNavigate();

//   return (
//     <div className="medical-home">
//       {/* Header */}
//       <header className="header">
//         <div className="logo">
//           <img src="/vitaflow-logo.png" alt="MediFlow Logo" className="logo-icon" />
//           <span className="logo-text">HealMindr</span>
//         </div>
//         <button className="login-button-top" onClick={() => navigate('/login')}>
//           Login
//         </button>
       
//       </header>




//       {/* Hero Section */}
//       <section className="hero-section">
//         <div className="hero-content">
//           <h1 className="quote">
//             Your Health, Redefined<br />
//             {/* <span className="redefined">Redefined</span> */}
//           </h1>
//           <p className="subtext">
//            Transform the way you care. From reminders to real-time insights your health, guided every step of the way.
//           </p>
//           <button className="get-started-button" onClick={() => navigate('/login')}>
//             Get Started →
//           </button>


//           <button className="rules-below-button" onClick={() => navigate('/rules')}>
//   Hear Me Out Once
// </button>
//         </div>
//       </section>
//     </div>
//   );
// }

// export default MedicalApp;

import React from "react";
import { useNavigate } from "react-router-dom";
import "../styles/styling.css";

function MedicalApp() {
  const navigate = useNavigate();

  return (
    <div className="medical-home">
      {/* Header */}
      <header className="header">
        <div className="logo">
          {/* <img src="/vitaflow-logo.png" alt="HealMindr Logo" className="logo-icon" /> */}
          <span className="logo-text"> 💊 HealMindr</span>
        </div>

        <div className="header-buttons">
          <button className="login-button-top" onClick={() => navigate("/login")}>
            Login
          </button>
          <button className="rules-button" onClick={() => navigate("/rules")}>
            Hear Me Out Once
          </button>
        </div>
      </header>

      {/* Hero Section */}
      <section className="hero-section">
        <div className="hero-content">
          <h1 className="quote">
            Your Health, <span className="redefined">Redefined</span>
          </h1>
          <p className="subtext">
            Transform the way you care. From reminders to real-time insights your health, guided every step of the way.
          </p>

          <button className="get-started-button" onClick={() => navigate("/login")}>
            Get Started →
          </button>
        </div>
      </section>
    </div>
  );
}

export default MedicalApp;
