package com.retailer.service;

import com.retailer.model.Customer;
import com.retailer.model.MonthlyPoints;
import com.retailer.model.TotalPoints;
import com.retailer.model.TransactionPointsSummary;
import com.retailer.repository.CustomerRepository;
import com.retailer.repository.MonthlyPointsRepository;
import com.retailer.repository.TotalPointsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static com.retailer.constants.TestConstants.TEST_120_AMOUNT;
import static com.retailer.constants.TestConstants.TEST_23_AMOUNT;
import static com.retailer.constants.TestConstants.TEST_80_AMOUNT;
import static com.retailer.constants.TestConstants.TEST_CUSTOMER_ID_1;
import static com.retailer.constants.TestConstants.TEST_DATE;
import static com.retailer.constants.TestConstants.TEST_TRANSACTION_ID;
import static com.retailer.util.TestUtils.createTestCustomer;
import static com.retailer.util.TestUtils.createTestMonthlyPoints;
import static com.retailer.util.TestUtils.createTestTotalPoints;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionServiceTest {
	
	@MockBean
	private CustomerRepository customerRepository;
	@MockBean
	private MonthlyPointsRepository monthlyPointsRepository;
	@MockBean
	private TotalPointsRepository totalPointsRepository;

	@Captor
	private ArgumentCaptor<MonthlyPoints> monthlyPointsArgumentCaptor;

	@Captor
	private ArgumentCaptor<TotalPoints> totalPointsArgumentCaptor;

	private TransactionService service;
	
	@Before
	public void setUp() {
		service = new TransactionService(customerRepository, monthlyPointsRepository, totalPointsRepository);
	}

	@Test
	public void testProcessTransactionExistingCustomerExtraPoints() {

		Customer testCustomer = createTestCustomer(TEST_CUSTOMER_ID_1);
		MonthlyPoints testMonthlyPoints = createTestMonthlyPoints(TEST_CUSTOMER_ID_1, 200);
		TotalPoints testTotalPoints = createTestTotalPoints(TEST_CUSTOMER_ID_1, 500);

		Mockito.when(customerRepository.findById(anyString())).thenReturn(Optional.of(testCustomer));
		Mockito.when(monthlyPointsRepository.findByMonthlyPointsKeyCustomerIdAndMonthlyPointsKeyYearAndMonthlyPointsKeyMonth(anyString(), anyInt(), anyInt())).thenReturn(testMonthlyPoints);
		Mockito.when(totalPointsRepository.findById(anyString())).thenReturn(Optional.of(testTotalPoints));
		Mockito.when(monthlyPointsRepository.save(any(MonthlyPoints.class))).thenAnswer(i -> i.getArguments()[0]);
		Mockito.when(totalPointsRepository.save(any(TotalPoints.class))).thenAnswer(i -> i.getArguments()[0]);

		TransactionPointsSummary transactionPointsSummary = service.processTransaction(TEST_TRANSACTION_ID, TEST_CUSTOMER_ID_1, TEST_120_AMOUNT, TEST_DATE);

		assertThat(transactionPointsSummary).isNotNull();
		assertThat(transactionPointsSummary.getCustomerId()).isEqualTo(TEST_CUSTOMER_ID_1);
		assertThat(transactionPointsSummary.getTransactionPoints()).isEqualTo(90);

		verify(customerRepository, times(1)).findById(anyString());
		verify(monthlyPointsRepository, times(1)).findByMonthlyPointsKeyCustomerIdAndMonthlyPointsKeyYearAndMonthlyPointsKeyMonth(anyString(), anyInt(), anyInt());
		verify(monthlyPointsRepository).save(monthlyPointsArgumentCaptor.capture());
		MonthlyPoints savedMonthlyPoints = monthlyPointsArgumentCaptor.getValue();
		assertThat(savedMonthlyPoints).isNotNull();
		assertThat(savedMonthlyPoints.getMonthlyPoints()).isEqualTo(290);
		verify(totalPointsRepository, times(1)).findById(anyString());
		verify(totalPointsRepository).save(totalPointsArgumentCaptor.capture());
		TotalPoints savedTotalPoints = totalPointsArgumentCaptor.getValue();
		assertThat(savedTotalPoints).isNotNull();
		assertThat(savedTotalPoints.getTotalPoints()).isEqualTo(590);
	}

	@Test
	public void testProcessTransactionExistingCustomerLowPoints() {

		Customer testCustomer = createTestCustomer(TEST_CUSTOMER_ID_1);
		MonthlyPoints testMonthlyPoints = createTestMonthlyPoints(TEST_CUSTOMER_ID_1, 200);
		TotalPoints testTotalPoints = createTestTotalPoints(TEST_CUSTOMER_ID_1, 500);

		Mockito.when(customerRepository.findById(anyString())).thenReturn(Optional.of(testCustomer));
		Mockito.when(monthlyPointsRepository.findByMonthlyPointsKeyCustomerIdAndMonthlyPointsKeyYearAndMonthlyPointsKeyMonth(anyString(), anyInt(), anyInt())).thenReturn(testMonthlyPoints);
		Mockito.when(totalPointsRepository.findById(anyString())).thenReturn(Optional.of(testTotalPoints));
		Mockito.when(monthlyPointsRepository.save(any(MonthlyPoints.class))).thenAnswer(i -> i.getArguments()[0]);
		Mockito.when(totalPointsRepository.save(any(TotalPoints.class))).thenAnswer(i -> i.getArguments()[0]);

		TransactionPointsSummary transactionPointsSummary = service.processTransaction(TEST_TRANSACTION_ID, TEST_CUSTOMER_ID_1, TEST_80_AMOUNT, TEST_DATE);

		assertThat(transactionPointsSummary).isNotNull();
		assertThat(transactionPointsSummary.getCustomerId()).isEqualTo(TEST_CUSTOMER_ID_1);
		assertThat(transactionPointsSummary.getTransactionPoints()).isEqualTo(30);

		verify(customerRepository, times(1)).findById(anyString());
		verify(monthlyPointsRepository, times(1)).findByMonthlyPointsKeyCustomerIdAndMonthlyPointsKeyYearAndMonthlyPointsKeyMonth(anyString(), anyInt(), anyInt());
		verify(monthlyPointsRepository).save(monthlyPointsArgumentCaptor.capture());
		MonthlyPoints savedMonthlyPoints = monthlyPointsArgumentCaptor.getValue();
		assertThat(savedMonthlyPoints).isNotNull();
		assertThat(savedMonthlyPoints.getMonthlyPoints()).isEqualTo(230);
		verify(totalPointsRepository, times(1)).findById(anyString());
		verify(totalPointsRepository).save(totalPointsArgumentCaptor.capture());
		TotalPoints savedTotalPoints = totalPointsArgumentCaptor.getValue();
		assertThat(savedTotalPoints).isNotNull();
		assertThat(savedTotalPoints.getTotalPoints()).isEqualTo(530);
	}

	@Test
	public void testProcessTransactionExistingCustomerNoPoints() {

		Customer testCustomer = createTestCustomer(TEST_CUSTOMER_ID_1);

		Mockito.when(customerRepository.findById(anyString())).thenReturn(Optional.of(testCustomer));

		TransactionPointsSummary transactionPointsSummary = service.processTransaction(TEST_TRANSACTION_ID, TEST_CUSTOMER_ID_1, TEST_23_AMOUNT, TEST_DATE);

		assertThat(transactionPointsSummary).isNotNull();
		assertThat(transactionPointsSummary.getCustomerId()).isEqualTo(TEST_CUSTOMER_ID_1);
		assertThat(transactionPointsSummary.getTransactionPoints()).isEqualTo(0);

		verify(customerRepository, times(1)).findById(anyString());
		verify(monthlyPointsRepository, times(0)).findByMonthlyPointsKeyCustomerIdAndMonthlyPointsKeyYearAndMonthlyPointsKeyMonth(anyString(), anyInt(), anyInt());
		verify(totalPointsRepository, times(0)).findById(anyString());
	}

	@Test
	public void testProcessTransactionNewCustomerExtraPoints() {

		Mockito.when(customerRepository.findById(anyString())).thenReturn(Optional.empty());
		Mockito.when(monthlyPointsRepository.findByMonthlyPointsKeyCustomerIdAndMonthlyPointsKeyYearAndMonthlyPointsKeyMonth(anyString(), anyInt(), anyInt())).thenReturn(null);
		Mockito.when(totalPointsRepository.findById(anyString())).thenReturn(Optional.empty());
		Mockito.when(monthlyPointsRepository.save(any(MonthlyPoints.class))).thenAnswer(i -> i.getArguments()[0]);
		Mockito.when(totalPointsRepository.save(any(TotalPoints.class))).thenAnswer(i -> i.getArguments()[0]);

		TransactionPointsSummary transactionPointsSummary = service.processTransaction(TEST_TRANSACTION_ID, TEST_CUSTOMER_ID_1, TEST_120_AMOUNT, TEST_DATE);

		assertThat(transactionPointsSummary).isNotNull();
		assertThat(transactionPointsSummary.getCustomerId()).isEqualTo(TEST_CUSTOMER_ID_1);
		assertThat(transactionPointsSummary.getTransactionPoints()).isEqualTo(90);

		verify(customerRepository, times(1)).findById(anyString());
		verify(monthlyPointsRepository, times(1)).findByMonthlyPointsKeyCustomerIdAndMonthlyPointsKeyYearAndMonthlyPointsKeyMonth(anyString(), anyInt(), anyInt());
		verify(monthlyPointsRepository).save(monthlyPointsArgumentCaptor.capture());
		MonthlyPoints savedMonthlyPoints = monthlyPointsArgumentCaptor.getValue();
		assertThat(savedMonthlyPoints).isNotNull();
		assertThat(savedMonthlyPoints.getMonthlyPoints()).isEqualTo(90);
		verify(totalPointsRepository, times(1)).findById(anyString());
		verify(totalPointsRepository).save(totalPointsArgumentCaptor.capture());
		TotalPoints savedTotalPoints = totalPointsArgumentCaptor.getValue();
		assertThat(savedTotalPoints).isNotNull();
		assertThat(savedTotalPoints.getTotalPoints()).isEqualTo(90);
	}

}
