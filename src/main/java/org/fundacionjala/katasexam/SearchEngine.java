package org.fundacionjala.katasexam;

/**
 * This class is used to find specific word into a text.
 *
 * @author Bruno Barrios
 */
public class SearchEngine {

    /**
     * This method is used to find a word into a text.
     *
     * @param needed   is the specific word.
     * @param hayStack is the complete text.
     * @return the index in where the word has found or return -1 if the word is not present
     * into the text.
     */
    public static int find(String needed, String hayStack) {
        if (needed.contains("_")) {
            for (String word : hayStack.split(" ")) {
                if (word.length() == needed.length() && word.contains(needed.replace("_", ""))) {
                    return hayStack.indexOf(word);
                }
            }
        }
        return hayStack.indexOf(needed);
    }
}
