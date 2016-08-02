package com.fundacionjala.bankocr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for {@link BankOCR}
 * 
 * @author Bruno Barrios, Rosario Garcia
 */
public class BankOCRTest {

    private BankOCR bankOCR;

    @Before
    public void setUp() {
        bankOCR = new BankOCR();
    }

    @Test
    public void testReadFromFile() throws IOException {
        bankOCR.readAccountOcrFile("accountsOCR.txt");
        List<String> expectedResult = new ArrayList<String>();
        expectedResult.add("    _  _     _  _  _  _  _ ");
        expectedResult.add("  | _||_||_||_ |_   ||_||_|");
        expectedResult.add("  ||_ |_|  | _||_|  ||_| _|");
        expectedResult.add("");
        expectedResult.add("    _  _     _  _  _  _  _ ");
        expectedResult.add("  | _| _||_||_ |_   ||_|| |");
        expectedResult.add("  ||_  _|  | _||_|  ||_| _|");
        expectedResult.add("");
        expectedResult.add("    _  _     _  _  _  _  _ ");
        expectedResult.add("  | _| _||_||_ |_   ||_||_|");
        expectedResult.add("  ||_  _|  | _||_|  ||_| _|");
        assertEquals("", expectedResult, bankOCR.readAccountOcrFile("accountsOCR.txt"));
    }

    @Test
    public void testConvertAocracccountToRealNumberAccount() {
        String[] accountEntry = new String[]{   "    _  _     _  _  _  _  _ ",
                                                "  | _| _||_||_ |_   ||_||_|",
                                                "  ||_  _|  | _||_|  ||_| _|"};
        String expectedResult = "123456789";
        assertEquals(expectedResult, bankOCR.convertOcrAccountToNumberAccount(Arrays.asList(accountEntry)));
    }

    @Test
    public void testValidAccount() {
        assertTrue(bankOCR.isValidAccount("123456789"));
        assertFalse(bankOCR.isValidAccount("128456789"));
    }

    @Test
    public void testCheckAccount() {
        List<String> expectedResult = new ArrayList<String>();
        expectedResult.add("128456789 ERR");
        expectedResult.add("12345678? ILL");
        expectedResult.add("123456789");
        bankOCR.addAccountToNumberAccountsList("128456789");
        bankOCR.addAccountToNumberAccountsList("12345678?");
        bankOCR.addAccountToNumberAccountsList("123456789");
        assertEquals(expectedResult, bankOCR.getNumberAccountsList());
    }
}
