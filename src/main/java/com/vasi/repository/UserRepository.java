package com.vasi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vasi.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);
}
