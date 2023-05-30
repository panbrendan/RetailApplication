package com.retailer.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
public class Customer {

	private @Id String customerId;

	//For a different application to fill customer data
	private String firstName;

	private String lastName;

	private String email;

	public Customer() {
	}

	public Customer(String customerId) {
		this.customerId = customerId;
	}

}
