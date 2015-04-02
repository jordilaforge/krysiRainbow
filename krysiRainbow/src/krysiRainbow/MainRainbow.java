package krysiRainbow;

import java.util.ArrayList;
import java.util.Optional;

public class MainRainbow {

    private static final String TESTHASH = "1d56a37fb6b08aa709fe90e12ca59e12";  //Valid

    public static void main(String[] args) {
        Rainbow a = new Rainbow();
        System.out.println("***");
        System.out.println("Populating table...");
        a.populateRainbow();
        //a.printRainbow();
        System.out.println("Searching for test hash...");
        Optional<ArrayList<String>> res = a.searchFor(TESTHASH);
        System.out.println("---");
        System.out.println("Results:");
        if (res.isPresent()) {
            ArrayList<String> p = res.get();
            for (String r : p) {
                System.out.println("Possible result for " + TESTHASH + " is: " + r);
            }
            if (p.size() == 1) System.out.println("There was no collision.");
            else if (p.size() > 1)
                System.out.println("There was a collision, possible results have been collected and are shown above.");
        } else {
            System.out.println("No possible result has been found.");
        }
        System.out.println("***");
    }
}
