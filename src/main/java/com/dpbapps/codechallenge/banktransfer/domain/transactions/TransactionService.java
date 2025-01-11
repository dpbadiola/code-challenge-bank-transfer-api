package com.dpbapps.codechallenge.banktransfer.domain.transactions;

import com.dpbapps.codechallenge.banktransfer.domain.transactions.dto.TransactionRequest;
import com.dpbapps.codechallenge.banktransfer.domain.transactions.dto.TransactionResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
public class TransactionService {

	public TransactionResponse getTransaction(UUID uuid) {
		return TransactionResponse.builder()
			.uuid(UUID.randomUUID())
			.transactionTimestamp(Instant.now())
			.postingTimestamp(Instant.now())
			.amount(BigDecimal.ONE)
			.transferFee(BigDecimal.ZERO)
			.endingBalance(BigDecimal.TEN)
			.build()
			;
	}

	public UUID createTransaction(TransactionRequest request) {
		return UUID.randomUUID();
	}

}
