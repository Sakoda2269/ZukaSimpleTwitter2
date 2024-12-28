package com.example.test.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.test.entities.Account;
import com.example.test.entities.Tweet;
import com.example.test.repositories.AccountRepository;
import com.example.test.repositories.TweetRepository;

@Service
public class TweetService {
	
	private final AccountRepository accountRepository;
	private final TweetRepository tweetRepository;
	
	public TweetService(AccountRepository ar, TweetRepository tr) {
		accountRepository = ar;
		tweetRepository = tr;
	}
	
	public void tweet(Account account, String contents) {
		Tweet tweet = new Tweet();
		tweet.setAccount(account);
		tweet.setContents(contents);
		//一意なIDを使用
		tweet.setTid(UUID.randomUUID().toString());
		tweetRepository.save(tweet);
	}
	
	public List<Tweet> getAccountTweets(String email) {
		Account account = accountRepository.findByEmail(email).orElseThrow();
		return account.getTweets();
	}
	
	public List<Tweet> getAllTweets() {
		return tweetRepository.findAll();
	}
	
}
