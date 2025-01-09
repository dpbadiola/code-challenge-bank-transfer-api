package com.dpbapps.codechallenge.banktransfer.domain.transactions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransactionRequest(
	@JsonProperty("sender_account_number")
	@Schema(description = "Sender's account number", example = "ORANJE00001234")
	String senderAccountNumber,

	@JsonProperty("recipient_account number")
	@Schema(description = "Recipient's account number", example = "BLU00001234")
	String recipientAccountNumber,

	@JsonProperty("recipient_bank_code")
	@Schema(description = "Recipient's bank code", example = "BLU")
	String recipientBankCode,

	@JsonProperty("transfer_amount")
	@Schema(description = "Transfer amount", example = "10.00")
	BigDecimal amount,

	@JsonProperty("transfer_type")
	@Schema(description = "Transfer method/type", example = "InstaPay")
	String type
) {

}
