package com.jyz.cipher.example;

import sun.misc.BASE64Encoder;

import java.util.Base64;

/**
 * java 64编码工具类
 */
public class Base641 {

    public static String encode(String content){
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(content.getBytes());
    }

    public static String encode1(String content){
        return Base64.getEncoder().encodeToString(content.getBytes());
    }
}
