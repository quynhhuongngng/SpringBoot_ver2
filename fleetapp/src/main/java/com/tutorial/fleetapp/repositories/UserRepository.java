package com.tutorial.fleetapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import com.tutorial.fleetapp.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	//Cách 1
	User findByUsername(String username);

	//Cách 2
	@Query("SELECT u FROM User u WHERE u.username = :username")
	public User getUserByUsername(@Param("username") String username);
}
