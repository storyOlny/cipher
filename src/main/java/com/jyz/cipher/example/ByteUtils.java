package com.jyz.cipher.example;

public class ByteUtils {

    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    public static byte[] hexStringToByteArray(String s) {
        char[] rawChars = s.toUpperCase().toCharArray();
        int hexChars = 0;
        for (int i = 0; i < rawChars.length; i++) {
            if (rawChars[i] >= '0' && rawChars[i] <= '9' || rawChars[i] >= 'A' && rawChars[i] <= 'F') {
                ++hexChars;
            }
        }
        byte[] byteString = new byte[hexChars + 1 >> 1];
        int pos = hexChars & 1;
        for (int i = 0; i < rawChars.length; i++) {
            if (rawChars[i] >= '0' && rawChars[i] <= '9') {
                byteString[pos >> 1] = (byte) (byteString[pos >> 1] << 4);
                byteString[pos >> 1] = (byte) (byteString[pos >> 1] | rawChars[i] - 48);

            } else {
                if (rawChars[i] < 'A' || rawChars[i] > 'F') {
                    continue;
                }
                byteString[pos >> 1] = (byte) (byteString[pos >> 1] << 4);
                byteString[pos >> 1] = (byte) (byteString[pos >> 1] | rawChars[i] - 55);
            }
            ++pos;
        }
        return byteString;
    }

    public static String byteArrayToHexString(byte[] b) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            result.append(HEX_ARRAY[b[i] >>> 4 & 15]);
            result.append(HEX_ARRAY[b[i] & 15]);
        }
        return result.toString();
    }
}
