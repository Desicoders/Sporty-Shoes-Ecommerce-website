package com.sporty.shoes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sporty.shoes.enteties.Admins;
import com.sporty.shoes.enteties.FieldValue;
import com.sporty.shoes.enteties.Users;
import com.sporty.shoes.exceptions.AdminLoginFirstException;
import com.sporty.shoes.security.AdminLogin;
import com.sporty.shoes.service.AdminServiceImpl;
import com.sporty.shoes.service.UserServiceImpl;

@RestController
@RequestMapping("/admins")
public class AdminController {
	@Autowired
	AdminServiceImpl adminServiceImpl;
	@Autowired
	UserServiceImpl userServiceImpl;

	@Autowired
	AdminLogin adminLogin;

	@PatchMapping("/update/password")
	public ResponseEntity<String> updatePassword(@RequestBody FieldValue fielvalue) {
		if (adminLogin.isAdminLoggedin() == false)
			throw new AdminLoginFirstException("Login As Admin First");
		adminServiceImpl.update(adminLogin.getAdminid(), "password", fielvalue.getFieldValue());
		return new ResponseEntity<String>("Admin password  updated sucessfully", HttpStatus.OK);
	}

	@GetMapping("/users/all")
	public ResponseEntity<List<Users>> getAllUsers() {
		if (adminLogin.isAdminLoggedin() == false)
			throw new AdminLoginFirstException("Login As Admin First");

		return new ResponseEntity<List<Users>>(userServiceImpl.getAll(), HttpStatus.OK);
	}

	@GetMapping("/users/search")
	public ResponseEntity<List<Users>> getAllUsersByName(@RequestParam String name) {
		if (adminLogin.isAdminLoggedin() == false)
			throw new AdminLoginFirstException("Login As Admin First");

		return new ResponseEntity<List<Users>>(userServiceImpl.getUsesrByName(name), HttpStatus.OK);
	}

	@PostMapping("/signin")
	public ResponseEntity<String> Signin(@RequestBody Admins admins)
	{
		Admins admins2= adminServiceImpl.get(admins.getId());
		if(admins2.getPassword().equals(admins2.getPassword()))
		  {
			adminLogin.setAdminid(admins.getId());
			adminLogin.setAdminLoggedin(true);
			return new ResponseEntity<String>("Admin logged in sucessfully",HttpStatus.OK);
		  }
		return new ResponseEntity<String>("in correct password",HttpStatus.BAD_REQUEST);
	}
	@GetMapping("/signout")
	public ResponseEntity<String> Signout()
	{
			adminLogin.setAdminLoggedin(false);
		return new ResponseEntity<String>("Admin signed Out sucsessfully",HttpStatus.OK);
	}

}
