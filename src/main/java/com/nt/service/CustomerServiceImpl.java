package com.nt.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.entity.CustomerEntity;
import com.nt.exceptions.CustomerNotFoundException;
import com.nt.model.Customer;
import com.nt.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository custRepo;

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public boolean addCustomer(Customer c) {
		CustomerEntity entity = new CustomerEntity();
		BeanUtils.copyProperties(c, entity);

		entity = custRepo.save(entity);

		return entity.getCustomerId() > 0;

	}

	@Override
	public Customer getCustomerDetailsByID(Integer customerID) {
		Optional<CustomerEntity> optEntity = custRepo.findById(customerID);

		if (optEntity.isPresent()) {
			CustomerEntity customerEntity = optEntity.get();
			Customer model = new Customer();
			BeanUtils.copyProperties(customerEntity, model);
			return model;
		}

		return null;

	}

	@Override
	public Customer findByEmail(String email) {
		CustomerEntity entity = custRepo.findByEmail(email);
		
		if(entity!=null) {
		Customer model = new Customer();
		BeanUtils.copyProperties(entity, model);
		return model;
		}

		return null;
	}

	@Override
	public List<String> findAllEmails() {
		List<String> findAllMails = custRepo.findAllMails();
		return findAllMails;
	}
}
