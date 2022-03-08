package com.techelevator.data;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    // Get the cruuent date and time
    public String getCurrentDateAndTime() {
        String currentDateAndTime = new SimpleDateFormat("MM/dd/yy hh:mm:ss a").format(new Date());
        return currentDateAndTime;
    }


    // Write formatted file to the document every time calls the method
    public void logEventMessage(String message, double balanceBeforeTransaction, double balanceAfterTransaction) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("log.txt"), true))) {
            String logMessageFormat = String.format(" %-20s %-20s $%-20s $%-20s", getCurrentDateAndTime(), message, balanceBeforeTransaction, balanceAfterTransaction);
            bufferedWriter.write(logMessageFormat);
            bufferedWriter.write(System.lineSeparator());
            bufferedWriter.write(System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
