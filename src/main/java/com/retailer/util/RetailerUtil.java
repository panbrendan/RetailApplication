package com.retailer.util;

import com.retailer.model.MonthlyPoints;
import com.retailer.model.MonthlyPointsKey;
import com.retailer.model.TransactionPointsSummary;
import com.retailer.model.TotalPoints;

public final class RetailerUtil {

    private RetailerUtil() {
        //No-ops constructor
    }

    public static MonthlyPoints createMonthlyPoints(String customerId, int year, int month, int transactionPoints) {

        MonthlyPointsKey monthlyPointsKey = new MonthlyPointsKey(customerId, year, month);
        return new MonthlyPoints(monthlyPointsKey, transactionPoints);
    }

    public static TotalPoints createTotalPoints(String customerId, int transactionPoints) {

        return new TotalPoints(customerId, transactionPoints);
    }

    public static TransactionPointsSummary createTransactionPointsSummary(String transactionId, String customerId, int transactionPoints) {

        return new TransactionPointsSummary(transactionId, customerId, transactionPoints);
    }
}
