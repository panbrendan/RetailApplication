package com.retailer.repository;

import static com.retailer.constants.TestConstants.TEST_CUSTOMER_ID_1;
import static com.retailer.constants.TestConstants.TEST_CUSTOMER_ID_2;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.retailer.model.Customer;
import com.retailer.model.TransactionPointsSummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CustomerRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private CustomerRepository repository;

	@Test
	public void testRepo() {

		Customer customer1 = new Customer(TEST_CUSTOMER_ID_1);
		entityManager.persist(customer1);
		entityManager.flush();

		assertThat(repository.findAll().size()).isEqualTo(1);

		// save
		Customer customer2 = new Customer(TEST_CUSTOMER_ID_2);
		Customer savedCustomer = repository.save(customer2);

		assertThat(repository.findAll().size()).isEqualTo(2);
		assertThat(savedCustomer.getCustomerId()).isEqualTo(TEST_CUSTOMER_ID_2);
	}
}
