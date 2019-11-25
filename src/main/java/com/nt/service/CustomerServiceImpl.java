package com.nt.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nt.entity.CustomerEntity;
import com.nt.model.Customer;
import com.nt.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	public CustomerServiceImpl() {
		logger.debug("CustomerServiceImpl:instantiated");
		logger.info("CustomerServiceImpl:instantiated");
	}

	@Autowired
	private CustomerRepository custRepo;

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public boolean addCustomer(Customer c) {
		logger.debug("addCustomer() method is started");
		CustomerEntity entity = new CustomerEntity();
		BeanUtils.copyProperties(c, entity);

		entity = custRepo.save(entity);
		logger.debug("addCustomer() method is ended");
		logger.info("addCustomer() method is sucessfully executed");

		return entity.getCustomerId() > 0;

	}

	@Override
	public Customer getCustomerDetailsByID(Integer customerID) {
		logger.debug("getCustomerDetailsByID() method is started");
		Optional<CustomerEntity> optEntity = custRepo.findById(customerID);

		if (optEntity.isPresent()) {
			CustomerEntity customerEntity = optEntity.get();
			Customer model = new Customer();
			BeanUtils.copyProperties(customerEntity, model);
			logger.debug("getCustomerDetailsByID() method is ended");
			return model;

		}
		logger.info("getCustomerDetailsByID() method is sucessfully executed ");
		return null;

	}

	@Override
	public Customer findByEmail(String email) {
		logger.debug("findByEmail() method is started");
		CustomerEntity entity = custRepo.findByEmail(email);

		if (entity != null) {
			Customer model = new Customer();
			BeanUtils.copyProperties(entity, model);
			logger.debug("findByEmail() method is ended");
			return model;
		}
		logger.info("findByEmail() method is sucessfully executed");
		return null;
	}

	@Override
	public List<String> findAllEmails() {
		logger.debug("findAllEmails() method is started");
		List<String> findAllMails = custRepo.findAllMails();
		logger.debug("findAllEmails() method is ended");
		logger.info("findAllEmails() method is executed");
		return findAllMails;
	}
}
