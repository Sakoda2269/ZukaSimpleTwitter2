package com.example.test.configs.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.test.entities.Account;
import com.example.test.repositories.AccountRepository;


//データベースからアカウントを取得し、認証する際に必要
@Service
public class AccountDetailsService implements UserDetailsService{

	//アカウントのリポジトリ
	private final AccountRepository accountRepository;
	
	public AccountDetailsService(AccountRepository accountRepo) {
		accountRepository = accountRepo;
	}
	
	//アカウントを特定する 今回はemailを使って特定
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Account account = accountRepository.findByEmail(email).orElseThrow(
				() -> new UsernameNotFoundException("User not found")
				);
		return new AccountDetails(account);
	}

}
