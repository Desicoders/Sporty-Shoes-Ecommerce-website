package com.sporty.shoes.security;

import lombok.Data;

@Data
public class AdminLogin {

	boolean adminLoggedin;
	int Adminid;
	
	public AdminLogin(boolean b,int Adminid) {
        this.adminLoggedin=b;
        this.Adminid=Adminid;
	}

}
