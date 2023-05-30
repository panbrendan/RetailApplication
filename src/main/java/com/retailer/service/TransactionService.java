package com.retailer.service;

import com.retailer.model.*;
import com.retailer.repository.CustomerRepository;
import com.retailer.repository.MonthlyPointsRepository;
import com.retailer.repository.TotalPointsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static com.retailer.util.RetailerUtil.*;

@Service
public class TransactionService {

	private static final Logger LOG = LoggerFactory.getLogger(TransactionService.class);
	private final CustomerRepository customerRepository;
	private final MonthlyPointsRepository monthlyPointsRepository;
	private final TotalPointsRepository totalPointsRepository;

	@Autowired
	public TransactionService(CustomerRepository customerRepository, MonthlyPointsRepository monthlyPointsRepository, TotalPointsRepository totalPointsRepository) {

		this.customerRepository = customerRepository;
		this.monthlyPointsRepository = monthlyPointsRepository;
		this.totalPointsRepository = totalPointsRepository;
	}

	public TransactionPointsSummary processTransaction(String transactionId, String customerId, BigDecimal amount, LocalDate transactionDate) {

		Customer customer = getCustomer(customerId);
		return calculatePointsAndReturn(transactionId, customer, amount, transactionDate);
	}


	private Customer getCustomer(String customerId) {

		Optional<Customer> customerOpt = customerRepository.findById(customerId);
		//Assume to create new customer if customerId is not found
		return customerOpt.orElse(new Customer(customerId));
	}

	private TransactionPointsSummary calculatePointsAndReturn(String transactionId, Customer customer, BigDecimal amount, LocalDate transactionDate) {

		int transactionPoints = calculateTransactionPoints(amount);
		LOG.info("{} : Obtained {} points for customer {}!", transactionId, transactionPoints, customer.getCustomerId());

		if(transactionPoints > 0) {
			storeMonthlyPoints(transactionId, customer.getCustomerId(), transactionPoints, transactionDate);
			storeTotalPoints(transactionId, customer.getCustomerId(), transactionPoints);
		}

		return createTransactionPointsSummary(transactionId, customer.getCustomerId(), transactionPoints);
	}

	private int calculateTransactionPoints(BigDecimal amount) {

		BigDecimal baseAmount = BigDecimal.valueOf(50);
		BigDecimal extraAmount = BigDecimal.valueOf(100);

		if (amount.compareTo(extraAmount) > 0) {
			BigDecimal extraPoints = amount.subtract(extraAmount);
			BigDecimal basePoints = extraAmount.subtract(baseAmount);
			return extraPoints.intValue() * 2 + basePoints.intValue();
		} else if (amount.compareTo(baseAmount) > 0) {
			return amount.subtract(baseAmount).intValue();
		} else {
			return 0;
		}
	}

	private void storeMonthlyPoints(String transactionId, String customerId, int transactionPoints, LocalDate transactionDate) {

		int year = transactionDate.getYear();
		int month = transactionDate.getYear();
		MonthlyPoints monthlyPoints = monthlyPointsRepository.findByMonthlyPointsKeyCustomerIdAndMonthlyPointsKeyYearAndMonthlyPointsKeyMonth(customerId, year, month);
		int points;
		if(null == monthlyPoints) {
			points = createNewMonthlyPoints(customerId, year, month, transactionPoints);
		} else {
			points = updateAndSaveMonthlyPoints(monthlyPoints, transactionPoints);
		}
		LOG.info("{} : Monthly Points: {} for customer {}", transactionId, points, customerId);
	}

	private int createNewMonthlyPoints(String customerId, int year, int month, int transactionPoints) {

		MonthlyPoints monthlyPoints = createMonthlyPoints(customerId, year, month, transactionPoints);
		return monthlyPointsRepository.save(monthlyPoints).getMonthlyPoints();
	}

	private int updateAndSaveMonthlyPoints(MonthlyPoints monthlyPoints, int transactionPoints) {

		int newPoints = monthlyPoints.getMonthlyPoints() + transactionPoints;
		monthlyPoints.setMonthlyPoints(newPoints);
		return monthlyPointsRepository.save(monthlyPoints).getMonthlyPoints();
	}

	private void storeTotalPoints(String transactionId, String customerId, int transactionPoints) {

		Optional<TotalPoints> totalPointsOpt = totalPointsRepository.findById(customerId);
		int points = totalPointsOpt.map(totalPoints -> updateAndSaveTotalPoints(totalPoints, transactionPoints)).orElseGet(() -> createNewTotalPoints(customerId, transactionPoints));
		LOG.info("{} : Total Points: {} for customer {}", transactionId, points, customerId);
	}

	private int updateAndSaveTotalPoints(TotalPoints totalPoints, int transactionPoints) {

		int newPoints = totalPoints.getTotalPoints() + transactionPoints;
		totalPoints.setTotalPoints(newPoints);
		return totalPointsRepository.save(totalPoints).getTotalPoints();
	}

	private int createNewTotalPoints(String customerId, int transactionPoints) {

		TotalPoints totalPoints = createTotalPoints(customerId, transactionPoints);
		return totalPointsRepository.save(totalPoints).getTotalPoints();
	}


}
