package com.retailer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.retailer.model.TransactionPointsSummary;
import com.retailer.request.TransactionRequest;
import com.retailer.service.TransactionService;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import static com.retailer.constants.TestConstants.TEST_120_AMOUNT;
import static com.retailer.constants.TestConstants.TEST_CUSTOMER_ID_1;
import static com.retailer.constants.TestConstants.TEST_DATE;
import static com.retailer.constants.TestConstants.TEST_TRANSACTION_ID;
import static com.retailer.util.TestUtils.getPutRequestBuilder;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(Parameterized.class)
@WebMvcTest(value = TransactionController.class, secure = false)
public class TransactionControllerTest {

	private static final String PROCESS_TRANSACTION_ENDPOINT = "/transaction/process/{transactionId}";
	@ClassRule
	public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

	@Rule
	public final SpringMethodRule springMethodRule = new SpringMethodRule();
	@MockBean
	private TransactionService transactionService;
	@Autowired
	private MockMvc mockMvc;

	private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

	private final String transactionId;
	private final TransactionRequest tRequest;

	public TransactionControllerTest(String transactionId, TransactionRequest tRequest) {
		this.transactionId = transactionId;
		this.tRequest = tRequest;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> data() {

		Collection<Object[]> params = new ArrayList<>();

		TransactionRequest request1 = new TransactionRequest();
		request1.setCustomerId(TEST_CUSTOMER_ID_1);
		request1.setAmount(TEST_120_AMOUNT);
		request1.setTransactionDate(TEST_DATE);
		TransactionRequest request2 = new TransactionRequest();
		request2.setCustomerId("");
		request2.setAmount(TEST_120_AMOUNT);
		request2.setTransactionDate(TEST_DATE);
		TransactionRequest request3 = new TransactionRequest();
		request3.setCustomerId(null);
		request3.setAmount(TEST_120_AMOUNT);
		request3.setTransactionDate(TEST_DATE);
		TransactionRequest request4 = new TransactionRequest();
		request4.setCustomerId(TEST_CUSTOMER_ID_1);
		request4.setAmount(null);
		request4.setTransactionDate(TEST_DATE);
		TransactionRequest request5 = new TransactionRequest();
		request5.setCustomerId(TEST_CUSTOMER_ID_1);
		request5.setAmount(BigDecimal.ZERO);
		request5.setTransactionDate(TEST_DATE);
		TransactionRequest request6 = new TransactionRequest();
		request6.setCustomerId(TEST_CUSTOMER_ID_1);
		request6.setAmount(BigDecimal.valueOf(-1));
		request6.setTransactionDate(TEST_DATE);
		TransactionRequest request7 = new TransactionRequest();
		request7.setCustomerId(TEST_CUSTOMER_ID_1);
		request7.setAmount(TEST_120_AMOUNT);
		request7.setTransactionDate(null);

		params.add(new Object[] {TEST_TRANSACTION_ID, request1});
		params.add(new Object[] {"", request1});
		params.add(new Object[] {null, request1});
		params.add(new Object[] {TEST_TRANSACTION_ID, null});
		params.add(new Object[] {TEST_TRANSACTION_ID, request2});
		params.add(new Object[] {TEST_TRANSACTION_ID, request3});
		params.add(new Object[] {TEST_TRANSACTION_ID, request4});
		params.add(new Object[] {TEST_TRANSACTION_ID, request5});
		params.add(new Object[] {TEST_TRANSACTION_ID, request6});
		params.add(new Object[] {TEST_TRANSACTION_ID, request7});

		return params;
	}

	@Test
	public void processTransaction() throws Exception {
		boolean shouldThrowNotFound = null == transactionId || "".equalsIgnoreCase(transactionId);
		boolean shouldThrowBadRequest = null == tRequest  || null == tRequest.getCustomerId() || "".equalsIgnoreCase(tRequest.getCustomerId()) || null == tRequest.getTransactionDate() || null == tRequest.getAmount() ||
				0 == tRequest.getAmount().signum() || -1 == tRequest.getAmount().signum();

		TransactionPointsSummary summary = new TransactionPointsSummary(TEST_TRANSACTION_ID, TEST_CUSTOMER_ID_1, 50);
		when(transactionService.processTransaction(anyString(), anyString(), any(BigDecimal.class), any(LocalDate.class))).thenReturn(summary);

		String requestAsJson = objectMapper.writeValueAsString(tRequest);
		RequestBuilder requestBuilder = getPutRequestBuilder(PROCESS_TRANSACTION_ENDPOINT, transactionId, requestAsJson);

		ResultActions resultActions = mockMvc.perform(requestBuilder);
		if(shouldThrowNotFound) {
			resultActions.andExpect(MockMvcResultMatchers.status().isNotFound());
		} else if (shouldThrowBadRequest) {
			resultActions.andExpect(MockMvcResultMatchers.status().isBadRequest());
		} else {
			resultActions.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(jsonPath("$.transactionPointsSummary.transactionPoints", equalTo(summary.getTransactionPoints())));
		}
	}
}