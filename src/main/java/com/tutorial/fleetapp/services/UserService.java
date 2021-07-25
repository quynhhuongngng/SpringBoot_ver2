package com.tutorial.fleetapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tutorial.fleetapp.models.User;
import com.tutorial.fleetapp.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private UserRepository userRepository;

	public void save(User user) {
		if (user.getPassword().matches("^\\$2[ayb]\\$.{56}$")) {
			userRepository.save(user);
		} else {
			user.setPassword(encoder.encode(user.getPassword()));
			userRepository.save(user);

		}
		// ^ - start of a string
		// \$ - a literal $ char (it should be escaped in a regex pattern to match a
		// literal $ char, else, it will denote the end of string)
		// 2 - a 2 char
		// [ayb] - a character class matching any single char out of the specified set
		// \$ - a $ char
		// .{56} - any 56 chars other than line break chars (if not POSIX compliant
		// regex engine is used, else, it will match any chars; to match any chars in
		// common NFA engines, replace . with [\s\S] or use a corresponding DOTALL flag)
		// $ - end of string.

	}

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	// get by id
	public Optional<User> findById(int id) {
		return userRepository.findById(id);
	}

	// get by username
	public User findByUsername(String username) {
		return userRepository.getUserByUsername(username);
	}

	public void delete(Integer id) {
		userRepository.deleteById(id);
	}

}
