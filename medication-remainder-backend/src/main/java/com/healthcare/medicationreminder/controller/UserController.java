package com.healthcare.medicationreminder.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.healthcare.medicationreminder.model.MedicineCatalog;
import com.healthcare.medicationreminder.model.User;
import com.healthcare.medicationreminder.service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	  @Autowired
	    private UserService userService;
	  
	  
//	  Register new user
//	   POST /api/users/register
	 @PostMapping("/register")
	 public ResponseEntity<?> registerUser(@RequestBody User user)
	 {
		 try
		 {
			 User registeredUser=userService.registerUser(user);
			 
			 Map<String, Object> response = new HashMap<>();
	            response.put("success", true);
	            response.put("message", "User registered successfully!");
	            response.put("user", registeredUser);
	            
	            return ResponseEntity.status(HttpStatus.CREATED).body(response);
			 
		 }catch (RuntimeException e) {
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", false);
	            response.put("message", e.getMessage());
	            
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        }
		  
	 }
	 
//	 Login user
//      POST /api/users/login
	 
	 @PostMapping("/login")  //on class
	    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> credentials) {
	        try {
	            String usernameOrEmail = credentials.get("usernameOrEmail");
	            String password = credentials.get("password");
	            
	            User user = userService.loginUser(usernameOrEmail, password);
	            
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", true);
	            response.put("message", "Login successful!");
	            response.put("user", user);
	            
	            return ResponseEntity.ok(response);
	        } catch (RuntimeException e) {
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", false);
	            response.put("message", e.getMessage());
	            
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	        }
	    }
	 
	 
//	 Get user by ID
//     GET /api/users/{id}
	 
	 @GetMapping("/{id}")
	    public ResponseEntity<?> getUserById(@PathVariable Long id) {
	        Optional<User> user = userService.getUserById(id);
	        
	        if (user.isPresent()) {
	            return ResponseEntity.ok(user.get());
	        } else {
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", false);
	            response.put("message", "User not found");
	            
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	    }
	 @GetMapping
	    public ResponseEntity<List<User>> getAllUsers() {
	        List<User> users = userService.getAllUsers();
	        return ResponseEntity.ok(users);
	    }
	 
//	 Update user
	 
	 @PutMapping("/{id}")
	    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
	        try {
	            user.setUserId(id);
	            User updatedUser = userService.updateUser(user);
	            
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", true);
	            response.put("message", "User updated successfully!");
	            response.put("user", updatedUser);
	            
	            return ResponseEntity.ok(response);
	        } catch (Exception e) {
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", false);
	            response.put("message", "Failed to update user");
	            
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        }
	    }
	 
	  @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
	        try {
	            userService.deleteUser(id);
	            
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", true);
	            response.put("message", "User deleted successfully!");
	            
	            return ResponseEntity.ok(response);
	        } catch (Exception e) {
	            Map<String, Object> response = new HashMap<>();
	            response.put("success", false);
	            response.put("message", "Failed to delete user");
	            
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	        }
	    }
	 
//	  Check if email exists
//	   GET /api/users/check-email?email=john@example.com
	  @GetMapping("/check-username")
	    public ResponseEntity<?> checkUsername(@RequestParam String username) {
	        Boolean exists = userService.usernameExists(username);
	        
	        Map<String, Object> response = new HashMap<>();
	        response.put("exists", exists);
	        
	        return ResponseEntity.ok(response);
	    }


    
//    GET /api/users/check-email?email=john@example.com
    @GetMapping("/check-email")
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        Boolean exists = userService.emailExists(email);
        
        Map<String, Object> response = new HashMap<>();
        response.put("exists", exists);
        
        return ResponseEntity.ok(response);
    }
}

