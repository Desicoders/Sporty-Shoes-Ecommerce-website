package com.sporty.shoes.controller;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sporty.shoes.enteties.Admins;
import com.sporty.shoes.enteties.FieldValue;
import com.sporty.shoes.enteties.Orders;
import com.sporty.shoes.enteties.Products;
import com.sporty.shoes.enteties.Users;
import com.sporty.shoes.exceptions.UserLoginFirst;
import com.sporty.shoes.security.UserLogin;
import com.sporty.shoes.service.OrderServiceImpl;
import com.sporty.shoes.service.ProductServiceImpl;
import com.sporty.shoes.service.UserServiceImpl;

@RestController
@RequestMapping("/users")
public class UserController {

		
	@Autowired
	UserServiceImpl userServiceImpl;
	
	@Autowired
	ProductServiceImpl productServiceImpl;
	
	@Autowired
	OrderServiceImpl orderServiceImpl;
	
	@Autowired
	UserLogin userLogin;
	
	@GetMapping("/")
	ResponseEntity<Users> getUser()
	{ 
		if(userLogin.isUserLoggedin()==false)
			throw new UserLoginFirst("please Login First As User");
		return new ResponseEntity<Users>( userServiceImpl.get(userLogin.getUserid()),HttpStatus.OK);
	}
	
	@PostMapping("/product/{productid}/order")
		public ResponseEntity<EntityModel<Orders>> createOrder( @PathVariable int productid) throws NoSuchMethodException, SecurityException
		{

		if(userLogin.isUserLoggedin()==false)
			throw new UserLoginFirst("please Login First As User");
		Users user=userServiceImpl.get(userLogin.getUserid());
		
		Products product=productServiceImpl.get(productid);
		
		productServiceImpl.update(productid,"quantityInStock", product.getQuantityInStock()-1);
		Orders order=new Orders();
		order.setProduct(product);
		order.setUsers(user);
		order=orderServiceImpl.save(order);
		
		EntityModel<Orders> entityModel= EntityModel.of(order);
		Method method=this.getClass().getMethod("getAllOrders",null);
		method.setAccessible(true);
		Link linktoOrders =WebMvcLinkBuilder.linkTo(method).withSelfRel();;
		entityModel.add(linktoOrders);
		
		return new ResponseEntity<EntityModel<Orders>>(entityModel,HttpStatus.CREATED);
		
		}
	
	@GetMapping("/order/all")
	public ResponseEntity <List<Orders> >getAllOrders()
	{

		if(userLogin.isUserLoggedin()==false)
			throw new UserLoginFirst("please Login First As User");
		Users users=userServiceImpl.get(userLogin.getUserid());
		return new ResponseEntity<List<Orders>>(orderServiceImpl.getAllOrdersByUser(users),HttpStatus.OK);
		
		
	}
	@PatchMapping("/update/password")
	public ResponseEntity <String >updatePassword(@RequestBody FieldValue fielvalue)
	{

		if(userLogin.isUserLoggedin()==false)
			throw new UserLoginFirst("please Login First As User");
		
		Users users=userServiceImpl.update(userLogin.getUserid(), "password",fielvalue.getFieldValue());
		return new ResponseEntity<String>("User's password updated",HttpStatus.OK);
		
		
	}
	@PatchMapping("/update/name")
	public ResponseEntity <String >updateName(@RequestBody FieldValue fielvalue)
	{
		
		Users users=userServiceImpl.update(userLogin.getUserid(), "name",fielvalue.getFieldValue());
		return new ResponseEntity<String>("Username updated updated",HttpStatus.OK);
		
		
	}
	
	@PostMapping("/signup")
	public ResponseEntity<Users> signupUp(@RequestBody Users users)
	{
		if(userLogin.isUserLoggedin()==true)
			throw new UserLoginFirst("please logout first as a userss");
		
		System.out.println(users);
		users=userServiceImpl.save(users);
		return new ResponseEntity<Users>(users,HttpStatus.CREATED);
	}
	@GetMapping("/signout")
	public ResponseEntity<String> Signout()
	{
		if(userLogin.isUserLoggedin()==false)
			throw new UserLoginFirst("please Login First As User");
		
			userLogin.setUserLoggedin(false);
		return new ResponseEntity<String>("User signed Out sucsessfully",HttpStatus.OK);
	}

	@PostMapping("/signin")
	public ResponseEntity<String> Signin(@RequestBody Users user)
	{
		Users user2= userServiceImpl.get(user.getId());
		if(user2.getPassword().equals(user.getPassword()))
		  {
			userLogin.setUserid(user.getId());
			userLogin.setUserLoggedin(true);
			return new ResponseEntity<String>("User logged in sucessfully",HttpStatus.OK);
		  }
		return new ResponseEntity<String>("in correct password",HttpStatus.BAD_REQUEST);
	}

	
	
	
}
