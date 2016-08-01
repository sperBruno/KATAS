package org.fundacionjala.katasexam;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Bruno Barrios on 7/25/2016.
 */
public class WildsTest {

    private static final String HAYSTACK = "Once upon a midnight dreary, while I pondered, weak and weary";

    @Test
    public void normalSearchTest() {
        assertEquals(0, SearchEngine.find("Once", HAYSTACK));
        assertEquals(12, SearchEngine.find("midnight", HAYSTACK));
        assertEquals(-1, SearchEngine.find("codewars", HAYSTACK));
    }

    @Test
    public void wildSearchTest() {
        assertEquals(5, SearchEngine.find("_po_", HAYSTACK));
        assertEquals(12, SearchEngine.find("___night", HAYSTACK));
        assertEquals(29, SearchEngine.find("_hile", HAYSTACK));
        assertEquals(29, SearchEngine.find("w_ile", HAYSTACK));
        assertEquals(37, SearchEngine.find("pondere_", HAYSTACK));
        assertEquals(37, SearchEngine.find("p_ndere_", HAYSTACK));
    }
}
