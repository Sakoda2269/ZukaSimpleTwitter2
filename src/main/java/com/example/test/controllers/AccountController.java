package com.example.test.controllers;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.test.configs.security.AccountDetails;
import com.example.test.entities.Account;
import com.example.test.entities.Tweet;
import com.example.test.services.AccountService;
import com.example.test.services.TweetService;

@Controller
@RequestMapping("")
public class AccountController {
	
	private final AccountService accountService;
	private final TweetService tweetService;
	
	public AccountController(AccountService as, TweetService ts) {
		accountService = as;
		tweetService = ts;
	}
	
	@GetMapping("/")
	public String index(Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//ログイン中なら
		if(!authentication.getPrincipal().equals("anonymousUser")) {
			//ログイン中のアカウントを取得
			AccountDetails accountDetail = (AccountDetails)authentication.getPrincipal();
			Account account = accountDetail.getAccount();
			model.addAttribute("name", account.getName());
		}
		
		List<Tweet> tweets = tweetService.getAllTweets();
		model.addAttribute("tweets", tweets);
		return "index";
	}
	
	@GetMapping("/home")
	public String home(Model model) {
		//ログイン中のアカウントを取得
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AccountDetails accountDetail = (AccountDetails)authentication.getPrincipal();
		Account account = accountDetail.getAccount();
		model.addAttribute("name", account.getName());
		List<Tweet> accountTweets = tweetService.getAccountTweets(account.getEmail());
		model.addAttribute("tweets", accountTweets);
		return "home";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/register")
	public String regiserPage() {
		return "register";
	}
	
	@PostMapping("/register")
	public String register(@RequestParam("email") String email, @RequestParam("name") String name, @RequestParam("password") String password) {
		accountService.regiserAccount(email, name, password);
		return "redirect:/login";
	}
	
	@GetMapping("/tweet")
	public String tweetPage() {
		return "tweet";
	}
	
	@PostMapping("/tweet")
	public String tweet(@RequestParam("contents") String contents) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AccountDetails account = (AccountDetails)authentication.getPrincipal();
		tweetService.tweet(account.getAccount(), contents);
		return "redirect:/home";
	}
	
	@GetMapping("/logouted")
	public String logouted() {
		return "logouted";
	}
}
