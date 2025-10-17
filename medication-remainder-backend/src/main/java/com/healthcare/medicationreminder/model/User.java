package com.healthcare.medicationreminder.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="users")

public class User {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
 private Long userId;
	 @Column(unique = true, nullable = false, length = 50)
	private String username;
	 @Column(unique = true, nullable = false, length = 100)
	private String email;
	 
	 @Column(nullable = false, length = 255)
	 private String password;
	 @Column(name = "full_name", nullable = false, length = 100)
	    private String fullName;
	 @Column(length=12)
	 private String Phone;
	 @Column(name="date_of_birth", nullable=false)
	 private LocalDate dateofbirth;
	 @Column(name="created_at",nullable=false,updatable=false)
	 private LocalDateTime createdAt;
	 @Column(name = "updated_at")
	    private LocalDateTime updatedAt;
	 
	 @PrePersist
	 protected void onCreate()
	 { 
		  createdAt = LocalDateTime.now();
	      updatedAt = LocalDateTime.now();
	    }
	    
	    @PreUpdate
	    protected void onUpdate() {
	        updatedAt = LocalDateTime.now();
	    }
	}

	


