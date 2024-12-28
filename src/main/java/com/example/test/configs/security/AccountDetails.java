package com.example.test.configs.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.test.entities.Account;


//アカウント認証用のクラス
public class AccountDetails implements UserDetails{

	private final Account account;
	
	public AccountDetails(Account account) {
		this.account = account;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO 自動生成されたメソッド・スタブ
		return Collections.singleton(new SimpleGrantedAuthority(account.getRole()));
	}

	@Override
	public String getPassword() {
		return account.getPassword();
	}

	//今回はemailを使用してアカウントを特定している
	@Override
	public String getUsername() {
		return account.getEmail();
	}
	
	public Account getAccount() {
		return account;
	}
	

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
