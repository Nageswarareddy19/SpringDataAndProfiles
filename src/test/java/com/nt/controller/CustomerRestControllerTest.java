package com.nt.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.HttpMediaTypeException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nt.model.Customer;
import com.nt.service.CustomerService;

@WebMvcTest
public class CustomerRestControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private CustomerService service;

	@Test
	public void addCustomerTestPositive() throws Exception {
		Customer c = new Customer();
		c.setCustomerId(101);
		c.setCustomerName("Nreddy");
		c.setCustomerEmail("nreddy@gmail.com");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(c);
		when(service.addCustomer(Mockito.any(Customer.class))).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(("/add")).content(json)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		int status = response.getStatus();
		assertEquals(HttpStatus.CREATED.value(), status);

	}

	@Test
	public void addCustomerTestNegative() throws Exception {
		Customer c = new Customer();
		c.setCustomerId(101);
		c.setCustomerName("Nreddy");
		c.setCustomerEmail("nreddy@gmail.com");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(c);
		when(service.addCustomer(Mockito.any(Customer.class))).thenReturn(false);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(("/add")).content(json)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);

	}

	@Test
	public void findByCustomerEmailTestPositive() throws Exception {

		Customer c = new Customer();
		c.setCustomerId(101);
		c.setCustomerName("jhon");
		c.setCustomerEmail("test123@gmail.com");
		when(service.findByEmail("test123@gmail.com")).thenReturn(c);
		RequestBuilder builder = MockMvcRequestBuilders.get("/getEmail/test123@gmail.com");
		MvcResult result = mockMvc.perform(builder).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);

	}

	@Test
	public void findByCustomerEmailTestNegative() throws Exception {

		when(service.findByEmail("test123@gmail.com")).thenReturn(null);
		RequestBuilder builder = MockMvcRequestBuilders.get("/getEmail/test123@gmail.com");
		MvcResult result = mockMvc.perform(builder).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.BAD_REQUEST.value(), status);

	}

	@Test
	public void getCustomerDetailsByIdTestPositive() throws Exception {

		Customer c = new Customer();
		c.setCustomerId(102);
		c.setCustomerName("naveen");
		c.setCustomerEmail("naveen@gmail.com");
		when(service.getCustomerDetailsByID(102)).thenReturn(c);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/get/102");
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);

	}

	@Test
	public void getCustomerDetailsByIdTestNegative() throws Exception {

		when(service.getCustomerDetailsByID(103)).thenReturn(null);
		RequestBuilder builder = MockMvcRequestBuilders.get("/get/104");
		MvcResult result = mockMvc.perform(builder).andReturn();
		int status = result.getResponse().getStatus();
		assertEquals(HttpStatus.BAD_REQUEST.value(), status);

	}

	@Test
	public void getAllEmailsPositiveTest() throws Exception {
		List<String> emails = new ArrayList<>();
		emails.add("test1@gmail.com");
		emails.add("test2@gmail.com");
		emails.add("test3@gmail.com");
		when(service.findAllEmails()).thenReturn(emails);
		RequestBuilder builder = MockMvcRequestBuilders.get("/getEmails");
		MvcResult result = mockMvc.perform(builder).andReturn();
		int status = result.getResponse().getStatus();

		assertEquals(HttpStatus.OK.value(), status);

	}

	@Test
	public void getAllEmailsNegativeTest() throws Exception {
		List<String> emails = new ArrayList<>();
		when(service.findAllEmails()).thenReturn(emails);
		RequestBuilder builder = MockMvcRequestBuilders.get("/getEmails");
		MvcResult result = mockMvc.perform(builder).andReturn();
		int status = result.getResponse().getStatus();

		assertEquals(HttpStatus.BAD_REQUEST.value(), status);

	}

}
