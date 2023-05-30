package com.retailer.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TransactionRequest {

	private String customerId;
	private BigDecimal amount;
	private LocalDate transactionDate;
}
