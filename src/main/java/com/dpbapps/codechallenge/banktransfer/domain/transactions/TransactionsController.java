package com.dpbapps.codechallenge.banktransfer.domain.transactions;

import com.dpbapps.codechallenge.banktransfer.domain.transactions.exceptions.RecordNotFoundException;
import com.dpbapps.codechallenge.banktransfer.domain.transactions.dto.TransactionRequest;
import com.dpbapps.codechallenge.banktransfer.domain.transactions.dto.TransactionResponse;
import com.dpbapps.codechallenge.banktransfer.domain.transactions.exceptions.TransactionsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@Tag(name = "transactions")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/transactions")
public class TransactionsController {

	private final TransactionService transactionService;

	@Operation(
		summary = "Get transaction details",
		parameters = {
			@Parameter(name = "uuid", description = "UUID of transaction", required = true)
		},
		responses = {
			@ApiResponse(
				responseCode = "200", description = "Transaction exists",
				content = @Content(schema = @Schema(implementation = TransactionResponse.class))
			),
			@ApiResponse(
				responseCode = "404", description = "Transaction does not exists",
				content = @Content(schema = @Schema(implementation = ProblemDetail.class))
			)
		}
	)
	@GetMapping("/{uuid}")
	@ResponseStatus(HttpStatus.OK)
	public TransactionResponse getTransaction(@PathVariable("uuid") UUID uuid) {
		return transactionService.getTransaction(uuid);
	}

	@Operation(
		summary = "Make a transfer request",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = @Content(schema = @Schema(implementation = TransactionRequest.class)),
			description = "Transaction payload",
			required = true
		),
		responses = {
			@ApiResponse(
				responseCode = "201", description = "Transaction created",
				content = { @Content(schema = @Schema(implementation = URI.class)) }
			),
			@ApiResponse(
				responseCode = "400", description = "Transaction request invalid",
				content = @Content(schema = @Schema(implementation = ProblemDetail.class))
			)
		}
	)
	@PostMapping("transfer")
	@ResponseStatus(HttpStatus.CREATED)
	public URI createTransaction(@RequestBody TransactionRequest request) {
		return URI.create("/transactions/" + transactionService.createTransaction(request));
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(RecordNotFoundException.class)
	public ProblemDetail recordNotFoundException(RecordNotFoundException ex) {
		return buildProblemDetail(HttpStatus.NOT_FOUND, "Record not found", ex.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(TransactionsException.class)
	public ProblemDetail transactionsException(TransactionsException ex) {
		return buildProblemDetail(HttpStatus.BAD_REQUEST, "Transaction error", ex.getMessage());
	}

	private ProblemDetail buildProblemDetail(HttpStatus status, String title, String detail) {
		ProblemDetail problemDetail = ProblemDetail.forStatus(status);
		problemDetail.setTitle(title);
		problemDetail.setDetail(detail);

		return problemDetail;
	}

}
