package com.retailer.validator;

import com.retailer.constants.Constants;
import com.retailer.exception.BadRequestException;
import com.retailer.request.TransactionRequest;

import java.math.BigDecimal;

import static org.springframework.util.StringUtils.hasLength;

public final class TransactionValidator {

	private TransactionValidator() {
		//No-ops
	}

	public static void validateTransaction(String transactionId, TransactionRequest request) {

		//Only validation for customer is not null, we COULD add validation on whether it exists, but not for now
		if (!hasLength(transactionId)) {
			throw new BadRequestException(Constants.MESSAGE_INVALID_TRANSACTION_ID);
		} else if (!hasLength(request.getCustomerId())) {
			throw new BadRequestException(Constants.MESSAGE_INVALID_CUSTOMER_ID);
		} else if (null == request.getAmount() || isZeroOrNegative(request.getAmount())) {
			throw new BadRequestException(Constants.MESSAGE_INVALID_AMOUNT);
		} else if (null == request.getTransactionDate()) {
			throw new BadRequestException(Constants.MESSAGE_INVALID_DATE);
		}
	}

	private static boolean isZeroOrNegative(BigDecimal amount) {
		return 0 == amount.signum() || -1 == amount.signum();
	}
}
