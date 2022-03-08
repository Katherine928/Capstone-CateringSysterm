package com.techelevator.data;


import com.techelevator.view.UserInterface;

import java.util.*;

public class CustomerBalance {

    private double currentBalance;
    private Logger logger = new Logger();
    private UserInterface userInterface = new UserInterface();


    // Add money to current balance
    public void addMoney(double money) {
        currentBalance += money;
    }


    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }


    // Check money the customer entered is valid or not
    public void checkMoney(String money) {
        Integer moneyEntered = Integer.parseInt(money);
        double moneyTOAdd = Double.parseDouble(money);
        double balanceBeforeTransaction = getCurrentBalance();
        List<Integer> moneyOption = new ArrayList<>(Arrays.asList(1, 5, 10, 20, 50, 100, 1499)); // Accepted money amount
        if (!moneyOption.contains(moneyEntered)) {
            String message = "ERROR: Please enter valid money amount ($1, $5, $10, $20, $50, $100)\n";
            userInterface.printStatusMessage(message);
        } else if ((moneyTOAdd + getCurrentBalance()) >= 1501) {
            String message = "Maximum Balance is 1500 . Please add correct amount or Select Product / Complete Transaction"; // Check if customer hit the $1500 limit
            userInterface.printStatusMessage(message);
        } else {
            addMoney(moneyTOAdd);  // if the money entered valid, add the amount to current balance
            logEvent("ADD MONEY", balanceBeforeTransaction); // Write the activity to the log file
        }
    }

    // Break down the bill the different type of money
    public Map<String, Integer> getChange() {
        int change = (int) (Math.ceil(currentBalance * 100));
        int dollars = Math.round((int) change / 100);
        change = change % 100;
        int twenties = dollars / 20;
        dollars = dollars % 20;
        int tens = dollars / 10;
        dollars = dollars % 10;
        int fives = dollars / 5;
        int ones = dollars % 5;
        int quarters = Math.round((int) change / 25);
        change = change % 25;
        int dimes = Math.round((int) change / 10);
        change = change % 10;
        int nickels = Math.round((int) change / 5);
        change = change % 5;
        int pennies = Math.round((int) change / 1);

        // Add different type of money and it's value after calculated into a map
        Map<String, Integer> changes = new LinkedHashMap<>();
        changes.put("Twenties", twenties);
        changes.put("Tens", tens);
        changes.put("Fives", fives);
        changes.put("Ones", ones);
        changes.put("Quarters", quarters);
        changes.put("Dimes", dimes);
        changes.put("Nickels", nickels);
        changes.put("Pennies", pennies);

        return changes;

    }

    // Write the behavior to the log file
    public void logEvent(String message, double balanceBeforeTransaction) {
        double balanceAfterTransaction = getCurrentBalance();
        logger.logEventMessage(message, balanceBeforeTransaction, balanceAfterTransaction);
    }


}
