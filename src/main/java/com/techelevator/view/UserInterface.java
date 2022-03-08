package com.techelevator.view;

import com.techelevator.data.CateringItem;
import com.techelevator.data.CustomerBalance;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private Scanner scanner = new Scanner(System.in);
    private CustomerBalance customerBalance;


    // Print out the main menu for user to select
    public String mainMenu() {

        System.out.println("*****************************************");
        System.out.println("****  Weyland Corporation  Catering  ****");
        System.out.println("*****************************************\n");

        System.out.println("(1) Display catering Items");
        System.out.println("(2) Order");
        System.out.println("(3) Quit \n");

        System.out.print("Please select a choice from the above menu: ");
        String choice = scanner.nextLine();
        return choice;
    }


    // Print out sub menu when user choose "(2) Order"
    public String subMenu(CustomerBalance customerBalance) {

        System.out.println("------------------------------------------------------\n");
        System.out.println("(1) Add Money");
        System.out.println("(2) Select Products");
        System.out.println("(3) Complete Transaction");
        System.out.println("Current Account Balance: " + customerBalance.getCurrentBalance() + "\n");
        System.out.print("Please select a choice from the above menu: ");
        String choice = scanner.nextLine();
        return choice;
    }


    // Showing the list of product for user to select when they choose "(2) Select Products" in sub menu
    public void displayCateringItems(List<CateringItem> cateringItems) {
        System.out.printf("%-15s %-30s %-15s %-15s %n", "Product Code", "Description", "Qty", "Price");
        System.out.print("--------------------------------------------------------------------------\n\n");
        for (CateringItem item : cateringItems) {
            printCateringItem(item);
        }
    }

    // Print out customer's orders after they complete the transaction
    public void printReportScreen(List<String> customerOrderList) {
        for (String order : customerOrderList) {
            System.out.println(order);
        }
    }

    // Mark the item as "sold out" when the quantity is 0
    public void printCateringItem(CateringItem item) {
        if (item != null) {
            if (item.getQuantity() == 0) {
                String message = "SOLD OUT";
                System.out.printf("%-15s %-30s %-15s $%-15s %n", item.getProductCode(), item.getProductName(), message, item.getProductPrice());
            } else {
                System.out.printf("%-15s %-30s %-15s $%-15s %n", item.getProductCode(), item.getProductName(), item.getQuantity(), item.getProductPrice());
            }
        }
    }

    // Get the answers from users every time when need to ask user question
    public String getUserInput(String message) {
        System.out.print(message);
        String userInput = scanner.nextLine();
        return userInput;
    }

    // Print out the message to user
    public void printStatusMessage(String message) {
        System.out.println(message);
    }

    // Print out the goodbye message to user when they complete the transaction
    public void goodByeMessage() {
        System.out.println("Thank you for Shopping with Weyland Corporation. Have a great day !");
    }
}


