CREATE DATABASE medication_reminder;
USE medication_reminder;

CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
     date_of_birth DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
CREATE TABLE medicine_catalog (
    medicine_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    medicine_name VARCHAR(100) NOT NULL,
    generic_name VARCHAR(100),
    category VARCHAR(50),
    purpose TEXT NOT NULL,
    common_uses TEXT NOT NULL,
    dosage_forms VARCHAR(100),
    side_effects TEXT,
    precautions TEXT,
    expiry_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_medications (
    medication_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    medicine_id BIGINT,
    custom_medicine_name VARCHAR(100),
    dosage VARCHAR(50) NOT NULL,
    frequency VARCHAR(50) NOT NULL,
    duration_days INT,
    start_date DATE NOT NULL,
    end_date DATE,
    current_stock INT,
    refill_reminder_at INT,
    notes TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (medicine_id) REFERENCES medicine_catalog(medicine_id) ON DELETE SET NULL
);

CREATE TABLE reminder_schedules (
    schedule_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    medication_id BIGINT NOT NULL,
    reminder_time TIME NOT NULL,
    reminder_days VARCHAR(50) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (medication_id) REFERENCES user_medications(medication_id) ON DELETE CASCADE
);

CREATE TABLE medication_history (
    history_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    medication_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    taken_at TIMESTAMP NOT NULL,
    status ENUM('TAKEN', 'MISSED', 'SKIPPED') NOT NULL,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (medication_id) REFERENCES user_medications(medication_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);


INSERT INTO medicine_catalog (medicine_name, generic_name, category, purpose, common_uses, dosage_forms, side_effects, precautions) VALUES
('Paracetamol', 'Acetaminophen', 'Analgesic/Antipyretic', 'Fever and pain relief', 'Used for treating fever, headache, body ache, toothache, and mild to moderate pain. Commonly used for cold and flu symptoms.', 'Tablet, Syrup, Suspension', 'Nausea, allergic reactions, liver damage (with overdose)', 'Do not exceed 4000mg per day. Avoid alcohol. Consult doctor if liver problems exist.'),

('Ibuprofen', 'Ibuprofen', 'NSAID', 'Pain, inflammation, and fever', 'Used for headaches, dental pain, menstrual cramps, muscle aches, arthritis, and fever reduction.', 'Tablet, Capsule, Syrup', 'Stomach upset, heartburn, dizziness, increased bleeding risk', 'Take with food. Avoid if you have stomach ulcers or kidney problems.');
desc medicine_catalog;
INSERT INTO users (username, email, password, full_name, phone) VALUES
('testuser', 'test@example.com', 'password123', 'Test User', '9876543210');

SELECT COUNT(*) AS total_medicines FROM medicine_catalog;

SHOW TABLES;
