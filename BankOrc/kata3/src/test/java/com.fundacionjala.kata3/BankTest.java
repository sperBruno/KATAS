package com.fundacionjala.kata3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

import static com.fundacionjala.kata3.Bank.calculateSum;
import static com.fundacionjala.kata3.Bank.getFinaLNumber;
import static com.fundacionjala.kata3.Bank.getNumber;
import static com.fundacionjala.kata3.Bank.printEntriesFromStringArray;
import static com.fundacionjala.kata3.Bank.verifyAccoutNumber;
import static com.fundacionjala.kata3.Bank.writeResult;

/**
 * Created by Bruno on 7/7/2016.
 */
public class BankTest {

    @Test
    public void readNumeberTest() {
        Bank.read("C:\\numbers.txt");
        getNumber(0);
        String expectedResult = "49086771?";
        Assert.assertTrue(expectedResult.equals(getFinaLNumber()));
        Assert.assertFalse(calculateSum());
    }

    @Test
    public void readFromStringArray() {
        String[] arrayString = new String[]{"    _  _     _  _  _  _  _ ",
                "  | _| _||_||_ |_   ||_||_|",
                "  ||_  _|  | _||_|  ||_| _|"};
        printEntriesFromStringArray(arrayString);
        getNumber(0);
        String expectedResult = "123456789";

        Assert.assertEquals(expectedResult, getFinaLNumber());
    }

    @Test
    public void verifyValidaAccount() {
        String[] arrayString = new String[]{"    _  _     _  _  _  _  _ ",
                "  | _| _||_||_ |_   ||_||_|",
                "  ||_  _|  | _||_|  ||_| _|"};
        printEntriesFromStringArray(arrayString);
        getNumber(0);
        Assert.assertTrue(calculateSum());
    }

    @Test
    public void verifyOutPut() {
        String[] arrayString = new String[]{"    _  _     _  _  _  _  _ ",
                "  | _| _||_||_ |_   ||_||_|",
                "  ||_  _|  | _||_|  ||_| _|"};
        printEntriesFromStringArray(arrayString);
        getNumber(0);
        calculateSum();
        verifyAccoutNumber();
        writeResult();
        String expectedResult = "123456789";
        String actualResult = "";
        try {
            Scanner file = new Scanner(new File("C:\\outputs.txt"));
            while (file.hasNext()) {
                actualResult = file.nextLine().toString();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(expectedResult, actualResult);
    }
}
