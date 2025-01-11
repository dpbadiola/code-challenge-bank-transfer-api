package com.dpbapps.codechallenge.banktransfer.domain.transactions;

import com.dpbapps.codechallenge.banktransfer.domain.error.exceptions.RecordNotFoundException;
import com.dpbapps.codechallenge.banktransfer.domain.transactions.dto.TransactionRequest;
import com.dpbapps.codechallenge.banktransfer.domain.transactions.dto.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

	private final TransactionsRepository repository;

	@Transactional(readOnly = true)
	public TransactionResponse getTransaction(UUID uuid) {
		return repository.findById(uuid)
			.map(this::mapTransactions)
			.orElseThrow(() -> new RecordNotFoundException("Transaction not found"))
			;
	}

	public UUID createTransaction(TransactionRequest request) {
		return UUID.randomUUID();
	}

	private TransactionResponse mapTransactions(Transactions entity) {
		return TransactionResponse.builder()
			.uuid(entity.getUuid())
			.transactionTimestamp(entity.getCreatedDate())
			.postingTimestamp(entity.getPostingDate())
			.amount(entity.getCredit().subtract(entity.getDebit()))
			.transferFee(entity.getTransferFee())
			.endingBalance(entity.getEndingBalance())
			.build()
			;
	}

}
