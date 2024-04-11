package org.example;

/*
This is a utility class to create the SHA 256 algorithm
 */

import java.security.MessageDigest;

public class StringUtil {
    //This class applies the sha256 to a String and returns the result
    public static String applySha256 (String input ) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //Applies SHA-256 to our input
            byte [] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer(); //This will contain the hashHexadecimal
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
