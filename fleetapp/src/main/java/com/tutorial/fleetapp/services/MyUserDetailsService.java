package com.tutorial.fleetapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tutorial.fleetapp.models.User;
import com.tutorial.fleetapp.models.UserPrincipal;
import com.tutorial.fleetapp.repositories.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getUserByUsername(username);
		if (user == null || !user.isEnabled()) {
			throw new UsernameNotFoundException("Không tìm thấy tài khoản hoặc tài khoản chưa được kích hoạt!");
		}

		return new UserPrincipal(user);
	}

}
