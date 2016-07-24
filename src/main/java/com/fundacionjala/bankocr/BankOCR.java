package com.fundacionjala.bankocr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class if for resolve the KataBankOCR
 * <p>
 * User Story 1
 * You work for a bank, which has recently purchased an ingenious machine to assist in reading letters and faxes sent in by branch offices. The machine scans the paper documents, and produces a file with a number of entries which each look like this:
 * _  _     _  _  _  _  _
 * | _| _||_||_ |_   ||_||_|
 * ||_  _|  | _||_|  ||_| _|
 * Each entry is 4 lines long, and each line has 27 characters. The first 3 lines of each entry contain an account number written using pipes and underscores, and the fourth line is blank. Each account number should have 9 digits, all of which should be in the range 0-9. A normal file contains around 500 entries.
 * Your first task is to write a program that can take this file and parse it into actual account numbers.
 * <p>
 * User Story 2
 * Having done that, you quickly realize that the ingenious machine is not in fact infallible. Sometimes it goes wrong in its scanning. The next step therefore is to validate that the numbers you read are in fact valid account numbers. A valid account number has a valid checksum. This can be calculated as follows:
 * account number:  3  4  5  8  8  2  8  6  5
 * position names:  d9 d8 d7 d6 d5 d4 d3 d2 d1
 * checksum calculation:
 * (d1+2*d2+3*d3 +..+9*d9) mod 11 = 0
 * So now you should also write some code that calculates the checksum for a given number, and identifies if it is a valid account number.
 * <p>
 * User Story 3
 * Your boss is keen to see your results. He asks you to write out a file of your findings, one for each input file, in this format:
 * 457508000
 * 664371495 ERR
 * 86110??36 ILL
 * ie the file has one account number per row. If some characters are illegible, they are replaced by a ?. In the case of a wrong checksum, or illegible number, this is noted in a second column indicating status.
 *
 * @author Bruno Barrios, Rosario Garcia
 */
public class BankOCR {

    private static final String INPUT_FILE_NAME = "accountsOCR.txt";
    private static final String ILLEGIBLE_NUMBER = "?";
    private static final String ILLEGIBLE_MESSAGE = "ILL";
    private static final String ERROR_MESSAGE = "ERR";
    private static final int COLS_DIGITS = 3;
    private static final int ROWS_DIGITS = 3;
    private static final int NUMBER_DIGITS = 9;

    private List<String> numberAccountsList;

    /**
     * Constructor class
     */
    public BankOCR() {
        numberAccountsList = new ArrayList<String>();
    }

    /**
     * Method to read all ocr accounts and parse to numeric accounts.
     *
     * @throws IOException
     */
    public void ocrAccountToNumberAccount() throws IOException {
        List<String> ocrAccountsList = readAccountOcrFile(INPUT_FILE_NAME);

        for (int line = 0; line < ocrAccountsList.size(); line += ROWS_DIGITS + 1) {
            List<String> accountEntry = ocrAccountsList.subList(line, line + ROWS_DIGITS);
            String account = convertOcrAccountToNumberAccount(accountEntry);
            addAccountToNumberAccountsList(account);
        }
        writeNumberAccountsOutputFile();
    }

    /**
     * Method to read the file which contains the OCR accounts
     *
     * @param fileName name of file
     * @return ocr accounts list
     * @throws IOException
     */
    public List<String> readAccountOcrFile(String fileName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        String line;
        List ocrAccountsList = new ArrayList<String>();
        while ((line = bufferedReader.readLine()) != null) {
            ocrAccountsList.add(line);
        }
        fileInputStream.close();
        return ocrAccountsList;
    }

    /**
     * Method to extract a OCR digit from the OCR account and parse it.
     *
     * @param accountEntry List with one OCR account
     * @return numeric account
     */
    public String convertOcrAccountToNumberAccount(List<String> accountEntry) {
        StringBuilder accountNumber = new StringBuilder();
        for (int currentDigit = 0; currentDigit < NUMBER_DIGITS; currentDigit++) {
            String[] ocrDigit = new String[ROWS_DIGITS];
            int substringStartIndex = currentDigit * COLS_DIGITS;
            ocrDigit[0] = String.valueOf(accountEntry.get(0).substring(substringStartIndex, substringStartIndex + COLS_DIGITS));
            ocrDigit[1] = String.valueOf(accountEntry.get(1).substring(substringStartIndex, substringStartIndex + COLS_DIGITS));
            ocrDigit[2] = String.valueOf(accountEntry.get(2).substring(substringStartIndex, substringStartIndex + COLS_DIGITS));
            accountNumber.append(parseOcrDigitToNumberDigit(ocrDigit));
        }
        return accountNumber.toString();
    }

    /**
     * Method to parse a OCR digit to numeric digit
     *
     * @param ocrDigit
     * @return numeric digit
     */
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

    /**
     * Method to add the numeric account with the status to list of accounts that had converted
     *
     * @param numberAccount If the account is illegible in the second column will contain ILL,
     *                      If is a invalid account will contain ERR,
     *                      by default w'ont contain message, only the numeric account
     */
    public void addAccountToNumberAccountsList(String numberAccount) {
        if (numberAccount.contains(ILLEGIBLE_NUMBER)) {
            numberAccountsList.add(String.format("%s %s", numberAccount, ILLEGIBLE_MESSAGE));
        } else if (isValidAccount(numberAccount)) {
            numberAccountsList.add(numberAccount);
        } else {
            numberAccountsList.add(String.format("%s %s", numberAccount, ERROR_MESSAGE));
        }
    }

    /**
     * This method is to verify if the account is a valid account
     * if the account satisfy the condition: is a valid account,
     *
     * @param accountOut numeric account
     * @return true if (cheksum % 11) is zero,
     * false otherwise.
     */
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

    /**
     * Method to write the output file
     * which will contains all numeric accounts
     * with its status.
     */
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
