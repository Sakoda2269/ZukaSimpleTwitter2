package com.example.test.configs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/*
 * アカウントなどのセキュリティに関する設定をするクラス
 */
@Configuration
public class SecurityConfig {
	
	private final AccountDetailsService accountDetailsService;
	
	public SecurityConfig(AccountDetailsService ads) {
		accountDetailsService = ads;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(accountDetailsService);//データベースからアカウントを取得する際に使用するServiceクラスの登録
		authProvider.setPasswordEncoder(passwordEncoder());//パスワードエンコーダーの登録
		return authProvider;
	}
	
	//おまじない
	@Bean 
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConf) throws Exception {
		return authenticationConf.getAuthenticationManager();
	}
	
	
	//各urlのセキュリティ設定。
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
		//ログインページの設定
		.formLogin(login -> 
			login.loginPage("/login")//ログインページは/loginでアクセス
			.loginProcessingUrl("/login")//ログインするためには/loginにPOST
			.defaultSuccessUrl("/home")//ログイン成功時に/homeにリダイレクト
			.failureUrl("/login?error=true")//ログイン失敗時に/login?error=trueにリダイレクト
			.permitAll()//すべてのユーザーがアクセス可能
		)
		//urlに対してセキュリティ設定
		.authorizeHttpRequests(authz ->
			authz.requestMatchers("/", "/login", "/register", "/css/**", "/js/**", "/logouted", "/logout").permitAll()//このurl弐はすべてのユーザーがアクセス可能
			.anyRequest().authenticated()//ほかのurlにはログインしたユーザーしかアクセスできない
//			.requestMatchers("/aiueo").hasRole("ROLE_ADMIN") /aiueoにはROLE_ADMINを持つユーザーしかアクセスできない
//			.requestMatchers("/database").denyAll() /databaseには誰もアクセスできない
		)
		//ログアウトページの設定
		.logout(logout -> 
			logout.logoutUrl("/logout")//ログアウトするためには/logoutにPOST
			.logoutSuccessUrl("/logouted")//ログアウト成功時に/logoutedにリダイレクト
			.permitAll()//すべてのユーザーがログアウトできる
		)
		 .sessionManagement(session -> session
	                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // セッションを必要に応じて作成
	            );
		;
		return http.build();
	}
}
