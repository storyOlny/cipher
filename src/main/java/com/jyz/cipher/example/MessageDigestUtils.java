package com.jyz.cipher.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestUtils {

    public static String encrypt(String content) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(content.getBytes());
        byte[] re = md5.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < re.length; i++) {
            sb.append(Character.forDigit((re[i] & 240) >> 4, 16));
            sb.append(Character.forDigit(re[i] & 15, 16));
        }
        return sb.toString();
    }
}
