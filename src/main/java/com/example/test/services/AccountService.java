package com.example.test.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.test.entities.Account;
import com.example.test.repositories.AccountRepository;

@Service
public class AccountService {
	
	private final AccountRepository accountRepo;
	private final PasswordEncoder encoder;
	
	public AccountService(AccountRepository ar, PasswordEncoder pe) {
		accountRepo = ar;
		encoder = pe;
	}
	
	public void regiserAccount(String email, String name, String password) {
		Account account = new Account();
		account.setEmail(email);
		account.setName(name);
		account.setPassword(encoder.encode(password));
		account.setRole("ROLE_USER");
		accountRepo.save(account);
	}
	
}
