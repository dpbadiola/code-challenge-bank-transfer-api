INSERT INTO accounts(id, account_number)
VALUES (1, 'ORANJE00001234');
INSERT INTO accounts(id, account_number)
VALUES (2, 'ORANJE00005678');

INSERT INTO transactions(id,
                         create_date,
                         posting_date,
                         debit,
                         credit,
                         account_id,
                         transfer_type,
                         transfer_fee,
                         transfer_metadata,
                         ending_balance)
VALUES ('00000000-0000-0000-0000-000000000000',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP(),
        0.00,
        1000.00,
        1,
        null,
        0.00,
        null,
        1000.00);
