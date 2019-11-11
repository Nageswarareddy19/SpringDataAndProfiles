package com.nt.service;

import java.util.List;

import com.nt.model.Customer;

public interface CustomerService {
	
	public boolean addCustomer(Customer c);
	public Customer getCustomerDetailsByID(Integer customerID);
	public Customer findByEmail(String email);
	
	public List<String> findAllEmails();

}
