package com.sporty.shoes.controller;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sporty.shoes.enteties.FieldValue;
import com.sporty.shoes.enteties.Products;
import com.sporty.shoes.exceptions.AdminLoginFirstException;
import com.sporty.shoes.security.AdminLogin;
import com.sporty.shoes.service.ProductServiceImpl;

@RestController
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	AdminLogin adminLogin;
	
	@Autowired
	ProductServiceImpl productServiceImpl;

	@GetMapping("/all")
	public ResponseEntity<List<Products>> getAllProducts() {
		return new ResponseEntity<List<Products>>(productServiceImpl.getAll(), HttpStatus.OK);
	}

	@GetMapping("/allGroupedByCategories")
	public ResponseEntity<List<Products>> getAllProductsgroupedByCategories() {
		return new ResponseEntity<List<Products>>(productServiceImpl.getAllBygroupedByCategory(), HttpStatus.OK);
	}

	@GetMapping("/searchByName")
	public ResponseEntity<List<Products>> getAllProductsByName(@RequestParam("name") String name) {
		return new ResponseEntity<List<Products>>(productServiceImpl.getAllByName(name), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Products> getProductById(@PathVariable int id) {
		return new ResponseEntity<Products>(productServiceImpl.get(id), HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<EntityModel<Products>> addProduct(@RequestBody Products product) throws NoSuchMethodException, SecurityException
	{
		if (adminLogin.isAdminLoggedin() == false)
			throw new AdminLoginFirstException("Login As Admin First");
		productServiceImpl.save(product);
		EntityModel<Products> entityModel= EntityModel.of(product);
		Method method=this.getClass().getMethod("getAllProducts");
		method.setAccessible(true);
		Link linktoOrders =WebMvcLinkBuilder.linkTo(method).withSelfRel();;
		entityModel.add(linktoOrders);
		return new ResponseEntity<EntityModel<Products>>( entityModel,HttpStatus.CREATED);
	}

	@PatchMapping("/{productid}/update/{fieldname}")
	public ResponseEntity<Products> updateProductname(@PathVariable int productid,@RequestBody FieldValue fieldValue,@PathVariable String fieldname)
	{
		if (adminLogin.isAdminLoggedin() == false)
			throw new AdminLoginFirstException("Login As Admin First");
		
		Products products= productServiceImpl.update(productid,fieldname,fieldValue.getFieldValue());
		return new ResponseEntity<Products>(products,HttpStatus.OK);
	}
	
	@DeleteMapping("/{productid}/delete")
	public ResponseEntity<String> deleteProductById(@PathVariable  int productid)
	{
		if (adminLogin.isAdminLoggedin() == false)
			throw new AdminLoginFirstException("Login As Admin First");
		productServiceImpl.delete(productid);
		return new ResponseEntity<String>("Product deleted sucessfully",HttpStatus.OK);
	}
	

}
