package krysiRainbow;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class Rainbow {

    private static final char[] alphabet = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private final int CHAINLENGTH = 2000;
    private final int WORDLENGTH = 7;

    public HashMap<String, String> rainbow;

    /**
     * constructor for class rainbow
     */
    public Rainbow() {
        rainbow = new HashMap<>();
    }

    /**
     * MD5 hash function
     *
     * @param r String to hash
     * @return hashed String
     */
    public String H(String r) {
        MessageDigest md = null;
        byte[] bytes = null;
        try {
            md = MessageDigest.getInstance("MD5");
            bytes = md.digest(r.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xff & bytes[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }

    /**
     * reduction function
     *
     * @param h     String for reduction
     * @param level level of reduction
     * @return
     */
    public String R(String h, int level) {
        BigInteger H = new BigInteger(h, 16);
        H = H.add(BigInteger.valueOf(level));
        StringBuilder r = new StringBuilder();

        for (int i = 1; i <= WORDLENGTH; i++) {
            r.append(alphabet[(H.mod(BigInteger.valueOf(alphabet.length))).intValue()]);
            H = H.divide(BigInteger.valueOf(alphabet.length));
        }

        return r.reverse().toString();
    }

    /**
     * test method only for testing purpose
     */
    public void VogtTest() {
        String h1 = H("0000000");
        System.out.println(h1);
        String r1 = R(h1, 0);
        System.out.println(r1);
        String h2 = H(r1);
        System.out.println(h2);
        String r2 = R(h2, 1);
        System.out.println(r2);
        String h3 = H(r2);
        System.out.println(h3);
        String r3 = R(h3, 2);
        System.out.println(r3);
        String h4 = H(r3);
        System.out.println(h4);
    }

    /**
     * populates rainbowtable witch possible Strings and their hashes
     */
    public void populateRainbow() {
        possibleStrings(WORDLENGTH, alphabet, "");
        determineChainEndHashes();
    }

    private void determineChainEndHashes() {
        for (Map.Entry<String, String> curr : rainbow.entrySet()) {
            String temp = curr.getKey();
            for (int i = 0; i <= CHAINLENGTH; i++) {
                temp = H(temp);
                temp = R(temp, i);
            }
            rainbow.put(curr.getKey(), temp);
        }
    }

    /**
     * adds possible passwords strings to hashmap
     *
     * @param maxLength max wordlength
     * @param alphabet
     * @param curr
     */
    private void possibleStrings(int maxLength, char[] alphabet, String curr) {
        if (rainbow.size() >= CHAINLENGTH) return;
        // If the current string has reached it's maximum length
        if (curr.length() == maxLength) {
            rainbow.put(curr, "");

            // Else add each letter from the alphabet to new strings and process these new strings again
        } else {
            for (int i = 0; i < alphabet.length; i++) {
                String oldCurr = curr;
                curr += alphabet[i];
                possibleStrings(maxLength, alphabet, curr);
                curr = oldCurr;
            }
        }
    }

    /**
     * return number of elements in rainbow table
     *
     * @return
     */
    public int count() {
        return rainbow.size();
    }

    /**
     * prints rainbow table
     */
    public void printRainbow() {
        for (Entry<String, String> entry : rainbow.entrySet()) {
            System.out.println(entry.getKey() + " | " + entry.getValue());
        }
        System.out.println(count() + " elements are in rainbow table");
    }

    /**
     * search the rainbow table for a given hash, trying reduction function from chainlength to 0
     * solution is collision proof returning all possibles chain starters
     *
     * @param inHash hash, which should be found in table
     * @return list of possible rainbow chain begins
     */
    public Optional<ArrayList<String>> searchFor(String inHash) {
        ArrayList<ArrayList<String>> possibleResults = new ArrayList<>();
        ArrayList<String> resultList = new ArrayList<>();
        for (int i = CHAINLENGTH + 1; i >= 0; i--) {
            String s = iterateReduction(inHash, i);
            Optional<ArrayList<String>> oResults = rainbowContainsValue(s);
            if (oResults.isPresent()) {
                possibleResults.add(oResults.get());
            }
        }
        for (ArrayList<String> curr : possibleResults) {
            for (String s : curr) {
                resultList.add(s);
            }
        }
        //System.out.println("Search has ended with " + possibleResults.size() + " results.");
        return resultList.size() > 0 ? Optional.of(resultList) : Optional.empty();
    }

    /**
     * iterates through reductions in order to find a matching string in a chain, starting from the given reduction level
     *
     * @param hash         hash value to reduce and hash
     * @param currentLevel level where to start from
     * @return the resulting string after current level to chain length iterations
     */
    private String iterateReduction(String hash, int currentLevel) {
        while (currentLevel < CHAINLENGTH) {
            hash = R(hash, currentLevel);
            hash = H(hash);
            currentLevel++;
        }
        hash = R(hash, CHAINLENGTH);
        return hash;
    }

    /**
     * checks if the rainbow contains a value
     * has to be an own implementation using .equals because HashMap.containsvalue would use identity matching
     *
     * @param k String to find in value column
     * @return if a value is present, the value, else empty
     */
    private Optional<ArrayList<String>> rainbowContainsValue(String k) {
        ArrayList<String> hits = new ArrayList<>();
        for (Map.Entry<String, String> entr : rainbow.entrySet()) {
            if (entr.getValue().toLowerCase().equals(k.toLowerCase())) {
                hits.add(entr.getKey());
                //System.out.println("A chain starter has been found (reduced value: "+ entr.getValue()+"), matching key is [" + entr.getKey() + "].");
            }
        }
        if (hits.size() > 0) return Optional.of(hits);
        else return Optional.empty();
    }
}

