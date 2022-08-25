package com.sporty.shoes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sporty.shoes.security.AdminLogin;
import com.sporty.shoes.security.UserLogin;

@Configuration
public class AppConfig {

	@Bean
	AdminLogin adminLogin()
	{
		return new AdminLogin(false,0);
	}
	@Bean
	UserLogin userLogin()
	{
		return new UserLogin(false,0);
	}
}
