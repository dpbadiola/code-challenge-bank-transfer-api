package com.dpbapps.codechallenge.banktransfer.domain.transactions;

import com.dpbapps.codechallenge.banktransfer.BaseControllerTest;
import com.dpbapps.codechallenge.banktransfer.domain.transactions.exceptions.RecordNotFoundException;
import com.dpbapps.codechallenge.banktransfer.domain.transactions.exceptions.TransactionsException;
import com.dpbapps.codechallenge.banktransfer.domain.transactions.dto.TransactionRequest;
import com.dpbapps.codechallenge.banktransfer.domain.transactions.dto.TransactionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionsController.class)
public class TransactionControllerTest extends BaseControllerTest {

	@Autowired
	private WebApplicationContext context;

	@MockitoBean
	private TransactionService transactionService;

	private ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
		mockMvc = setupMockMvc(context);
	}

	@Test
	void getTransaction() throws Exception {
		when(transactionService.getTransaction(any())).thenReturn(TransactionResponse.builder().build());

		mockMvc.perform(get("/api/transactions/{uuid}", UUID.randomUUID()))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$").exists())
		;
	}

	@Test
	void getTransaction_missingRecord() throws Exception {
		when(transactionService.getTransaction(any())).thenThrow(RecordNotFoundException.class);

		mockMvc.perform(get("/api/transactions/{uuid}", UUID.randomUUID()))
			.andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
			.andExpect(jsonPath("$").exists())
		;
	}

	@Test
	void createTransaction() throws Exception {
		when(transactionService.createTransaction(any(TransactionRequest.class))).thenReturn(UUID.randomUUID());

		TransactionRequest request = TransactionRequest.builder()
			.amount(BigDecimal.valueOf(100))
			.build();
		mockMvc.perform(post("/api/transactions/transfer")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request))
			)
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$").exists())
		;
	}

	@Test
	void createTransaction_error() throws Exception {
		when(transactionService.createTransaction(any(TransactionRequest.class))).thenThrow(TransactionsException.class);

		TransactionRequest request = TransactionRequest.builder()
			.amount(BigDecimal.valueOf(100))
			.build();
		mockMvc.perform(post("/api/transactions/transfer")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(request))
			)
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
			.andExpect(jsonPath("$").exists())
		;
	}

}
