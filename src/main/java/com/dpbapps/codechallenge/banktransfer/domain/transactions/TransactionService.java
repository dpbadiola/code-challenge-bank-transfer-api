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
import java.time.Instant;
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
		Accounts sender = accountsRepository.findByAccountNumber(request.senderAccountNumber())
			.orElseThrow(() -> new TransactionsException("Sender account number not found"));

		BigDecimal senderAccountBalance = getAccountBalance(sender.getId());
		if (senderAccountBalance.subtract(request.amount()).compareTo(BigDecimal.ZERO) < 0) {
			throw new TransactionsException("Insufficient balance");
		}

		Accounts recipient = accountsRepository.findByAccountNumber(request.senderAccountNumber())
			.orElseThrow(() -> new TransactionsException("Recipient account number not found"));

		Instant timestamp = Instant.now();

		Transactions senderTransaction = Transactions.builder()
			.uuid(UUID.randomUUID())
			.createdDate(timestamp)
			.postingDate(timestamp)
			.debit(request.amount())
			.credit(BigDecimal.ZERO)
			.account(sender)
			.transferType("Internal")
			.transferFee(BigDecimal.ZERO)
			.endingBalance(senderAccountBalance.subtract(request.amount()))
			.build();
		transactionsRepository.save(senderTransaction);

		BigDecimal recipientAccountBalance = getAccountBalance(recipient.getId());
		Transactions recipientTransaction = Transactions.builder()
			.uuid(UUID.randomUUID())
			.createdDate(timestamp)
			.postingDate(timestamp)
			.debit(BigDecimal.ZERO)
			.credit(request.amount())
			.account(recipient)
			.transferType("Internal")
			.transferFee(BigDecimal.ZERO)
			.endingBalance(recipientAccountBalance.add(request.amount()))
			.build();
		transactionsRepository.save(recipientTransaction);

		return senderTransaction.getUuid();
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
