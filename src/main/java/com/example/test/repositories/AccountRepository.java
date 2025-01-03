package com.example.test.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.test.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>{
	
	Optional<Account> findByEmail(String email);
	
}
