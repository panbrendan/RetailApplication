package com.retailer.controller;

import com.retailer.model.TransactionPointsSummary;
import com.retailer.request.TransactionRequest;
import com.retailer.response.TransactionResponse;
import com.retailer.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.retailer.validator.TransactionValidator.validateTransaction;

@RestController
@RequestMapping(value="/transaction", produces = { MediaType.APPLICATION_JSON_VALUE })
public class TransactionController {

	private static final Logger LOG = LoggerFactory.getLogger(TransactionController.class);
	private final TransactionService transactionService;

	@Autowired
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PutMapping("/process/{transactionId}")
	public ResponseEntity<TransactionResponse> processTransaction(@PathVariable("transactionId") String transactionId, @RequestBody TransactionRequest request) {

		LOG.info("Received transaction: {}", transactionId);
		validateTransaction(transactionId, request);

		TransactionPointsSummary transactionPointsSummary = transactionService.processTransaction(transactionId, request.getCustomerId(), request.getAmount(), request.getTransactionDate());

		TransactionResponse response = new TransactionResponse();
		response.setTransactionPointsSummary(transactionPointsSummary);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
