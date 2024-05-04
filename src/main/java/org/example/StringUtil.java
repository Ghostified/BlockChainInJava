package org.example;

/*
This is a utility class to create the SHA 256 algorithm
 */

import org.bouncycastle.*;

import java.security.*;
import java.util.Base64;
import java.util.List;
import com.google.gson.GsonBuilder;

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

    public static byte [] applyECDSASig (PrivateKey privateKey , String input) {
        Signature dsa;
        byte [] output = new byte [0];
        try {
            dsa = Signature.getInstance("ECDSA", "BC");
            dsa.initSign(privateKey);
            byte [] strByte = input.getBytes();
            dsa.update(strByte);
            byte [] realSig = dsa.sign();
            output = realSig;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  output;
    }

    //verifies a string signature
    public static boolean verifyECDSASig (PublicKey publicKey, String data, byte [] signature) {
        try {
            Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
            ecdsaVerify.initVerify (publicKey);
            ecdsaVerify.update(data.getBytes());
            return ecdsaVerify.verify(signature);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //shorthand helper to turn an object into a json
    public static String getStringFromKey (Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    //shorthand helper to turn object into a json string

}
