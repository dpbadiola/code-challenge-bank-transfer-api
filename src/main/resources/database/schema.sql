DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS accounts;

CREATE TABLE IF NOT EXISTS accounts
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_number CHARACTER(50) UNIQUE
);
CREATE TABLE IF NOT EXISTS transactions
(
    id                UUID           NOT NULL PRIMARY KEY,
    create_date       TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    posting_date      TIMESTAMP      NOT NULL,
    debit             DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
    credit            DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
    account_id        BIGINT         NOT NULL,
    transfer_type     CHARACTER(50),
    transfer_fee      DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
    transfer_metadata JSON,
    ending_balance    DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
    CONSTRAINT fk_transactions_account_id FOREIGN KEY (account_id) REFERENCES accounts (id)
);
