package com.retailer.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class TotalPoints {

	private @Id String customerId;

	//For a different application to fill customer data
	private int totalPoints;

	public TotalPoints() {
	}

	public TotalPoints(String customerId, int totalPoints) {
		this.customerId = customerId;
		this.totalPoints = totalPoints;
	}

}
