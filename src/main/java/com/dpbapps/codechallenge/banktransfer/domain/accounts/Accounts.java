package com.dpbapps.codechallenge.banktransfer.domain.accounts;

import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
public class Accounts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "account_number")
	private String accountNumber;

}
