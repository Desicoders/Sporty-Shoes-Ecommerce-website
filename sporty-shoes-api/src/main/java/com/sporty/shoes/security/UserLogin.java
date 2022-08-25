package com.sporty.shoes.security;

import lombok.Data;

@Data
public class UserLogin {
	boolean userLoggedin;
	int userid;
	public UserLogin(boolean b,int userid){
		this.userLoggedin=b;
		this.userid=userid;
	}

}
