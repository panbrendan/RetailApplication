package com.retailer.model;

import lombok.Data;

@Data
public class TransactionPointsSummary {

	private String transactionId;
	private String customerId;
	private int transactionPoints;

	private TransactionPointsSummary() {
	}

	public TransactionPointsSummary(String transactionId, String customerId, int transactionPoints) {
		this.transactionId = transactionId;
		this.customerId = customerId;
		this.transactionPoints = transactionPoints;
	}

}
