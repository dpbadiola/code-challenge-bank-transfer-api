package com.dpbapps.codechallenge.banktransfer.domain.transactions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

 import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, UUID> {

	List<Transactions> findByAccountId(Long accountId);

}
