package org.example;

import java.sql.*;

public class BankOperations {

    Connection con = DBConnection.getConnection();

    // ===== CREATE ACCOUNT =====

    public int createAccount(String name, double balance) {

        int generatedAccNo = -1;

        try {

            String query =
                    "INSERT INTO accounts(name, balance) VALUES(?, ?)";

            PreparedStatement ps =
                    con.prepareStatement(
                            query,
                            Statement.RETURN_GENERATED_KEYS
                    );

            ps.setString(1, name);

            ps.setDouble(2, balance);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {

                generatedAccNo = rs.getInt(1);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return generatedAccNo;
    }

    // ===== DEPOSIT =====

    public boolean deposit(int accNo, double amount) {

        try {

            if (amount <= 0) {

                return false;
            }

            String checkQuery =
                    "SELECT * FROM accounts WHERE account_number=?";

            PreparedStatement checkPs =
                    con.prepareStatement(checkQuery);

            checkPs.setInt(1, accNo);

            ResultSet rs = checkPs.executeQuery();

            if (!rs.next()) {

                return false;
            }

            String query =
                    "UPDATE accounts SET balance = balance + ? WHERE account_number=?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setDouble(1, amount);

            ps.setInt(2, accNo);

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // ===== WITHDRAW =====

    public boolean withdraw(int accNo, double amount) {

        try {

            if (amount <= 0) {

                return false;
            }

            String checkQuery =
                    "SELECT balance FROM accounts WHERE account_number=?";

            PreparedStatement checkPs =
                    con.prepareStatement(checkQuery);

            checkPs.setInt(1, accNo);

            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {

                double currentBalance =
                        rs.getDouble("balance");

                if (currentBalance < amount) {

                    return false;
                }

                String query =
                        "UPDATE accounts SET balance = balance - ? WHERE account_number=?";

                PreparedStatement ps =
                        con.prepareStatement(query);

                ps.setDouble(1, amount);

                ps.setInt(2, accNo);

                int rows = ps.executeUpdate();

                return rows > 0;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // ===== TRANSFER =====

    public boolean transfer(int fromAcc, int toAcc, double amount) {

        try {

            if (amount <= 0) {

                return false;
            }

            con.setAutoCommit(false);

            boolean withdrawSuccess =
                    withdraw(fromAcc, amount);

            if (!withdrawSuccess) {

                con.rollback();

                return false;
            }

            boolean depositSuccess =
                    deposit(toAcc, amount);

            if (!depositSuccess) {

                con.rollback();

                return false;
            }

            con.commit();

            con.setAutoCommit(true);

            return true;

        } catch (Exception e) {

            try {

                con.rollback();

            } catch (Exception ex) {

                ex.printStackTrace();
            }

            e.printStackTrace();
        }

        return false;
    }

    // ===== ACCOUNT DETAILS =====

    public String getAccountDetails(int accNo) {

        try {

            String query =
                    "SELECT * FROM accounts WHERE account_number=?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, accNo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return
                        "=================================\n"
                                + "      ACCOUNT DETAILS\n"
                                + "=================================\n\n"
                                + "Account Number : "
                                + rs.getInt("account_number")
                                + "\n\nName : "
                                + rs.getString("name")
                                + "\n\nBalance : ₹"
                                + rs.getDouble("balance")
                                + "\n\n=================================";
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return "Account Not Found";
    }
}