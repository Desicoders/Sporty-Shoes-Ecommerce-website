package com.sporty.shoes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sporty.shoes.exceptions.ExceptionResponse;

@RestControllerAdvice
public class ExceptionController {

	@ExceptionHandler
	public ResponseEntity<?> handleException(Exception E)
	{	
		ExceptionResponse ER = new ExceptionResponse(E.getClass().getSimpleName(),E.getMessage() );
		return new ResponseEntity<ExceptionResponse> (ER,HttpStatus.NOT_FOUND);
	}
}
