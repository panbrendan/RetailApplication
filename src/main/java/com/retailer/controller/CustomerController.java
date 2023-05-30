package com.retailer.controller;

import com.retailer.model.CustomerSummary;
import com.retailer.response.CustomerSummaryResponse;
import com.retailer.service.CustomerSummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.retailer.validator.TransactionValidator.validateTransaction;

@RestController
@RequestMapping(value="/customer", produces = { MediaType.APPLICATION_JSON_VALUE })
public class CustomerController {

	private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);
	private final CustomerSummaryService customerSummaryService;

	@Autowired
	public CustomerController(CustomerSummaryService customerSummaryService) {

		this.customerSummaryService = customerSummaryService;
	}

	@GetMapping("/summary/{customerId}")
	public ResponseEntity<CustomerSummaryResponse> getCustomerSummary(@PathVariable String customerId, @RequestParam int year, @RequestParam int month) {

		LOG.info("Received customer summary request: {}", customerId);

		CustomerSummary customerSummary = customerSummaryService.retrieveCustomerSummary(customerId, year, month);

		CustomerSummaryResponse response = new CustomerSummaryResponse();
		response.setCustomerSummary(customerSummary);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
