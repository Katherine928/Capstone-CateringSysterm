package com.techelevator;

import com.techelevator.data.CustomerBalance;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomerBalanceTest {

    @Test
   public void addMoneyTest() {
        CustomerBalance customerBalance = new CustomerBalance();
        customerBalance.addMoney(10);
        Assert.assertEquals(10,customerBalance.getCurrentBalance(),1);
    }



    @Test
    public void getChangeTest() {
        CustomerBalance customerBalance = new CustomerBalance();
        customerBalance.setCurrentBalance(55.50);
        Map<String, Integer> changes = new LinkedHashMap<>();
        changes.put("Twenties", 2);
        changes.put("Tens", 1);
        changes.put("Fives", 1);
        changes.put("Ones", 0);
        changes.put("Quarters", 2);
        changes.put("Dimes", 0);
        changes.put("Nickels", 0);
        changes.put("Pennies", 0);

        Assert.assertEquals(changes,customerBalance.getChange());
    }

}
