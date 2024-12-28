package com.example.test.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="accounts")
@Data
public class Account {
	
	@Id
	private String email;
	
	private String name;
	
	private String password;
	
	private String role;

	@OneToMany(mappedBy="account", cascade = CascadeType.ALL, orphanRemoval=true)
	private List<Tweet> tweets;
	
}
