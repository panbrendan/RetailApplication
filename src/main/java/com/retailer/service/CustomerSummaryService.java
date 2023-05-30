package com.retailer.service;

import com.retailer.model.Customer;
import com.retailer.model.CustomerSummary;
import com.retailer.model.MonthlyPoints;
import com.retailer.model.TotalPoints;
import com.retailer.model.TransactionPointsSummary;
import com.retailer.repository.CustomerRepository;
import com.retailer.repository.MonthlyPointsRepository;
import com.retailer.repository.TotalPointsRepository;
import com.retailer.response.CustomerSummaryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static com.retailer.util.RetailerUtil.createMonthlyPoints;
import static com.retailer.util.RetailerUtil.createTotalPoints;
import static com.retailer.util.RetailerUtil.createTransactionPointsSummary;

@Service
public class CustomerSummaryService {

	private static final Logger LOG = LoggerFactory.getLogger(CustomerSummaryService.class);
	private final CustomerRepository customerRepository;
	private final MonthlyPointsRepository monthlyPointsRepository;
	private final TotalPointsRepository totalPointsRepository;

	@Autowired
	public CustomerSummaryService(CustomerRepository customerRepository, MonthlyPointsRepository monthlyPointsRepository, TotalPointsRepository totalPointsRepository) {

		this.customerRepository = customerRepository;
		this.monthlyPointsRepository = monthlyPointsRepository;
		this.totalPointsRepository = totalPointsRepository;
	}

	public CustomerSummary retrieveCustomerSummary(String customerId, int year, int month) {

		//We would retrieve the customer summary info here

		//Look at customer repo, get customer information and verify customer exists, if not, return a 204 No Content

		//Retrieve points for the month based off year/month from monthRepo

		//Retrieve total points from totalRepo

		//Create customer summary
		return new CustomerSummary(customerId, 0, 0);
	}
}
