package com.healthcare.medicationreminder.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import com.healthcare.medicationreminder.model.User;
import org.springframework.stereotype.Service;

import com.healthcare.medicationreminder.repository.UserRepository;

@Service
public class UserService {

	@Autowired
    private UserRepository userRepository;	
	
//	Register a new user
	
	public User registerUser(User user)
{
		if(userRepository.existsByUsername(user.getUsername()))
		
			throw new RuntimeException("Username already exists!");
	
	
	if(userRepository.existsByEmail(user.getEmail()))
	{
		throw new RuntimeException("Email alredy exists");
	}
	return userRepository.save(user);
}
//	Login user
	
	public User loginUser(String usernameOrEmail, String password)
	{
		Optional<User> userOptional= userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
		if(userOptional.isEmpty())
		{
			 throw new RuntimeException("User not found!");
		}
		User user=userOptional.get();
		if(!user.getPassword().equals(password))
		{
			throw new RuntimeException("Invalid password!");
		}
		return user;
		
	}
	
//	Get user by id
	public Optional<User> getUserById(Long userId)
	{
		return userRepository.findById(userId);
	}
	 
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
  
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
    public Boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }
    
   
    public Boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
	
	
}
