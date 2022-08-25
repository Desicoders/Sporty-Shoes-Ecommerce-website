package com.sporty.shoes.exceptions;

public class ProductNotFoundException extends RuntimeException{
	public ProductNotFoundException(String msg) {
		super(msg);
	}

}
