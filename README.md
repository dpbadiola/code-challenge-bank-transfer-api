# Bank Transfer API

A coding challenge. Tasked to create an API for ORANJE Bank following the business requirements.

## Requirements

* Create Transfer API where customers can transfer money to another account
    * Has three [transaction types](#transaction-types)
    * Confirm to the [API specification](#api-specification)
* Return appropriate response and error codes for each endpoint
* Supply unit and/or integration tests as needed to ensure functionalities are working as expected
* (Optional) Can use any local database (including in-memory databases)

### [Transaction Types](#transaction-types)

| Type     | Sender Bank | Recipient Bank | Transaction Limit | Fee         | Posting Date   |
|----------|-------------|----------------|-------------------|-------------|----------------|
| Internal | ORANJE      | ORANJE         | 250,000.00        | 0.00 (Free) | Real-time      |
| InstaNet | ORANJE      | BLU Bank       | 50,000.00         | 25.00       | Real-time      |
| PesoPay  | ORANJE      | BLU Bank       | 100,000.00        | 50.00       | 3 Banking days |

_NOTE: Uppercase letters on the bank details are the bank codes_

### [API specification](#api-specification)

Create an endpoint the accepts the following

| Field                      | Type        | Required?     | Additional Details                                     | 
|----------------------------|-------------|---------------|--------------------------------------------------------|
| `sender_account_number`    | string      | YES           |                                                        |
| `recipient_account number` | string      | YES           |                                                        |
| `recipient_bank_code`      | string      | NO (Optional) | internal transfer is set to `null`, otherwise external |
| `transfer_amount`          | big decimal | YES           |                                                        |
| `transfer_type`            | string      | YES           |                                                        |

## Solution

### Technologies

* Java 21
* Lombok
* SpringBoot 3
  * JPA, JDBC
  * Hibernate (Validation)
* H2
* Gradle
* IntelliJ

## Installation

### Pre-requisite

* Java 21

### Procedure

TODO
