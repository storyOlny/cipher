package com.jyz.cipher.example;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA加密解密,公钥加密，私钥解密，非对称加密
 */
public class RSAUtils {

    /**
     *
     * 生成密钥对
     * @return
     */
    public static KeyPair getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        //生成2048位的私钥和公钥
        keyPairGen.initialize(2048);
        return keyPairGen.generateKeyPair();
    }

    /**
     * 获取公钥（Base64编码）
     * @param keyPair
     * @return
     */
    public static String getPublicKey(KeyPair keyPair)
    {
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
    	return byte2Base64(bytes);
    }

    public static String byte2Base64(byte[] bytes)
    {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }

    /**
     * 获取私钥（Base64编码）
     * @param keyPair
     * @return
     */
    public static String getPrivateKey(KeyPair keyPair)
    {
        byte[] bytes = keyPair.getPrivate().getEncoded();
        return byte2Base64(bytes);
    }

    /**
     * 将Base64编码后的公钥转换成PublicKey对象
     * @param key
     * @return
     * @throws Exception
     */
    public static PublicKey string2PublicKey(String key) throws Exception
    {
        byte[] keyBytes = base642Byte(key);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(x509EncodedKeySpec);
    }

    public static byte[] base642Byte(String key) throws IOException {
        BASE64Decoder docoder = new BASE64Decoder();
        return docoder.decodeBuffer(key);
    }

    /**
     * 将Base64编码后的私钥转换成PrivateKey对象
     * @param key
     * @return
     * @throws Exception
     */
    public static PrivateKey string2PrivateKey(String key) throws Exception
    {
        byte[] keyBytes = base642Byte(key);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }
    /**
     * 公钥加密
     * @param content
     * @param publicKey
     * @return
     * @throws Exception
     */

    public static String publicEncrypt(String content, PublicKey publicKey) throws Exception
    {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte [] bytes = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        return byte2Base64(bytes);
    }

    public static String privateDecrypt(String content, PrivateKey privateKey) throws Exception
    {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte [] bytes = cipher.doFinal(base642Byte(content));
        return new String(bytes);
    }

    public static void main(String[] args) {
        try {
            //生成密钥对,公钥传给客户端，私钥保存在服务器端
            //生成RSA公钥和私钥，并用Base64编码
            KeyPair keyPair = getKeyPair();
            String publicKey = getPublicKey(keyPair);
            String privateKey = getPrivateKey(keyPair);
            System.out.println("公钥：" + publicKey);
            System.out.println("私钥：" + privateKey);
            //=======客户端代码
            //客户端端加密，服务器端解密
            String message = "Hello World";
            PublicKey publicKey1 = RSAUtils.string2PublicKey(publicKey);
            String publicKeyresult = publicEncrypt(message, publicKey1);

            //========服务端代码
            PrivateKey privateKey1 = RSAUtils.string2PrivateKey(privateKey);
            String privateKeyresult = privateDecrypt(publicKeyresult, privateKey1);
            System.out.println("解密后的明文：" + privateKeyresult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
