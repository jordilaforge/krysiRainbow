package krysiRainbow;

import java.util.ArrayList;
import java.util.Optional;

public class MainRainbow {

    private static final String TESTHASH = "1d56a37fb6b08aa709fe90e12ca59e12";  //Valid
    private static final String TESTHASH2 = "1e56a37fb6b08aa709fe90e12ca59e12"; //Invalid

    public static void main(String[] args) {
        Rainbow a = new Rainbow();
        a.populateRainbow();
        a.printRainbow();
        Optional<ArrayList<String>> res = a.searchFor(TESTHASH);
        if (res.isPresent()) {
            for (String r : res.get()) {
                System.out.println("Possible result for " + TESTHASH + " is: " + r);
            }
        } else {
            System.out.println("No possible result has been found.");
        }
    }
}
