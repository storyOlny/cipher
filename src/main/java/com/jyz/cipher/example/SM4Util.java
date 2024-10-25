package com.jyz.cipher.example;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;


public class SM4Util {

    public static String encryptEcb(String content, String secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] keyData = ByteUtils.hexStringToByteArray(secretKey==null||secretKey.length()==0?"abcd123456789012abcd123456789012":secretKey);
        byte[] srcData = content.getBytes(StandardCharsets.UTF_8);
        Cipher cipher = Cipher.getInstance("SM4/ECB/PKCS5Padding", "BC");
        Key sm4Key = new SecretKeySpec(keyData, "SM4");
        cipher.init(Cipher.ENCRYPT_MODE, sm4Key);
        byte[] encrypted = cipher.doFinal(srcData);
        return ByteUtils.byteArrayToHexString(encrypted);
    }
}
