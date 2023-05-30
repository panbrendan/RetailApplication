package com.retailer.model;

import lombok.Data;

@Data
public class CustomerSummary {

	private String customerId;
	private int monthPoints;
	private int totalPoints;

	private CustomerSummary() {
	}

	public CustomerSummary(String customerId, int monthPoints, int totalPoints) {
		this.customerId = customerId;
		this.monthPoints = monthPoints;
		this.totalPoints = totalPoints;
	}

}
