package com.techelevator.data;

import com.techelevator.view.UserInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CateringInventory {
    private Scanner scanner;
    private double totalItemPrice = 0.0;
    private Map<String, CateringItem> cateringInventory;
    private UserInterface userInterface = new UserInterface();
    private List<String> customerOrderList = new ArrayList<>();
    private Logger logger = new Logger();
    private CustomerBalance customerBalanceReference;


    public CateringInventory() {
        cateringInventory = new TreeMap<>();
        loadCateringInventory();
    }

    public List<CateringItem> retrieveCateringItems() {
        List<CateringItem> listOfCateringItems = new ArrayList<>();

        for (String item : cateringInventory.keySet()) {
            listOfCateringItems.add(cateringInventory.get(item));
        }

        return listOfCateringItems;
    }

    public void loadCateringInventory() {
        File file = new File("cateringsystem.csv");
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split("\\|");
            CateringItem item = new CateringItem(line[0], line[1], line[2], Double.parseDouble(line[3]));
            cateringInventory.put(line[1], item);
        }
    }

    public void order(CustomerBalance customerBalance) {
        customerBalanceReference = customerBalance;
        while (true) {
            String productSelectedCode = userInterface.getUserInput("Please select product from the list above: ");
            CateringItem cateringItem = cateringInventory.get(productSelectedCode);
            double balanceBeforeTransaction = customerBalance.getCurrentBalance();
            if (balanceBeforeTransaction == 0.0) {
                userInterface.printStatusMessage("Balance is 0.0. Please add money before selecting product");
                break;
            } else if (cateringItem == null) {
                userInterface.printStatusMessage("The product code just selected does not exist, please try again!");
                break;
            }
            int quantitySelected = Integer.parseInt(userInterface.getUserInput("How many do you want? : "));
            double productPrice = cateringItem.getProductPrice();
            double totalProductPrice = quantitySelected * cateringItem.getProductPrice();
            if (cateringItem.getQuantity() <= 0) {
                userInterface.printStatusMessage("The item is sold out, please try again!");
                break;
            } else if (quantitySelected > cateringItem.getQuantity()) {
                userInterface.printStatusMessage("Insufficient stock. Please come back later!");
                break;
            } else if (Double.compare(totalProductPrice, customerBalance.getCurrentBalance()) > 0) {
                userInterface.printStatusMessage("There is not enough money to purchase, please come back with more money!");
                break;
            } else {
                totalItemPrice += totalProductPrice;
                cateringItem.setQuantity(cateringItem.getQuantity() - quantitySelected);
                customerBalanceReference.setCurrentBalance(customerBalanceReference.getCurrentBalance() - totalProductPrice);
                customerReportFormat(cateringItem, quantitySelected, balanceBeforeTransaction, totalProductPrice);
                break;
            }

        }
    }

    public List<String> codeName(String codeKey) {
        Map<String, List<String>> codeMap = new HashMap<>();
        codeMap.put("B", Arrays.asList("Beverage", "Don't forget Ice."));
        codeMap.put("E", Arrays.asList("Entree", "Did you remember Dessert? "));
        codeMap.put("A", Arrays.asList("Appetizers", "You might need extra plates."));
        codeMap.put("D", Arrays.asList("Dessert", "Coffee goes with dessert."));
        return codeMap.get(codeKey);
    }


    public void customerReportFormat(CateringItem cateringItem, int quantity, double balanceBeforeTransaction, double totalProductPrice) {
        String productCode = cateringItem.getType();
        List<String> getNameAndMessage = codeName(productCode);
        String reportFormat = String.format(" %-5s %-10s $%-10s $%-10s %-1s ", quantity, getNameAndMessage.get(0), cateringItem.getProductPrice(), totalProductPrice, getNameAndMessage.get(1));
        String logMessageFormat = String.format("%-1s %-1s %-1s", quantity, cateringItem.getProductName(), cateringItem.getProductCode());
        customerOrderList.add(reportFormat);
        logger.logEventMessage(logMessageFormat, balanceBeforeTransaction, customerBalanceReference.getCurrentBalance());
    }


    public List<String> getCustomerOrderList() {
        return customerOrderList;
    }

    public String completeTransaction() {
        String balanceReturnMessage = "Total: " + "$" + totalItemPrice + "\n";
        Map<String, Integer> map = customerBalanceReference.getChange();
        balanceReturnMessage += "You received ";
        for (Map.Entry<String, Integer> item : map.entrySet()) {
            if (item.getValue() != 0) {
                String message = "(" + item.getValue() + ")" + item.getKey() + " ";
                balanceReturnMessage += message;
            }
        }

        balanceReturnMessage += "in change. \n";
        logger.logEventMessage("GIVE CHANGE", customerBalanceReference.getCurrentBalance(), 0.0);
        customerBalanceReference.setCurrentBalance(0.0);
        return balanceReturnMessage;

    }

}







