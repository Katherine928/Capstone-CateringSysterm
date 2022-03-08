package com.techelevator;


import com.techelevator.data.CateringInventory;
import com.techelevator.data.CustomerBalance;
import com.techelevator.view.UserInterface;

public class CateringSystemCLI {

    private CateringInventory cateringInventory = new CateringInventory();

    public static void main(String[] args) {
        CateringSystemCLI app = new CateringSystemCLI();
        app.run();
    }

    public void run() {
        CustomerBalance customerBalance = new CustomerBalance();
        UserInterface userInterface = new UserInterface();

        while (true) {
            String userChoice = userInterface.mainMenu();
            if (userChoice.equals("1")) {
                userInterface.displayCateringItems(cateringInventory.retrieveCateringItems());
            } else if (userChoice.equals("2")) {
                while (true) {
                    String userChoice2 = userInterface.subMenu(customerBalance);
                    if (userChoice2.equals("1")) {
                        String userInput = userInterface.getUserInput("Please Enter the whole dollar amount : ");
                        customerBalance.checkMoney(userInput);
                    } else if (userChoice2.equals("2")) {
                        userInterface.displayCateringItems(cateringInventory.retrieveCateringItems());
                        cateringInventory.order(customerBalance);
                    } else if (userChoice2.equals("3")) {
                        userInterface.printReportScreen(cateringInventory.getCustomerOrderList());
                        userInterface.printStatusMessage(cateringInventory.completeTransaction());
                        break;
                    }
                }
            } else if (userChoice.equals("3")) {
                userInterface.goodByeMessage();
                break;
            } else {
                String message = "Please enter a valid choice";
                userInterface.printStatusMessage(message);
            }
        }
    }
}
