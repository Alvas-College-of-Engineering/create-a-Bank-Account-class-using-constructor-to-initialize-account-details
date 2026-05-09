
# Bank Account Management System

A simple Java-based Bank Account Management System using Java Swing, JDBC, and MySQL.

## Features

- Create Bank Account
- Deposit Money
- Withdraw Money
- Transfer Money
- View Account Details

## Technologies Used

- Java
- Java Swing
- JDBC
- MySQL

## Project Structure

```text
src/
 └── org.example
      ├── BankAccount.java
      ├── DBConnection.java
      ├── BankOperations.java
      └── BankGUI.java
```

## Database Setup

```sql
CREATE DATABASE bankdb;

USE bankdb;

CREATE TABLE accounts (
    account_number INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    balance DOUBLE
);
```

## JDBC Connection

Update username and password in:

```java
DBConnection.java
```

## Run the Project

1. Open project in IntelliJ IDEA or Eclipse
2. Configure MySQL database
3. Run `BankGUI.java`

## Author

Abhishek Itagi
