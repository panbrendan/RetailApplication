package com.retailer.repository;

import com.retailer.model.MonthlyPoints;
import com.retailer.model.MonthlyPointsKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyPointsRepository extends JpaRepository<MonthlyPoints, MonthlyPointsKey> {

    MonthlyPoints findByMonthlyPointsKeyCustomerIdAndMonthlyPointsKeyYearAndMonthlyPointsKeyMonth(
            String customerId, int year, int month);
}
