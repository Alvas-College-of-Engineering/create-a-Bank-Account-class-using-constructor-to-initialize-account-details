package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class BankGUI extends JFrame implements ActionListener {

    JTextField nameField, balanceField;

    JTextField accField, amountField;

    JTextField fromField, toField, transferAmountField;

    JTextArea output;

    JButton createBtn, depositBtn,
            withdrawBtn, transferBtn,
            detailsBtn;

    BankOperations operations =
            new BankOperations();

    public BankGUI() {

        setTitle("Bank Account Management System");

        setSize(950, 700);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout(15, 15));

        getContentPane().setBackground(
                new Color(240, 244, 248)
        );

        // ================= HEADER =================

        JLabel title =
                new JLabel(
                        "BANK ACCOUNT MANAGEMENT SYSTEM"
                );

        title.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        title.setForeground(
                new Color(25, 50, 90)
        );

        title.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        title.setBorder(
                new EmptyBorder(20, 10, 20, 10)
        );

        add(title, BorderLayout.NORTH);

        // ================= MAIN PANEL =================

        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(
                new GridLayout(2, 2, 20, 20)
        );

        mainPanel.setBorder(
                new EmptyBorder(20, 20, 20, 20)
        );

        mainPanel.setBackground(
                new Color(240, 244, 248)
        );

        // ================= CREATE ACCOUNT PANEL =================

        JPanel createPanel =
                createSectionPanel("Create Account");

        createPanel.setLayout(
                new GridLayout(3, 2, 10, 10)
        );

        createPanel.add(new JLabel("Account Holder Name"));

        nameField = new JTextField();

        createPanel.add(nameField);

        createPanel.add(new JLabel("Initial Balance"));

        balanceField = new JTextField();

        createPanel.add(balanceField);

        createBtn = createButton("Create Account");

        createPanel.add(createBtn);

        // ================= DEPOSIT/WITHDRAW PANEL =================

        JPanel transactionPanel =
                createSectionPanel("Deposit / Withdraw");

        transactionPanel.setLayout(
                new GridLayout(4, 2, 10, 10)
        );

        transactionPanel.add(new JLabel("Account Number"));

        accField = new JTextField();

        transactionPanel.add(accField);

        transactionPanel.add(new JLabel("Amount"));

        amountField = new JTextField();

        transactionPanel.add(amountField);

        depositBtn = createButton("Deposit");

        withdrawBtn = createButton("Withdraw");

        transactionPanel.add(depositBtn);

        transactionPanel.add(withdrawBtn);

        // ================= TRANSFER PANEL =================

        JPanel transferPanel =
                createSectionPanel("Transfer Money");

        transferPanel.setLayout(
                new GridLayout(4, 2, 10, 10)
        );

        transferPanel.add(new JLabel("From Account"));

        fromField = new JTextField();

        transferPanel.add(fromField);

        transferPanel.add(new JLabel("To Account"));

        toField = new JTextField();

        transferPanel.add(toField);

        transferPanel.add(new JLabel("Transfer Amount"));

        transferAmountField = new JTextField();

        transferPanel.add(transferAmountField);

        transferBtn = createButton("Transfer");

        transferPanel.add(transferBtn);

        // ================= DETAILS PANEL =================

        JPanel detailsPanel =
                createSectionPanel("Account Details");

        detailsPanel.setLayout(
                new BorderLayout()
        );

        detailsBtn =
                createButton("Show Account Details");

        detailsPanel.add(detailsBtn,
                BorderLayout.NORTH);

        // ================= ADD PANELS =================

        mainPanel.add(createPanel);

        mainPanel.add(transactionPanel);

        mainPanel.add(transferPanel);

        mainPanel.add(detailsPanel);

        add(mainPanel, BorderLayout.CENTER);

        // ================= OUTPUT AREA =================

        output = new JTextArea();

        output.setEditable(false);

        output.setFont(
                new Font("Monospaced", Font.PLAIN, 16)
        );

        output.setMargin(
                new Insets(15, 15, 15, 15)
        );

        JScrollPane scrollPane =
                new JScrollPane(output);

        scrollPane.setPreferredSize(
                new Dimension(900, 220)
        );

        scrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(
                                Color.GRAY
                        ),
                        "System Output"
                )
        );

        add(scrollPane, BorderLayout.SOUTH);

        // ================= EVENTS =================

        createBtn.addActionListener(this);

        depositBtn.addActionListener(this);

        withdrawBtn.addActionListener(this);

        transferBtn.addActionListener(this);

        detailsBtn.addActionListener(this);

        setVisible(true);
    }

    // ================= PANEL STYLE =================

    private JPanel createSectionPanel(String title) {

        JPanel panel = new JPanel();

        panel.setBackground(Color.WHITE);

        panel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(
                                new Color(150, 150, 150)
                        ),
                        title
                )
        );

        return panel;
    }

    // ================= BUTTON STYLE =================

    private JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        button.setBackground(
                new Color(52, 120, 246)
        );

        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);

        return button;
    }

    // ================= ACTIONS =================

    @Override
    public void actionPerformed(ActionEvent e) {

        try {

            // ===== CREATE ACCOUNT =====

            if (e.getSource() == createBtn) {

                String name =
                        nameField.getText();

                if (name.isEmpty()) {

                    output.setText(
                            "Please Enter Name"
                    );

                    return;
                }

                double balance =
                        Double.parseDouble(
                                balanceField.getText()
                        );

                if (balance < 0) {

                    output.setText(
                            "Invalid Balance"
                    );

                    return;
                }

                BankAccount acc =
                        new BankAccount(
                                name,
                                balance
                        );

                int accNo =
                        operations.createAccount(
                                name,
                                balance
                        );

                output.setText(
                        "=================================\n"
                                + " ACCOUNT CREATED SUCCESSFULLY\n"
                                + "=================================\n\n"
                                + "Account Number : "
                                + accNo
                                + "\n\nAccount Holder : "
                                + name
                                + "\n\nBalance : ₹"
                                + balance

                );
            }

            // ===== DEPOSIT =====

            if (e.getSource() == depositBtn) {

                int accNo =
                        Integer.parseInt(
                                accField.getText()
                        );

                double amount =
                        Double.parseDouble(
                                amountField.getText()
                        );

                boolean status =
                        operations.deposit(
                                accNo,
                                amount
                        );

                if (status) {

                    output.setText(
                            "=================================\n"
                                    + " AMOUNT DEPOSITED SUCCESSFULLY\n"
                                    + "================================="
                    );

                } else {

                    output.setText(
                            "Deposit Failed"
                    );
                }
            }

            // ===== WITHDRAW =====

            if (e.getSource() == withdrawBtn) {

                int accNo =
                        Integer.parseInt(
                                accField.getText()
                        );

                double amount =
                        Double.parseDouble(
                                amountField.getText()
                        );

                boolean status =
                        operations.withdraw(
                                accNo,
                                amount
                        );

                if (status) {

                    output.setText(
                            "=================================\n"
                                    + " WITHDRAWAL SUCCESSFUL\n"
                                    + "================================="
                    );

                } else {

                    output.setText(
                            "Insufficient Balance OR Invalid Account"
                    );
                }
            }

            // ===== TRANSFER =====

            if (e.getSource() == transferBtn) {

                int from =
                        Integer.parseInt(
                                fromField.getText()
                        );

                int to =
                        Integer.parseInt(
                                toField.getText()
                        );

                double amount =
                        Double.parseDouble(
                                transferAmountField.getText()
                        );

                boolean status =
                        operations.transfer(
                                from,
                                to,
                                amount
                        );

                if (status) {

                    output.setText(
                            "=================================\n"
                                    + " MONEY TRANSFER SUCCESSFUL\n"
                                    + "================================="
                    );

                } else {

                    output.setText(
                            "Transfer Failed"
                    );
                }
            }

            // ===== SHOW DETAILS =====

            if (e.getSource() == detailsBtn) {

                if (accField.getText().isEmpty()) {

                    output.setText(
                            "Please Enter Account Number"
                    );

                    return;
                }

                int accNo =
                        Integer.parseInt(
                                accField.getText()
                        );

                String details =
                        operations.getAccountDetails(
                                accNo
                        );

                output.setText(details);
            }

        } catch (Exception ex) {

            output.setText(
                    "Invalid Input"
            );
        }
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        new BankGUI();
    }
}