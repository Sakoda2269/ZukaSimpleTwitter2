package com.example.test.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="tweets")
@Data
public class Tweet {
	
	@Id
	private String tid;
	
	private String contents;
	
	//外部キー
	@ManyToOne
	private Account account;
	
}
