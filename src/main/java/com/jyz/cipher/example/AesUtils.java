package com.jyz.cipher.example;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * aes 加密工具类
 */
public class AesUtils {
    /**
     * aes加密 使用SecretKeySpec直接生成密钥 Base64编码
     *
     * @param content  要加密的内容
     * @param slatKey  原始的密钥
     * @param vecorKey IV 向量
     * @return 加密后的内容
     */

    public static String encrypt(String content, String slatKey, String vecorKey) {
        SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(StandardCharsets.UTF_8), "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(vecorKey.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
            byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * aes加密,由种子直接生成密钥 Base64编码
     *
     * @param content  要加密的内容
     * @param seed     种子随机数
     * @param vecorKey IV向量
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String encrypt_2(String content, String seed, String vecorKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256, new SecureRandom(seed.getBytes(StandardCharsets.UTF_8)));
        System.out.println(keyGenerator.getProvider().getName());
        SecretKey originalSecretKey = keyGenerator.generateKey();
        byte[] raw = originalSecretKey.getEncoded();
        SecretKey secretKey = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(vecorKey.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * aes解密
     * @param content   aes 加密后的内容
     * @param slatKey   原始的密钥
     * @param vecorKey  Ive 向量
     * @return 解密后的内容
     */
    public static String decrypt(String content, String slatKey, String vecorKey) {
        SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(StandardCharsets.UTF_8), "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(vecorKey.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            byte[] encrypted = Base64.getDecoder().decode(content);
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }
}
