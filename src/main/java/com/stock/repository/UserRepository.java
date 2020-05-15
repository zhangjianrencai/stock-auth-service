package com.stock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stock.entity.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findOneWithAuthoritiesByUsername(String userName);

	Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);
}