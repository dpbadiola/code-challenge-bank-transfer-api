package com.dpbapps.codechallenge.banktransfer.domain.transactions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record TransactionResponse(

	@JsonProperty("uuid")
	@Schema(description = "Transaction's UUID")
	UUID uuid,

	@JsonProperty("transaction_timestamp")
	@Schema(description = "When transaction was created")
	Instant transactionTimestamp,

	@JsonProperty("posting_timestamp")
	@Schema(description = "When transaction will be posted to the recipient bank's account number")
	Instant postingTimestamp,

	@JsonProperty("ending_balance")
	@Schema(description = "Ending balance of account after transaction request")
	BigDecimal endingBalance
) {

}
