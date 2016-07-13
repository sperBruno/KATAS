package com.fundacionjala.bankocr;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Bruno on 7/7/2016.
 */
public class BankOCR {

    private static final String INPUT_FILE_NAME = "accountsOCR.txt";
    private static final int COLS_DIGITS = 3;
    private static final int ROWS_DIGITS = 3;
    private static final int NUMBER_DIGITS = 9;
    private static final String ILLEGIBLE_NUMBER = "?";
    private static final String ILLEGIBLE_MESSAGE = "ILLEGIBLE_MESSAGE";
    private static final String ERROR_MESSAGE = "ERROR_MESSAGE";

    private List<String> numberAccountsList;

    public BankOCR() {
        numberAccountsList = new ArrayList<String>();
    }

    public void ocrAccountToNumberAccount() throws IOException {
        List<String> ocrAccountsList = readAccountOcrFile(INPUT_FILE_NAME);

        for (int line = 0; line < ocrAccountsList.size(); line += ROWS_DIGITS + 1) {
            List<String> accountEntry = ocrAccountsList.subList(line, line + ROWS_DIGITS);
            String account = convertOcrAccountToNumberAccount(accountEntry.toArray(new String[ROWS_DIGITS]));
            addAccountToNumberAccountsList(account);
        }

        writeNumberAccountsOutputFile();
        
    }


    public List<String> readAccountOcrFile(String fileName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        String line;
        ArrayList ocrAccountsList = new ArrayList<String>();
        while ((line = bufferedReader.readLine()) != null) {
            ocrAccountsList.add(line);
        }
        fileInputStream.close();
        return ocrAccountsList;
    }

    public String convertOcrAccountToNumberAccount(String[] accountEntry) {
        StringBuilder accountNumber = new StringBuilder();
        for (int currentDigit = 0; currentDigit < NUMBER_DIGITS; currentDigit++) {
            String[] ocrDigit = new String[ROWS_DIGITS];
            int substringStartIndex = currentDigit * COLS_DIGITS;
            ocrDigit[0] = accountEntry[0].substring(substringStartIndex, substringStartIndex + COLS_DIGITS);
            ocrDigit[1] = accountEntry[1].substring(substringStartIndex, substringStartIndex + COLS_DIGITS);
            ocrDigit[2] = accountEntry[2].substring(substringStartIndex, substringStartIndex + COLS_DIGITS);
            accountNumber.append(parseOcrDigitToNumberDigit(ocrDigit));
        }
        return accountNumber.toString();
    }

    public String parseOcrDigitToNumberDigit(String[] ocrDigit) {
        if (Arrays.equals(ocrDigit, Digit.ZERO)) {
            return "0";
        } else if (Arrays.equals(ocrDigit, Digit.ONE)) {
            return "1";
        } else if (Arrays.equals(ocrDigit, Digit.TWO)) {
            return "2";
        } else if (Arrays.equals(ocrDigit, Digit.THREE)) {
            return "3";
        } else if (Arrays.equals(ocrDigit, Digit.FOUR)) {
            return "4";
        } else if (Arrays.equals(ocrDigit, Digit.FIVE)) {
            return "5";
        } else if (Arrays.equals(ocrDigit, Digit.SIX)) {
            return "6";
        } else if (Arrays.equals(ocrDigit, Digit.SEVEN)) {
            return "7";
        } else if (Arrays.equals(ocrDigit, Digit.EIGHT)) {
            return "8";
        } else if (Arrays.equals(ocrDigit, Digit.NINE)) {
            return "9";
        } else {
            return ILLEGIBLE_NUMBER;
        }
    }

    public void addAccountToNumberAccountsList(String numberAccount) {
        if (numberAccount.contains(ILLEGIBLE_NUMBER)) {
            numberAccountsList.add(String.format("%s %s", numberAccount, ILLEGIBLE_MESSAGE));
        } else if (isValidAccount(numberAccount)) {
            numberAccountsList.add(numberAccount);
        } else {
            numberAccountsList.add(String.format("%s %s", numberAccount, ERROR_MESSAGE));
        }
    }

    public boolean isValidAccount(String accountOut) {
        int checksum = 0;
        int digitPosition = 1;
        for (char digit : new StringBuilder(accountOut).reverse().toString().toCharArray()) {
            char charZero = '0';
            checksum += (digit - charZero) * digitPosition++;
        }
        final int factorFormula = 11;
        return (checksum % factorFormula) == 0;
    }

    public void writeNumberAccountsOutputFile() {
        final String outputFileName = "numberaccounts.txt";

        Writer writer;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(outputFileName)));
            for (String account : numberAccountsList) {
                writer.write(account + "\n");
            }
            
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getNumberAccountsList() {
        return numberAccountsList;
    }
}