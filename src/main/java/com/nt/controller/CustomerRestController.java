package com.nt.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nt.exceptions.CustomerNotFoundException;
import com.nt.exceptions.EmailsNotFoundException;
import com.nt.model.Customer;
import com.nt.service.CustomerService;

@RestController
public class CustomerRestController {

	Logger logger = LoggerFactory.getLogger(CustomerRestController.class);

	public CustomerRestController() {
		logger.debug("CustomerRestController-Instantiated");
		logger.info("CustomerRestController-Instantiated");
	}

	@Autowired
	private CustomerService custService;
	
	@PostMapping(value = "/add", consumes = { "application/json", "application/xml" })
	
	public ResponseEntity<String> addCustomer(@RequestBody Customer c) {
		logger.debug("addCustomer() method is called");
		boolean isSaved = custService.addCustomer(c);
		if (isSaved) {
			String res = "Customer details sucessfully added";
			logger.debug("addCustomer() method is ended");
			logger.info("addCustomer() method is sucessfully completed");
			return new ResponseEntity<String>(res, HttpStatus.CREATED);

		} else {
			String res = "Customer details is not added";
			logger.error("Internal server error");
			return new ResponseEntity<String>(res, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(value = "/get", produces = { "application/json", "application/xml" })
	public ResponseEntity<Customer> getCustomerBydId(@RequestParam Integer customerID) {
		logger.debug("getCustomerById() method is called");
		Customer cust = custService.getCustomerDetailsByID(customerID);
		if (cust != null) {
			logger.debug("getCustomerById() method is ended");
			logger.info("getCustomerById() method is sucessfully executed");
			return new ResponseEntity<Customer>(cust, HttpStatus.OK);
		} else {
			logger.error("CustomerNotFoundException is occurred");
			throw new CustomerNotFoundException("Customer id is not availble");
		}

	}

	@GetMapping(value = "/getEmail/{email}", produces = { "application/json", "application/xml" })
	public ResponseEntity<Customer> getCustomerDetailsByEmail(@PathVariable String email) {
			logger.debug("getCustomerDetailsByEmail() method is called");
		Customer c = custService.findByEmail(email);
		if (c != null) {
			logger.debug("getCustomerDetailsByEmail() method is ended");
			logger.info("getCustomerDetailsByEmail() method is sucessfully executed");
			return new ResponseEntity<Customer>(c, HttpStatus.OK);
		} else {
			logger.error("CustomerNotFoundException is occurred");
			throw new CustomerNotFoundException("Customer Email is not availabe");
		}

	}

	@GetMapping(value = "/getEmails", produces = { "application/json", "application/xml" })
	public ResponseEntity<List<String>> getAllMails() {
		logger.debug("getAllMails() method is called");
		List<String> list = custService.findAllEmails();

		if (!list.isEmpty()) {
			logger.debug("getAllMails() method is ended");
			logger.info("getAllMails() method is sucessfully executed");
			return new ResponseEntity<List<String>>(list, HttpStatus.OK);
		} else {
			logger.error("EmailsNotFoundException is raised");
			throw new EmailsNotFoundException("Emails are not found");
		}

	}

}
