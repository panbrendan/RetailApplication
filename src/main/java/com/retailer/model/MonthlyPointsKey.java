package com.retailer.model;


import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
@Data
public class MonthlyPointsKey implements Serializable {

    private String customerId;
    private int year;
    private int month;

    public MonthlyPointsKey() {
    }

    public MonthlyPointsKey(String customerId, int year, int month) {
        this.customerId = customerId;
        this.year = year;
        this.month = month;
    }

}
