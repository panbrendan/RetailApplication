package com.retailer.util;

import com.retailer.model.Customer;
import com.retailer.model.MonthlyPoints;
import com.retailer.model.MonthlyPointsKey;
import com.retailer.model.TotalPoints;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class TestUtils {

	public static RequestBuilder getPutRequestBuilder(String url, String transactionId, String requestAsJson) {
		return MockMvcRequestBuilders
				.put(url, transactionId)
				.accept(MediaType.APPLICATION_JSON)
				.content(requestAsJson)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8");
	}

	public static RequestBuilder getGetRequestBuilder(String url) {
		return MockMvcRequestBuilders
				.get(url)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8");
	}

	public static Customer createTestCustomer(String customerId) {
		Customer customer = new Customer(customerId);
		customer.setFirstName("John");
		customer.setLastName("Smith");
		customer.setEmail("john.smith@gmail.com");
		return customer;
	}

	public static MonthlyPoints createTestMonthlyPoints(String customerId, int existingPoints) {
		MonthlyPointsKey monthlyPointsKey = new MonthlyPointsKey(customerId, 2023, 1);
		return new MonthlyPoints(monthlyPointsKey, existingPoints);
	}

	public static TotalPoints createTestTotalPoints(String customerId, int existingPoints) {
		return new TotalPoints(customerId, existingPoints);
	}

}
