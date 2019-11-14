package com.nt.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import com.nt.entity.CustomerEntity;
import com.nt.model.Customer;
import com.nt.repository.CustomerRepository;

/**
 * 
 * @author Nageswara reddy
 * 
 *         This class is unit test class for CustomerServiceImpl class
 *
 */

@SpringBootTest
public class CustomerServiceTest {

	/**
	 * to make dependent class object as mock object
	 */
	@Mock
	private CustomerRepository mockRepo;
	/**
	 * to inject dependent(mock) object into target object
	 */
	@InjectMocks
	private CustomerServiceImpl service;

	/**
	 * if customer object is inserted into database then return true
	 */

	@Test
	public void addCustomerTest() {

		Customer customer = new Customer();
		customer.setCustomerId(101);
		customer.setCustomerEmail("krishna@gmail.com");
		customer.setCustomerName("krishna reddy");
		CustomerEntity entity = new CustomerEntity();
		BeanUtils.copyProperties(customer, entity);
		when(mockRepo.save(Mockito.any(CustomerEntity.class))).thenReturn(entity);

		boolean isSaved = service.addCustomer(customer);
		assertTrue(isSaved);

	}

	/**
	 * If id is available then return customer object (positive scenario)
	 */

	@Test
	public void getCustomerDetailsByIdTestPostitive() {

		Customer customer = new Customer();
		customer.setCustomerId(101);
		CustomerEntity entity = new CustomerEntity();
		BeanUtils.copyProperties(customer, entity);
		when(mockRepo.findById(101)).thenReturn(Optional.of(entity));
		Customer actualcustomerDetails = service.getCustomerDetailsByID(customer.getCustomerId());
		assertNotNull(actualcustomerDetails);
	}

	/**
	 * If id is not available then return null value (negative scenario)
	 */

	@Test
	public void getCustomerDetailsByIdTestNegative() {

		Customer customer = new Customer();
		customer.setCustomerId(null);
		CustomerEntity entity = new CustomerEntity();
		BeanUtils.copyProperties(customer, entity);
		when(mockRepo.findById(101)).thenReturn(Optional.of(entity));
		Customer actualcustomerDetails = service.getCustomerDetailsByID(customer.getCustomerId());
		assertNull(actualcustomerDetails);
	
	}

	/**
	 * if email is available then return customer object(positive scenario)
	 */

	@Test
	public void findByEmailTestPostitive() {

		String email = "nreddy@123";
		CustomerEntity entity = new CustomerEntity();
		entity.setCustomerEmail(email);
		when(mockRepo.findByEmail(email)).thenReturn(entity);
		Customer actualEmail = service.findByEmail(email);
		assertNotNull(actualEmail);

	}

	/**
	 * if email is not available then return null value (negative scenario)
	 */

	@Test
	public void findByEmailTestNegative() {

		String email = "test@123";
		CustomerEntity entity = new CustomerEntity();
		entity.setCustomerEmail(email);
		when(mockRepo.findByEmail(email)).thenReturn(null);
		Customer actualEmail = service.findByEmail(email);
		assertNull(actualEmail);

	}

	/**
	 * get all email's available in database
	 */

	@Test
	public void findAllEmails() {
		List<String> emails = new ArrayList<>();
		emails.add("test@gmail.com");
		emails.add("test1@gmail.com");
		emails.add("test2@gmail.com");
		emails.add("test3@gmail.com");
		when(mockRepo.findAllMails()).thenReturn(emails);
		List<String> actualEmails = service.findAllEmails();
		assertEquals(actualEmails, emails);

	}

}
