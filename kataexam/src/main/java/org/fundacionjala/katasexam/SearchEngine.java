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
                if (word.replace(",","").length() == needed.length() && validate(word, needed)) {
                    return hayStack.indexOf(replaceWildcard(word.toCharArray(), needed.toCharArray()));
                }
            }
        }
        return hayStack.indexOf(needed);
    }

    private static boolean validate(String word, String needed) {
        boolean answer = false;
        int count = 0;
        char[] charArray = needed.replace("_", "").toCharArray();
        for (int index = 0; index < charArray.length; index++) {
            if (word.toLowerCase().contains(String.valueOf(charArray[index]))) {
                count++;
            }
        }
        if (count == charArray.length) {
            answer = true;
        }
        return answer;
    }

    private static String replaceWildcard(char[] charArray, char[] arrayNeeded) {
        String answer = "";
        for (int index = 0; index < arrayNeeded.length; index++) {
            arrayNeeded[index] = charArray[index];
            answer = answer + String.valueOf(arrayNeeded[index]);
        }
        System.out.println(answer);

        return answer;
    }
}
