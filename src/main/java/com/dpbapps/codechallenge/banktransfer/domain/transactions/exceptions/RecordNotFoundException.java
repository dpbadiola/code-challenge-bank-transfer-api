package com.dpbapps.codechallenge.banktransfer.domain.transactions.exceptions;

public class RecordNotFoundException extends RuntimeException {

	public RecordNotFoundException(String message) {
		super(message);
	}

}
