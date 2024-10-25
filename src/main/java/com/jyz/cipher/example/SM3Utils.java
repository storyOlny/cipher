package com.jyz.cipher.example;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;

public class SM3Utils {

    public static String encrypt(String content){
        SM3Digest sm3Digest = new SM3Digest();
        sm3Digest.update(content.getBytes(), 0, content.getBytes().length);
        byte[] result = new byte[32];
        sm3Digest.doFinal(result, 0);
        return new String(Hex.encode(result));
    }

    public static String hmacEncrypt(String content,String key){
        KeyParameter keyParam = new KeyParameter(key.getBytes());
        byte [] message = content.getBytes();
        SM3Digest sm3Digest = new SM3Digest();
        HMac hmac = new HMac(sm3Digest);
        hmac.init(keyParam);
        hmac.update(message, 0, message.length);
        byte[] result = new byte[hmac.getMacSize()];
        sm3Digest.doFinal(result, 0);
        return new String(Hex.encode(result));
    }
}
