package krysiRainbow;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Rainbow {

    private static final char[] alphabet = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private static final String HASH = "1d56a37fb6b08aa709fe90e12ca59e12";
    private static final int CHAINLENGTH = 2000;
    private static final int WORDLENGTH = 7;
    
    
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
    
    public String R(String h, int Stufe) {
        BigInteger H = new BigInteger(h, 16);
        H=H.add(BigInteger.valueOf(Stufe));
        StringBuilder r = new StringBuilder();

        for (int i = 1; i <= WORDLENGTH; i++) {
            r.append(alphabet[(H.mod(BigInteger.valueOf(alphabet.length))).intValue()]);
            H = H.divide(BigInteger.valueOf(alphabet.length));
        }

        return r.reverse().toString();
    }

	public void VogtTest() {
		String h1 = H("0000000");
		System.out.println(h1);
		String r1 = R(h1,0);
		System.out.println(r1);
		String h2 = H(r1);
		System.out.println(h2);
		String r2 = R(h2,1);
		System.out.println(r2);
		String h3 = H(r2);
		System.out.println(h3);
		String r3 = R(h3,2);
		System.out.println(r3);
		String h4 = H(r3);
		System.out.println(h4);
	}
}
