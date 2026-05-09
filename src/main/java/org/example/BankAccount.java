package org.example;



public class BankAccount {

    private int accountNumber;
    private String name;
    private double balance;

    static int totalAccounts = 0;

    // Constructor
    public BankAccount(String name, double balance) {

        this.name = name;
        this.balance = balance;

        totalAccounts++;
    }

    // Getters

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public static int getTotalAccounts() {
        return totalAccounts;
    }
}