package com.nt.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nt.entity.CustomerEntity;



@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Serializable> {
	
	@Query(value  = "from CustomerEntity where customerEmail=:customerEmail")
	public CustomerEntity findByEmail(String customerEmail);
	
	@Query(value = "select customerEmail from CustomerEntity")
	public List<String> findAllMails();

}
