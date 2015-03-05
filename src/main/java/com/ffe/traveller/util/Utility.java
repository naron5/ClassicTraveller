package com.ffe.traveller.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by sechitwood on 2/9/15.
 */
public class Utility {

    public static long getSHA256(String in) {

        MessageDigest digest = null;
        byte[] hash = null;
        long returnValue = 0;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(in.getBytes("UTF-8"));
            int counter = 0;
            for (byte b : hash) {
                returnValue += b << (8 * counter++);
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return returnValue;
    }
}
