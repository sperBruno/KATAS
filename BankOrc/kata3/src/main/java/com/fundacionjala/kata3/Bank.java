package com.fundacionjala.kata3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.fundacionjala.kata3.Constant.ZERO;
import static com.fundacionjala.kata3.Constant.ONE;
import static com.fundacionjala.kata3.Constant.THREE;
import static com.fundacionjala.kata3.Constant.TWO;
import static com.fundacionjala.kata3.Constant.FOUR;
import static com.fundacionjala.kata3.Constant.FIVE;
import static com.fundacionjala.kata3.Constant.SIX;
import static com.fundacionjala.kata3.Constant.SEVEN;
import static com.fundacionjala.kata3.Constant.EIGHT;
import static com.fundacionjala.kata3.Constant.NINE;

/**
 * Created by Bruno on 7/7/2016.
 */
public class Bank {

    private static char[][] VALUES = new char[3][28];

    private static final List<String> towrite = new ArrayList<String>();

    private static String resultNumber = "";

    private static List<char[][]> listOfValues = new ArrayList<char[][]>();

    private static int sum;

    public static void read(String path) {
        try {
            Scanner file = new Scanner(new File(path));
            printEntries(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void printEntries(Scanner file) {
        int index = 0;
        while (file.hasNext()) {
            char[] chars = file.nextLine().toCharArray();
            for (int i = 0; i < chars.length; i++) {
                VALUES[index][i] = chars[i];
            }
            index++;
            listOfValues.add(VALUES);
        }
    }

    public static void printEntriesFromStringArray(String[] arrayString) {
        VALUES = new char[3][29];
        resultNumber = "";
        int index = 0;
        for (String line : arrayString) {
            char[] chars = line.toCharArray();
            System.out.println(chars.length);
            for (int i = 0; i < chars.length; i++) {
                VALUES[index][i] = chars[i];
            }
            index++;
            listOfValues.add(VALUES);
        }
    }


    public static void getNumber(int numbers) {
        String insertedNumber = "";
        int limitColum = ((numbers) * 3) + 3;
        if (numbers < 9) {
            for (int row = 0; row < 3; row++) {
                int i = numbers * 3;
                for (int column1 = i; column1 < limitColum; column1++) {
                    System.out.print(VALUES[row][column1]);
                    insertedNumber += VALUES[row][column1];
                }
                System.out.println();
            }
            matchingNumber(insertedNumber);
            getNumber(numbers + 1);
        }
    }

    public static void matchingNumber(String number) {
        if (ZERO.equals(String.valueOf(number))) {
            resultNumber += 0;
        } else if (ONE.equals(String.valueOf(number))) {
            resultNumber += 1;
        } else if (TWO.equals(String.valueOf(number))) {
            resultNumber += 2;
        } else if (THREE.equals(String.valueOf(number))) {
            resultNumber += 3;
        } else if (FOUR.equals(String.valueOf(number))) {
            resultNumber += 4;
        } else if (FIVE.equals(String.valueOf(number))) {
            resultNumber += 5;
        } else if (SIX.equals(String.valueOf(number))) {
            resultNumber += 6;
        } else if (SEVEN.equals(String.valueOf(number))) {
            resultNumber += 7;
        } else if (EIGHT.equals(String.valueOf(number))) {
            resultNumber += 8;
        } else if (NINE
                .equals(String.valueOf(number))) {
            resultNumber += 9;
        } else {
            resultNumber += "?";
        }
    }


    public static boolean calculateSum() {
        int constantValue = 1;
        boolean valid = false;
        sum = 0;
        if (resultNumber.contains("?")) {
            System.out.println("invalid Number");
            towrite.add(resultNumber + " ILL");
        } else {
            char[] arrayNumbers = resultNumber.toCharArray();
            for (int i = arrayNumbers.length - 1; i >= 0; i--) {
                sum += (Integer.parseInt(String.valueOf(arrayNumbers[i])) * constantValue);
                constantValue++;
            }
            if (sum % 11 == 0) {
                valid = true;
            }
        }
        return valid;
    }

    public static void verifyAccoutNumber() {
        if (calculateSum()) {
            System.out.println(resultNumber + " is a valid Account number");
            towrite.add(resultNumber);
        } else {
            System.out.println(resultNumber + " is an invalid Account number");
            towrite.add(resultNumber + " ERR");
        }
    }

    public static String getFinaLNumber() {
        return resultNumber;
    }

    public static void writeResult() {
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("C:\\outputs.txt"), "utf-8"));

            writer.write(towrite.get(1));

            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
//        read("c:\\numbers.txt");
//        getNumber(0);
//        System.out.println("number");
//        System.out.println(getFinaLNumber());
//        verifyAccoutNumber();
//        writeResult();
        System.out.println(165 % 11 == 0);
    }
}

