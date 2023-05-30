package com.retailer.repository;

import com.retailer.model.Customer;
import com.retailer.model.TotalPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
public interface TotalPointsRepository extends JpaRepository<TotalPoints, String> {

}
