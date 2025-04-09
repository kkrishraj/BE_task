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



initial insertion for user account details:
SQL Query: insert into user_account_details(name,mobile_no,balance) values ("Krishnan","9629900354",100); accountId is 1 for testing.


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


failure response for deposit/withdraw(invalid accountId):
{
    "status": false,
    "message": "invalid accountId"
}

failure response for withdraw(Insufficient funds):
{
    "status": false,
    "message": "Insufficient funds"
}
