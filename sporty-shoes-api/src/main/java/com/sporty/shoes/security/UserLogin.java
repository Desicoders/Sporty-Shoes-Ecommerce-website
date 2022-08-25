package com.sporty.shoes.security;

import lombok.Data;

@Data
public class UserLogin {
	boolean userLoggedin;
	public UserLogin(boolean b){
		this.userLoggedin=b;
	}

}
