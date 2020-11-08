package com.example.jpa.example1.web;

import com.example.jpa.example1.db1.User;
import com.example.jpa.example1.db1.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;
	/**
	 * @param user
	 * @return
	 */
	@PostMapping("/user")
	public User saveUser(@RequestBody User user) {
		return userRepository.save(user);
	}
	@GetMapping("/user/{id}")
	public User getUser(@PathVariable Long id) {
		return userRepository.getOne(id);
	}

}
