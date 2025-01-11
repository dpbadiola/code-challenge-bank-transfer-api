package com.dpbapps.codechallenge.banktransfer.domain.error;

import com.dpbapps.codechallenge.banktransfer.domain.error.exceptions.RecordNotFoundException;
import com.dpbapps.codechallenge.banktransfer.domain.transactions.exceptions.TransactionsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(RecordNotFoundException.class)
	public ProblemDetail recordNotFoundException(RecordNotFoundException ex) {
		return buildProblemDetail(HttpStatus.NOT_FOUND, "Record not found", ex.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(TransactionsException.class)
	public ProblemDetail transactionsException(TransactionsException ex) {
		return ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
	}

	private ProblemDetail buildProblemDetail(HttpStatus status, String title, String detail) {
		ProblemDetail problemDetail = ProblemDetail.forStatus(status);
		problemDetail.setTitle(title);
		problemDetail.setDetail(detail);

		return problemDetail;
	}

}
