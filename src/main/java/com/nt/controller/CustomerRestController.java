package com.nt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nt.exceptions.CustomerNotFoundException;
import com.nt.exceptions.EmailsNotFoundException;
import com.nt.model.Customer;
import com.nt.service.CustomerService;

@RestController
public class CustomerRestController {

	@Autowired
	private CustomerService custService;

	@PostMapping(value = "/add", consumes = { "application/json", "application/xml" })
	public ResponseEntity<String> addCustomer(@RequestBody Customer c) {
		boolean isSaved = custService.addCustomer(c);
		if (isSaved) {
			String res = "Customer details sucessfully added";
			return new ResponseEntity<String>(res, HttpStatus.CREATED);
		} else {
			String res = "Customer details is not added";
			return new ResponseEntity<String>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(value = "/get/{customerID}", produces = { "application/json", "application/xml" })
	public ResponseEntity<Customer> getCustomerBydId(@PathVariable Integer customerID) {
		Customer cust = custService.getCustomerDetailsByID(customerID);
		if (cust != null) {
			return new ResponseEntity<Customer>(cust, HttpStatus.OK);
		} else {
			throw new CustomerNotFoundException("Customer id is not availble");
		}

	}

	@GetMapping(value = "/getEmail/{email}", produces = { "application/json", "application/xml" })
	public ResponseEntity<Customer> getCustomerDetailsByEmail(@PathVariable String email) {

		Customer c = custService.findByEmail(email);
		if (c != null) {
			return new ResponseEntity<Customer>(c, HttpStatus.OK);
		} else {
			throw new CustomerNotFoundException("Customer Email is not availabe");
		}

	}

	@GetMapping(value = "/getEmails", produces = { "application/json", "application/xml" })
	public ResponseEntity<List<String>> getAllMails() {

		List<String> list = custService.findAllEmails();

		if (!list.isEmpty()) {
			return new ResponseEntity<List<String>>(list, HttpStatus.OK);
		} else {
			throw new EmailsNotFoundException("Emails are not found");
		}

	}

}
