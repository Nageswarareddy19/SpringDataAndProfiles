package com.nt.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class CustomerExceptionMapper {

	@ExceptionHandler(value = CustomerNotFoundException.class)
	public ResponseEntity<ApiError> handleCustomerNotFoundException() {

		ApiError error = new ApiError();
		error.setErrorCode(400);
		error.setErrorDesc("Customer id is invalid");
		error.setDate(new Date());
		return new ResponseEntity<ApiError>(error, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(value =EmailsNotFoundException.class)
	public ResponseEntity<ApiError> handleEmailsNotFoundException(){
		ApiError error = new ApiError();
		error.setErrorCode(400);
		error.setErrorDesc("Customer emails are not found");
		error.setDate(new Date());
		return new ResponseEntity<ApiError>(error,HttpStatus.BAD_REQUEST);
		
	}

}
