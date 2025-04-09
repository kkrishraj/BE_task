Deliverable:

You can choose the programming language you want to use however it would be preferrable if we can see the solution in Java or Scala and
MySQL or Postgres as database.

Description:
As mentioned above, the exercise should satisfy the following scenario:
A user has an account at MarlowBank and should be able to control their account through an ATM. The end goal of this project is to:
Allow users to deposit and withdraw money from an ATM (Keep in mind withdrawal limitations - no overdraft allowed)
Allow simultaneous access of an account (joint accounts), so it's important to check the order of withdrawals (balance should never fall
below 0)

Requirements:

An application that handles and covers the scenario above satisfying both points 1 + 2 through an api layer
All database scripts for the creation of the tables/indices/foreign keys etc.
Readme instructions for running the application end to end (including setting up the database)

Bonus:

Based on every update in account balance, the above application should push the relevant change (deposit/withdrawal) in a queue system
(preferably but not restrictive - Kafka). Another application should be implementeded that processes that queue and pushes the updates in
a "change log" table for auditing purposes. -  Tried with Kafka but need to do setup for kafka server to establish connection

Dockerized solution (steps to create dockerfile, instructions)

Logging  -  Done 

------------------------------------------------------------------------------------

DB Scripts:

CREATE TABLE user_account_details (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    mobile_no VARCHAR(100) UNIQUE NOT NULL,
    balance DECIMAL(15,2) NOT NULL DEFAULT 0.00
);


CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT,
    type VARCHAR(10),
    amount DECIMAL(15,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES user_account_details(id)
);


CREATE TABLE auditLog (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    message TEXT NOT NULL,
    received_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);




initial insertion for user account details:
SQL Query: insert into user_account_details(name,mobile_no,balance) values ("Krishnan","9629900354",100); accountId is 1 for testing.

-------------------------------------------------------------------------------------




Postman testing details:

localhost:8091/api/atm/deposit

request payload: {"accountId":1,"amount":100}

localhost:8091/api/atm/withdraw

request payload: {"accountId":1,"amount":100}

success response for deposit:
{
    "status": true,
    "message": "Deposit successful"
}


success response for withdraw:
{
    "status": true,
    "message": "Withdrawal successful"
}

invalid accountId request payload:
{"accountId":12,"amount":100}

failure response for deposit/withdraw(invalid accountId):
{
    "status": false,
    "message": "invalid accountId"
}

invalid accountId request payload:
{"accountId":1,"amount":-100}

failure response for deposit/withdraw(invalid accountId):
{
    "status": false,
    "message": "invalid amount"
}


failure response for withdraw(Insufficient funds):
{
    "status": false,
    "message": "Insufficient funds"
}
