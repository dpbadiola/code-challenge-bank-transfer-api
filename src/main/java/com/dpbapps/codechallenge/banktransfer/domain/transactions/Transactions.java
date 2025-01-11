package com.dpbapps.codechallenge.banktransfer.domain.transactions;

import com.dpbapps.codechallenge.banktransfer.domain.accounts.Accounts;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "transactions")
public class Transactions {

	@Id
	@Column(name = "id")
	private UUID uuid;

	@Column(name = "create_date")
	private Instant createdDate;

	@Column(name = "posting_date")
	private Instant postingDate;

	@Column(precision = 15, scale = 2)
	private BigDecimal debit;

	@Column(precision = 15, scale = 2)
	private BigDecimal credit;

	@OneToOne
	@JoinColumn(name = "account_id")
	private Accounts account;

	@Column(precision = 15, scale = 2, name = "transfer_fee")
	private BigDecimal transferFee;

	@Column(name = "transfer_type")
	private String transferType;

	@Column(name = "transfer_metadata")
	private String transferMetaData;

	@Column(precision = 15, scale = 2, name = "ending_balance")
	private BigDecimal endingBalance;

}
