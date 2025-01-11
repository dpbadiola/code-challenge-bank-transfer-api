package com.dpbapps.codechallenge.banktransfer.domain.transactions;

import com.dpbapps.codechallenge.banktransfer.domain.accounts.Accounts;
import com.dpbapps.codechallenge.banktransfer.domain.accounts.AccountsRepository;
import com.dpbapps.codechallenge.banktransfer.domain.transactions.exceptions.RecordNotFoundException;
import com.dpbapps.codechallenge.banktransfer.domain.transactions.dto.TransactionRequest;
import com.dpbapps.codechallenge.banktransfer.domain.transactions.dto.TransactionResponse;
import com.dpbapps.codechallenge.banktransfer.domain.transactions.exceptions.TransactionsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

	private final AccountsRepository accountsRepository;

	private final TransactionsRepository transactionsRepository;

	@Transactional(readOnly = true)
	public TransactionResponse getTransaction(UUID uuid) {
		return transactionsRepository.findById(uuid)
			.map(this::mapTransactions)
			.orElseThrow(() -> new RecordNotFoundException("Transaction not found"))
			;
	}

	@Transactional
	public UUID createTransaction(TransactionRequest request) {
		Long accountId = accountsRepository.findByAccountNumber(request.senderAccountNumber())
			.map(Accounts::getId)
			.orElseThrow(() -> new TransactionsException("Sender account number not found"));

		BigDecimal accountBalance = getAccountBalance(accountId);
		if (accountBalance.subtract(request.amount()).compareTo(BigDecimal.ZERO) < 0) {
			throw new TransactionsException("Insufficient balance");
		}

		// TODO: persist transaction
		// TODO: persist transfer details

		return UUID.randomUUID();
	}

	private BigDecimal getAccountBalance(Long accountId) {
		return transactionsRepository.findByAccountId(accountId).stream()
			.map(transaction -> transaction.getCredit().subtract(transaction.getDebit()))
			.reduce(BigDecimal.ZERO, BigDecimal::add);
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
