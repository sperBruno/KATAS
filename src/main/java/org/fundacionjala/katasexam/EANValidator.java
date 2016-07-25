package org.fundacionjala.katasexam;

/**
 * This class is used to verify if a number is an EAN NUMBER.
 *
 * @author Bruno Barrios.
 */
public class EANValidator {

    public static final int ONE_NUMBER = 1;
    private static final int TEN_NUMBER = 10;
    private static final int ZERO_NUMBER = 0;

    /**
     * This method validates whether a number is an EAN NUMBER.
     */
    public static boolean validate(String eanNumber) {
        char[] number = eanNumber.toCharArray();
        int checksum = checkSum(sumEANNumber(eanNumber.toCharArray()));
        return checksum == Integer.parseInt(String.valueOf(number[number.length - ONE_NUMBER]));
    }

    /**
     * This method will validate if a sum is divisible by 10.
     *
     * @param sum
     * @return return the module of ten.
     */
    private static int checkSum(int sum) {
        int checksum;
        if (sum % TEN_NUMBER == ZERO_NUMBER) {
            checksum = ZERO_NUMBER;
        } else {
            checksum = TEN_NUMBER - (sum % TEN_NUMBER);
        }
        return checksum;
    }

    /**
     * this method is used to sum number of a char array.
     *
     * @param eanNumber
     * @return the sum of the number till one before its threshold.
     */
    private static int sumEANNumber(char[] eanNumber) {
        int sum = ZERO_NUMBER;
        final int multiplyNumber = 3;
        final int twoNumber = 2;
        for (int index = ZERO_NUMBER; index < eanNumber.length - ONE_NUMBER; index++) {
            if ((index + ONE_NUMBER) % twoNumber != ZERO_NUMBER) {
                sum += (Integer.parseInt((String.valueOf(eanNumber[index]))));
            } else {
                sum += (Integer.parseInt(String.valueOf(eanNumber[index])) * multiplyNumber);
            }
        }
        return sum;
    }
}
