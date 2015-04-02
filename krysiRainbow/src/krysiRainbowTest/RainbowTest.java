package krysiRainbowTest;

import krysiRainbow.Rainbow;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RainbowTest {

    Rainbow r = new Rainbow();
    private boolean setUpIsDone = false;

    @Before
    public void setup() {
        if (setUpIsDone) {
            return;
        }
        r.populateRainbow();
        setUpIsDone = true;
    }

    @Test
    public void TestR() {
        String r1 = r.R("29c3eea3f305d6b823f562ac4be35217", 0);
        assertEquals(r1, "87inwgn");
    }

    @Test
    public void TestH() {

        String h1 = r.H("0000000");
        assertEquals(h1, "29c3eea3f305d6b823f562ac4be35217");
    }

    @Test
    public void TestChain() {
        String h1 = r.H("0000000");
        String r1 = r.R(h1, 0);
        String h2 = r.H(r1);
        String r2 = r.R(h2, 1);
        String h3 = r.H(r2);
        String r3 = r.R(h3, 2);
        String h4 = r.H(r3);
        assertEquals(h4, "c0e9a2f2ae2b9300b6f7ef3e63807e84");
    }

    @Test
    public void TestRainbowTable() {
        assertEquals(2000, r.count());
        //random pick 1
        assertEquals(r.rainbow.get("000011u"), "fkiheu3");
        //random pick 2
        assertEquals(r.rainbow.get("00000ou"), "bq1q5bo");
        //random pick 3
        assertEquals(r.rainbow.get("000009q"), "mksbse3");
    }

    @Test
    public void TestValidHash() {

    }

}
